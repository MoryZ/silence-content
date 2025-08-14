package com.old.silence.content.infrastructure.milvus;

import io.milvus.client.MilvusServiceClient;
import io.milvus.grpc.DataType;
import io.milvus.param.ConnectParam;
import io.milvus.param.IndexType;
import io.milvus.param.MetricType;
import io.milvus.param.collection.CreateCollectionParam;
import io.milvus.param.collection.DropCollectionParam;
import io.milvus.param.collection.FieldType;
import io.milvus.param.collection.HasCollectionParam;
import io.milvus.param.collection.LoadCollectionParam;
import io.milvus.param.dml.InsertParam;
import io.milvus.param.dml.SearchParam;
import io.milvus.param.index.CreateIndexParam;
import io.milvus.response.SearchResultsWrapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author moryzang
 */
public class MilvusJavaDemo {

    private static final String COLLECTION_NAME = "demo_collection";
    private static final int DIMENSION = 128;

    public static void main(String[] args) {
        // 1. 连接Milvus
        MilvusServiceClient milvusClient = new MilvusServiceClient(
                ConnectParam.newBuilder()
                        .withHost("localhost")
                        .withPort(19530)
                        .withConnectTimeout(10, TimeUnit.SECONDS)  // 增加超时
                        .withKeepAliveTime(20, TimeUnit.SECONDS)
                        .withKeepAliveTimeout(10, TimeUnit.SECONDS)
                        .build()
        );

        try {
            // 2. 检查集合是否存在，存在则删除
            if (milvusClient.hasCollection(HasCollectionParam.newBuilder()
                    .withCollectionName(COLLECTION_NAME)
                    .build()).getData()) {
                milvusClient.dropCollection(DropCollectionParam.newBuilder()
                        .withCollectionName(COLLECTION_NAME)
                        .build());
            }

            // 3. 创建集合
            FieldType idField = FieldType.newBuilder()
                    .withName("id")
                    .withDataType(DataType.Int64)
                    .withPrimaryKey(true)
                    .withAutoID(true)
                    .build();

            FieldType vectorField = FieldType.newBuilder()
                    .withName("vector")
                    .withDataType(DataType.FloatVector)
                    .withDimension(DIMENSION)
                    .build();

            milvusClient.createCollection(CreateCollectionParam.newBuilder()
                    .withCollectionName(COLLECTION_NAME)
                    .addFieldType(idField)
                    .addFieldType(vectorField)
                    .build());

            // 4. 插入数据
            Random random = new Random();
            List<List<Float>> vectors = new ArrayList<>();
            for (int i = 0; i < 500; i++) {
                List<Float> vector = new ArrayList<>();
                for (int j = 0; j < DIMENSION; j++) {
                    vector.add(random.nextFloat());
                }
                vectors.add(vector);
            }

            // 分批插入（每批200条）
            int batchSize = 200;
            for (int i = 0; i < vectors.size(); i += batchSize) {
                int end = Math.min(i + batchSize, vectors.size());
                List<List<Float>> batch = vectors.subList(i, end);

                InsertParam insertParam = InsertParam.newBuilder()
                        .withCollectionName(COLLECTION_NAME)
                        .withFields(Collections.singletonList(
                                new InsertParam.Field("vector", batch)
                        ))
                        .build();

                try {
                    milvusClient.insert(insertParam);
                } catch (Exception e) {
                    System.err.println("插入批次 " + i + "-" + end + " 失败: " + e.getMessage());
                }
            }

            // 5. 创建索引
            milvusClient.createIndex(CreateIndexParam.newBuilder()
                    .withCollectionName(COLLECTION_NAME)
                    .withFieldName("vector")
                    .withIndexType(IndexType.IVF_FLAT)
                    .withMetricType(MetricType.L2)
                    .withExtraParam("{\"nlist\":1024}")
                    .build());

            // 6. 加载集合到内存
            milvusClient.loadCollection(LoadCollectionParam.newBuilder()
                    .withCollectionName(COLLECTION_NAME)
                    .build());

            // 7. 向量搜索
            List<Float> searchVector = vectors.getFirst(); // 用第一个向量作为查询
            List<List<Float>> searchVectors = Collections.singletonList(searchVector);

            SearchParam searchParam = SearchParam.newBuilder()
                    .withCollectionName(COLLECTION_NAME)
                    .withMetricType(MetricType.L2)
                    .withTopK(5)
                    .withVectors(searchVectors)
                    .withVectorFieldName("vector")
                    .build();

            SearchResultsWrapper resultsWrapper = new SearchResultsWrapper(
                    milvusClient.search(searchParam).getData().getResults()
            );

            System.out.println("搜索结果:");
            for (SearchResultsWrapper.IDScore score : resultsWrapper.getIDScore(0)) {
                System.out.printf("ID: %d, 距离: %f\n", score.getLongID(), score.getScore());
            }

        } finally {
            // 8. 关闭连接
            milvusClient.close();
        }
    }
}
