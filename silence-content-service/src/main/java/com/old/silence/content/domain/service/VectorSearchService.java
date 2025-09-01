package com.old.silence.content.domain.service;

import io.milvus.client.MilvusServiceClient;
import io.milvus.grpc.DataType;
import io.milvus.param.IndexType;
import io.milvus.param.MetricType;
import io.milvus.param.R;
import io.milvus.param.RpcStatus;
import io.milvus.param.collection.CreateCollectionParam;
import io.milvus.param.collection.DropCollectionParam;
import io.milvus.param.collection.FieldType;
import io.milvus.param.collection.HasCollectionParam;
import io.milvus.param.collection.LoadCollectionParam;
import io.milvus.param.dml.InsertParam;
import io.milvus.param.dml.SearchParam;
import io.milvus.param.index.CreateIndexParam;
import io.milvus.response.SearchResultsWrapper;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author MurrayZhang
 */
@Service
public class VectorSearchService {


    private static final Logger log = LoggerFactory.getLogger(VectorSearchService.class);
    private final MilvusServiceClient milvusClient;
    private final OllamaEmbeddingClient embeddingClient;

    private static final String COLLECTION_NAME = "book_chunks";
    private static final int VECTOR_DIMENSION = 384; // 根据使用的嵌入模型调整维度

    public VectorSearchService(MilvusServiceClient milvusClient,
                               EmbeddingClient embeddingClient) {
        this.milvusClient = milvusClient;
        this.embeddingClient = embeddingClient;
    }

    @PostConstruct
    public void init() {
        createCollectionIfNotExists();
        createIndex();
        loadCollection();
    }

    private void createCollectionIfNotExists() {
        // 检查集合是否存在
        R<Boolean> resp = milvusClient.hasCollection(HasCollectionParam.newBuilder()
                .withCollectionName(COLLECTION_NAME)
                .build());

        if (!resp.getData()) {
            log.info("创建Milvus集合: {}", COLLECTION_NAME);

            // 定义字段
            FieldType idField = FieldType.newBuilder()
                    .withName("id")
                    .withDataType(DataType.Int64)
                    .withPrimaryKey(true)
                    .withAutoID(true)
                    .build();

            FieldType fileIdField = FieldType.newBuilder()
                    .withName("file_id")
                    .withDataType(DataType.VarChar)
                    .withMaxLength(36)
                    .build();

            FieldType bookNameField = FieldType.newBuilder()
                    .withName("book_name")
                    .withDataType(DataType.VarChar)
                    .withMaxLength(255)
                    .build();

            FieldType contentField = FieldType.newBuilder()
                    .withName("content")
                    .withDataType(DataType.VarChar)
                    .withMaxLength(4000)
                    .build();

            FieldType vectorField = FieldType.newBuilder()
                    .withName("vector")
                    .withDataType(DataType.FloatVector)
                    .withDimension(VECTOR_DIMENSION)
                    .build();

            // 创建集合
            CreateCollectionParam createParam = CreateCollectionParam.newBuilder()
                    .withCollectionName(COLLECTION_NAME)
                    .withDescription("Book text chunks with embeddings")
                    .withShardsNum(2)
                    .addFieldType(idField)
                    .addFieldType(fileIdField)
                    .addFieldType(bookNameField)
                    .addFieldType(contentField)
                    .addFieldType(vectorField)
                    .build();

            R<RpcStatus> response = milvusClient.createCollection(createParam);
            if (response.getStatus() != R.Status.Success.getCode()) {
                log.error("创建集合失败: {}", response.getMessage());
                throw new RuntimeException("Failed to create Milvus collection");
            }
        }
    }

    private void createIndex() {
        CreateIndexParam createIndexParam = CreateIndexParam.newBuilder()
                .withCollectionName(COLLECTION_NAME)
                .withFieldName("vector")
                .withIndexType(IndexType.IVF_FLAT)
                .withMetricType(MetricType.COSINE)
                .withExtraParam("{\"nlist\":1024}")
                .build();

        R<RpcStatus> response = milvusClient.createIndex(createIndexParam);
        if (response.getStatus() != R.Status.Success.getCode()) {
            log.error("创建索引失败: {}", response.getMessage());
        }
    }

    private void loadCollection() {
        R<RpcStatus> response = milvusClient.loadCollection(LoadCollectionParam.newBuilder()
                .withCollectionName(COLLECTION_NAME)
                .build());

        if (response.getStatus() != R.Status.Success.getCode()) {
            log.error("加载集合失败: {}", response.getMessage());
        }
    }

    public void insertEmbeddings(List<String> chunks, BigInteger fileId, String bookName) {
        if (chunks.isEmpty()) {
            return;
        }

        try {
            // 生成嵌入向量
            List<List<Float>> embeddings = generateEmbeddings(chunks);

            // 准备插入数据
            List<InsertParam.Field> fields = new ArrayList<>();
            fields.add(new InsertParam.Field("file_id",
                    chunks.stream().map(chunk -> fileId).collect(Collectors.toList())));
            fields.add(new InsertParam.Field("book_name",
                    chunks.stream().map(chunk -> bookName).collect(Collectors.toList())));
            fields.add(new InsertParam.Field("content", chunks));
            fields.add(new InsertParam.Field("vector", embeddings));

            // 插入数据
            InsertParam insertParam = InsertParam.newBuilder()
                    .withCollectionName(COLLECTION_NAME)
                    .withFields(fields)
                    .build();

            milvusClient.insert(insertParam);
            log.info("成功插入 {} 个文本块到Milvus", chunks.size());

        } catch (Exception e) {
            log.error("插入嵌入向量失败", e);
        }
    }

    public List<Map<String, Object>> similaritySearch(String query, int topK) {
        try {
            // 生成查询向量的嵌入
            List<Float> queryEmbedding = embeddingClient.embed(query);

            // 构建搜索参数
            String searchParam = SearchParam.newBuilder()
                    .withCollectionName(COLLECTION_NAME)
                    .withMetricType(MetricType.COSINE)
                    .withTopK(topK)
                    .withVectors(List.of(queryEmbedding))
                    .withVectorFieldName("vector")
                    .addOutField("file_id")
                    .addOutField("book_name")
                    .addOutField("content")
                    .build();

            // 执行搜索
            R<SearchResultsWrapper> response = milvusClient.search(searchParam);

            if (response.getStatus() != R.Status.Success.getCode()) {
                log.error("向量搜索失败: {}", response.getMessage());
                return List.of();
            }

            // 处理搜索结果
            SearchResultsWrapper wrapper = response.getData();
            List<Map<String, Object>> results = new ArrayList<>();

            for (int i = 0; i < wrapper.getRowRecords(0).size(); i++) {
                SearchResultsWrapper.IDScore score = wrapper.getIDScore(0).get(i);
                Map<String, Object> entity = wrapper.getRowRecords(0).get(i);

                results.add(Map.of(
                        "score", score.getScore(),
                        "book_name", entity.get("book_name"),
                        "content", entity.get("content"),
                        "file_id", entity.get("file_id")
                ));
            }

            return results;

        } catch (Exception e) {
            log.error("相似性搜索失败", e);
            return List.of();
        }
    }

    private List<List<Float>> generateEmbeddings(List<String> texts) {
        List<List<Float>> embeddings = new ArrayList<>();
        for (String text : texts) {
            List<Float> embedding = embeddingClient.embed(text);
            embeddings.add(embedding);
        }
        return embeddings;
    }

    public void cleanup() {
        R<RpcStatus> response = milvusClient.dropCollection(DropCollectionParam.newBuilder()
                .withCollectionName(COLLECTION_NAME)
                .build());

        if (response.getStatus() == R.Status.Success.getCode()) {
            log.info("成功清理Milvus集合");
        }
    }
}
