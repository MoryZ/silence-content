# 内容互动系统 (Content Interaction System) - 详细任务清单

**创建时间**: 2026-05-10  
**版本**: 1.0  
**状态**: 架构设计确认阶段  

---

## 📋 目录

1. [系统架构概览](#系统架构概览)
2. [第一阶段：Redis Key 规范设计](#第一阶段redis-key-规范设计)
3. [第二阶段：数据库实体 & Mapper](#第二阶段数据库实体--mapper)
4. [第三阶段：Redis DAO 层](#第三阶段redis-dao-层)
5. [第四阶段：核心业务 Service](#第四阶段核心业务-service)
6. [第五阶段：异步日志处理](#第五阶段异步日志处理)
7. [第六阶段：定时同步任务](#第六阶段定时同步任务)
8. [第七阶段：配置 & 工具类](#第七阶段配置--工具类)
9. [容错机制与数据完整性保证](#容错机制与数据完整性保证)
10. [实现检查清单](#实现检查清单)

---

## 系统架构概览

### 核心数据流

```
用户操作(点赞/浏览/分享)
  ↓
[同步] 写入 status 表 (仅点赞/收藏)
  ↓
[实时] 更新 Redis Bitmap + Hash + Set
  ↓
[异步] 发送事件 → log 表 (MQ消费)
  ↓
[定时1h] 从 Redis 脏数据池同步计数到 accumulation 表
```

### 三层存储模型

| 层级 | 表/缓存 | 用途 | 一致性保证 |
|------|--------|------|----------|
| **日志层** | content_interaction_log | 完整审计日志 | 最终一致 |
| **状态层** | content_user_interaction_status | 点赞/收藏状态 | 强一致 |
| **计数层** | content_interaction_accumulation | 各类互动总数 | 最终一致 |
| **缓存层** | Redis (Bitmap+Hash+Set) | 实时计数 & 状态 | 多维度 |

---

## 第一阶段：Redis Key 规范设计

### 1.1 Redis Key 设计原则

| Key类型 | 格式 | 数据结构 | TTL | 用途 | 选型理由 |
|--------|------|--------|-----|------|---------|
| **实时计数** | `interact:count:{type}:{resId}` | Hash | 永久 | 存储各类互动的增量计数 | Hash 支持原子性 HINCRBY，避免竞态 |
| **用户状态** | `interact:stat:{type}:{resId}` | Bitmap | 永久 | 用户点赞/收藏的 bitmap，offset=userId | Bitmap 空间高效，支持位操作 |
| **脏数据池** | `interact:dirty_set:{yyyyMMddHH}` | Set | 48h | 存储本小时发生变动的 resId | Set 去重，按小时隔离 |
| **点赞状态临时** | `interact:like:temp:{userId}:{resId}` | String | 5min | 防止重复点赞的临时标记 | String 足够，快速过期 |

### 1.2 Key 层级与命名空间

```
前缀:操作类型:维度:资源ID

示例：
  interact:count:like:10086        # 文章 10086 的点赞总数
  interact:count:favorite:10086    # 文章 10086 的收藏总数
  interact:count:view:10086        # 文章 10086 的浏览总数
  interact:stat:like:10086         # 文章 10086 的点赞 Bitmap
  interact:stat:favorite:10086     # 文章 10086 的收藏 Bitmap
  interact:dirty_set:202605101000  # 2026-05-10 10:00 的脏数据池
```

### 1.3 互动类型枚举

```java
public enum InteractionType {
    LIKE(1, "点赞"),          // 有状态，需维护 status 表
    FAVORITE(2, "收藏"),       // 有状态，需维护 status 表
    VIEW(3, "浏览"),           // 无状态，仅记录
    SHARE(4, "分享");          // 无状态，仅记录
    
    private final int code;
    private final String name;
}
```

---

## 第二阶段：数据库实体 & Mapper

### 2.1 实体类需求

#### 2.1.1 ContentUserInteractionStatus 实体

```
字段规格：
- statusId: Long (主键)
- userId: Long (用户ID)
- resourceId: Long (资源ID，如文章ID)
- resourceType: Integer (资源类型：文章=1, 视频=2)
- interactionType: Integer (互动类型：点赞=1, 收藏=2)
- status: Integer (状态：1=有效, 0=已取消)
- gmtCreate: LocalDateTime (创建时间)
- gmtModified: LocalDateTime (修改时间)

索引：
- 联合主键: (userId, resourceId, resourceType, interactionType)
- 反向查询: (resourceId, interactionType) 用于统计
```

#### 2.1.2 ContentInteractionLog 实体

```
字段规格：
- logId: Long (主键)
- userId: Long (用户ID)
- resourceId: Long (资源ID)
- resourceType: Integer (资源类型)
- interactionType: Integer (互动类型)
- clientIp: String (客户端IP)
- userAgent: String (用户代理)
- gmtCreate: LocalDateTime (创建时间)

索引：
- (resourceId, interactionType, gmtCreate) - 用于统计
- (userId, gmtCreate) - 用于审计
```

#### 2.1.3 ContentInteractionAccumulation 实体

```
字段规格：
- accumulationId: Long (主键)
- resourceId: Long (资源ID)
- resourceType: Integer (资源类型)
- likeCount: Long (点赞数)
- favoriteCount: Long (收藏数)
- viewCount: Long (浏览数)
- shareCount: Long (分享数)
- gmtCreate: LocalDateTime (创建时间)
- gmtModified: LocalDateTime (修改时间)

索引：
- (resourceId, resourceType) - 唯一索引
```

### 2.2 Mapper 接口关键方法

#### 2.2.1 ContentUserInteractionStatusMapper

```java
// 插入点赞/收藏记录
int insertStatus(ContentUserInteractionStatus status);

// 删除（取消点赞/收藏）
int deleteStatus(Long userId, Long resourceId, Integer resourceType, Integer interactionType);

// 查询用户是否已点赞
boolean isLiked(Long userId, Long resourceId, Integer resourceType, Integer interactionType);

// 统计资源被点赞数
Long countByResourceAndType(Long resourceId, Integer resourceType, Integer interactionType);
```

#### 2.2.2 ContentInteractionLogMapper

```java
// 异步插入日志
int insertLog(ContentInteractionLog log);

// 批量插入日志（消息队列消费时使用）
int batchInsertLog(List<ContentInteractionLog> logs);
```

#### 2.2.3 ContentInteractionAccumulationMapper - **关键**

```java
// 查询或创建
ContentInteractionAccumulation findOrCreate(Long resourceId, Integer resourceType);

// 原子性增量更新：支持 ON DUPLICATE KEY UPDATE
int upsertCount(Long resourceId, Integer resourceType, 
                Long likeDelta, Long favoriteDelta, 
                Long viewDelta, Long shareDelta);

// 批量 upsert（定时任务使用）
int batchUpsertCount(List<InteractionDeltaDTO> deltas);

// 获取实时计数
ContentInteractionAccumulation getAccumulation(Long resourceId, Integer resourceType);
```

### 2.3 关键 SQL 语句

#### 2.3.1 Batch Upsert SQL (核心)

```sql
-- 原子性操作：使用 ON DUPLICATE KEY UPDATE 保证幂等性
INSERT INTO content_interaction_accumulation 
(resource_id, resource_type, like_count, favorite_count, view_count, share_count, gmt_create, gmt_modified)
VALUES 
(?, ?, ?, ?, ?, ?, NOW(), NOW()),
(?, ?, ?, ?, ?, ?, NOW(), NOW())
-- 如果 resource_id+resource_type 存在，则更新计数
ON DUPLICATE KEY UPDATE
  like_count = like_count + VALUES(like_count),
  favorite_count = favorite_count + VALUES(favorite_count),
  view_count = view_count + VALUES(view_count),
  share_count = share_count + VALUES(share_count),
  gmt_modified = NOW();
```

#### 2.3.2 删除status记录时的处理

```sql
-- 删除时需要同时记录日志
DELETE FROM content_user_interaction_status 
WHERE user_id = ? 
  AND resource_id = ? 
  AND resource_type = ? 
  AND interaction_type = ?
  AND status = 1;
```

---

## 第三阶段：Redis DAO 层

### 3.1 InteractionRedisDAO 接口定义

```java
public interface InteractionRedisDAO {
    
    /**
     * SETBIT: 标记用户点赞/收藏状态
     * @param interactionType: 互动类型 (like/favorite)
     * @param resourceId: 资源ID
     * @param userId: 用户ID
     * @param status: true=点赞, false=取消点赞
     */
    void setBitStatus(InteractionType interactionType, Long resourceId, Long userId, boolean status);
    
    /**
     * GETBIT: 查询用户是否已点赞
     */
    boolean getBitStatus(InteractionType interactionType, Long resourceId, Long userId);
    
    /**
     * HINCRBY: 增加资源的互动计数
     * @param interactionType: 互动类型
     * @param resourceId: 资源ID
     * @param delta: 增量（可为负，表示取消）
     * @return 更新后的计数值
     */
    Long incrementCount(InteractionType interactionType, Long resourceId, Long delta);
    
    /**
     * HGETALL: 获取资源的所有互动计数
     */
    Map<String, Long> getAllCounts(Long resourceId);
    
    /**
     * HGET: 获取特定互动类型的计数
     */
    Long getCount(InteractionType interactionType, Long resourceId);
    
    /**
     * SADD: 将资源ID加入脏数据集合
     * @param resourceId: 资源ID
     */
    void markDirty(Long resourceId);
    
    /**
     * SMEMBERS: 获取脏数据集合中的所有资源ID
     */
    Set<Long> getDirtyResources();
    
    /**
     * RENAME: 原子性重命名脏数据Key（用于定时任务）
     * @return true if key exists and renamed
     */
    boolean renameDirtySet(String fromKey, String toKey);
    
    /**
     * DEL: 删除Key
     */
    void delete(String key);
    
    /**
     * EXISTS: 检查Key是否存在
     */
    boolean exists(String key);
}
```

### 3.2 关键实现细节

#### 3.2.1 Bitmap 操作特性

```
SETBIT key offset value
- offset = userId % 2^32 (Redis支持最大 2^32-1 的offset)
- 单个 Bitmap 最大可存储 512MB，即 4294967295 个位
- 空间复杂度极低，推荐用于大规模用户状态存储
```

#### 3.2.2 Hash 操作原子性

```
HINCRBY key field increment
- 单个操作原子性，避免并发计数不准
- 支持负数 increment，用于取消点赞时的递减
```

#### 3.2.3 Set 操作与脏数据池

```
脏数据池生命周期：
1. 实时操作时: SADD interact:dirty_set:{yyyyMMddHH} resourceId
2. 每小时0分：RENAME interact:dirty_set:{oldHour} interact:dirty_set:processing
3. 同步任务处理 processing 集合
4. 同步完成后：DEL interact:dirty_set:processing
```

### 3.3 错误处理与超时

```java
// Redis 操作超时保护：500ms
@Value("${redis.timeout:500}")
private long redisTimeout;

// 脏数据集合保留时间：48小时
// 防止任务失败导致数据永久丢失
@Value("${redis.dirty.expire:172800}")
private long dirtyExpireSeconds;
```

---

## 第四阶段：核心业务 Service

### 4.1 InteractionService 接口定义

```java
public interface InteractionService {
    
    /**
     * 点赞操作 (有状态)
     * 
     * 流程：
     * 1. DB: 插入 status 表，检查是否已存在（幂等）
     * 2. Redis: 更新 Bitmap + Hash + Set
     * 3. Async: 发送 MQ 消息记录 log 表
     * 4. Return: 返回操作结果
     * 
     * @return {success: true, message: "点赞成功", delta: 1}
     */
    InteractionResult like(Long userId, Long resourceId, Integer resourceType);
    
    /**
     * 取消点赞 (有状态)
     * 
     * 流程：
     * 1. DB: 删除 status 表记录
     * 2. Redis: 更新 Bitmap + 递减 Hash + 标记 Set
     * 3. Async: 发送 MQ 消息记录 log 表
     * 4. Return: 返回操作结果
     */
    InteractionResult unlike(Long userId, Long resourceId, Integer resourceType);
    
    /**
     * 浏览操作 (无状态)
     * 
     * 流程：
     * 1. Redis: 增加 Hash 计数 + 标记 Set（无需Bitmap）
     * 2. Async: 发送 MQ 消息记录 log 表
     * 3. Return: 返回操作结果
     */
    InteractionResult view(Long userId, Long resourceId, Integer resourceType);
    
    /**
     * 分享操作 (无状态)
     * 
     * 流程：
     * 1. Redis: 增加 Hash 计数 + 标记 Set
     * 2. Async: 发送 MQ 消息记录 log 表
     * 3. Return: 返回操作结果
     */
    InteractionResult share(Long userId, Long resourceId, Integer resourceType);
    
    /**
     * 查询用户是否已点赞
     * 
     * 优先级：Redis Bitmap > DB status 表
     */
    boolean isLiked(Long userId, Long resourceId, Integer resourceType);
    
    /**
     * 获取资源的互动统计
     * 
     * 优先级：Redis Hash > DB accumulation 表
     */
    InteractionStats getStats(Long resourceId, Integer resourceType);
    
    /**
     * 批量查询用户的点赞状态
     */
    Map<Long, Boolean> batchIsLiked(Long userId, List<Long> resourceIds, Integer resourceType);
}
```

### 4.2 数据模型

```java
// 互动操作结果
@Data
public class InteractionResult {
    private boolean success;
    private String message;
    private int delta;           // 计数变化量 (+1/-1/0)
    private long currentCount;   // 当前计数值
    private LocalDateTime operatedAt;
}

// 互动统计
@Data
public class InteractionStats {
    private Long resourceId;
    private Integer resourceType;
    private Long likeCount;
    private Long favoriteCount;
    private Long viewCount;
    private Long shareCount;
    private LocalDateTime lastModified;
}
```

### 4.3 核心业务逻辑流程图

#### 4.3.1 点赞 (doLike) 完整流程

```
开始
  ↓
【检查】Redis 临时标记：interact:like:temp:{userId}:{resId}
  ├─ 存在 → 返回 "操作过于频繁，请稍候"，END
  └─ 不存在 → 继续
  ↓
【DB-WRITE】向 status 表插入记录
  ├─ 成功 → 获取主键ID，继续
  ├─ 唯一性冲突 → 返回 "已点赞"，END
  └─ 其他异常 → 返回 "系统错误"，END
  ↓
【CACHE-UPDATE-1】SETBIT interact:stat:like:{resId} userId 1
  ↓
【CACHE-UPDATE-2】HINCRBY interact:count:like:{resId} "like" 1
  ↓
【CACHE-UPDATE-3】SADD interact:dirty_set:{yyyyMMddHH} {resId}
  ↓
【CACHE-TEMP】SET interact:like:temp:{userId}:{resId} 1 EX 5  (防重)
  ↓
【ASYNC-SEND】向 MQ 发送 "点赞日志事件"
  ├─ 消息格式: {userId, resId, interactionType=LIKE, ...}
  └─ MQ 消费者异步写入 log 表
  ↓
【RETURN】返回成功，delta=+1
```

#### 4.3.2 取消点赞 (undoLike) 完整流程

```
开始
  ↓
【DB-DELETE】从 status 表删除记录 (使用 DELETE 而非软删除)
  ├─ 成功删除 → 获取删除行数，继续
  ├─ 未找到记录 → 返回 "未点赞"，END
  └─ 其他异常 → 返回 "系统错误"，END
  ↓
【CACHE-UPDATE-1】SETBIT interact:stat:like:{resId} userId 0
  ↓
【CACHE-UPDATE-2】HINCRBY interact:count:like:{resId} "like" -1  (递减)
  ↓
【CACHE-UPDATE-3】SADD interact:dirty_set:{yyyyMMddHH} {resId}
  ↓
【ASYNC-SEND】向 MQ 发送 "取消点赞日志事件"
  ↓
【RETURN】返回成功，delta=-1
```

#### 4.3.3 浏览 (doView) 流程

```
开始
  ↓
【CACHE-UPDATE】HINCRBY interact:count:view:{resId} "view" 1
  ↓
【CACHE-DIRTY】SADD interact:dirty_set:{yyyyMMddHH} {resId}
  ↓
【ASYNC-SEND】向 MQ 发送 "浏览日志事件"
  ├─ 无需检查重复 (浏览允许多次)
  └─ 异步写入 log 表
  ↓
【RETURN】返回成功，delta=+1
```

### 4.4 事务与一致性保证

```java
// 点赞操作的事务边界
@Transactional(rollbackFor = Exception.class)
public InteractionResult like(Long userId, Long resourceId, Integer resourceType) {
    try {
        // 第一步：DB 写入（事务内）
        int rows = statusMapper.insertStatus(...);
        if (rows == 0) {
            throw new BizException("点赞失败");
        }
        
        // 第二步：Redis 更新（事务外，即使失败也不回滚 DB）
        redisDAO.setBitStatus(...);
        redisDAO.incrementCount(...);
        redisDAO.markDirty(...);
        
        // 第三步：异步 MQ（事务外，解耦）
        mqTemplate.send(...);
        
        return InteractionResult.success(1);
    } catch (DuplicateException e) {
        // 已点赞，返回幂等结果
        return InteractionResult.exists();
    }
}
```

---

## 第五阶段：异步日志处理

### 5.1 消息队列集成

#### 5.1.1 事件定义

```java
@Data
public class InteractionLogEvent {
    private Long userId;
    private Long resourceId;
    private Integer resourceType;
    private Integer interactionType;  // 1=like, 2=favorite, 3=view, 4=share
    private String clientIp;
    private String userAgent;
    private Long operatedAt;          // 时间戳，毫秒
}
```

#### 5.1.2 异步发送方法

```java
// 在 Service 中调用
@Async("interactionExecutor")
public void sendInteractionLog(InteractionLogEvent event) {
    try {
        // 发送到 MQ (RabbitMQ / Kafka / Pulsar)
        template.convertAndSend("interaction.log.exchange", "interaction.log.*", event);
    } catch (Exception e) {
        // 记录失败日志，但不中断主流程
        logger.error("Failed to send interaction log: {}", event, e);
        // 可选：保存到本地队列或数据库，后续重试
    }
}
```

#### 5.1.3 消费端处理

```java
@RabbitListener(queues = "interaction.log.queue")
public void consumeInteractionLog(InteractionLogEvent event) {
    try {
        ContentInteractionLog log = new ContentInteractionLog();
        log.setUserId(event.getUserId());
        log.setResourceId(event.getResourceId());
        log.setResourceType(event.getResourceType());
        log.setInteractionType(event.getInteractionType());
        log.setClientIp(event.getClientIp());
        log.setUserAgent(event.getUserAgent());
        
        logMapper.insertLog(log);
    } catch (Exception e) {
        logger.error("Failed to consume interaction log: {}", event, e);
        // 消息进入死信队列，人工处理
        throw new RuntimeException(e);
    }
}
```

### 5.2 容错机制

| 故障场景 | 影响范围 | 处理策略 |
|---------|---------|---------|
| MQ 宕机 | 日志不记录 | 本地队列 + 重试机制 |
| 消费端异常 | 特定日志丢失 | 死信队列，人工介入 |
| 网络延迟 | 日志延迟 | 异步消费可接受 |

---

## 第六阶段：定时同步任务

### 6.1 InteractionSyncJob 接口定义

```java
public interface InteractionSyncJob {
    
    /**
     * 每小时第 0 分钟执行
     * 
     * 流程：
     * 1. 获取上一小时的脏数据 Key: interact:dirty_set:{yyyyMMddHH}
     * 2. RENAME 为 interact:dirty_set:processing（原子操作）
     * 3. 分批从 processing 读取 resourceId
     * 4. 批量查询 Redis 计数值
     * 5. 批量 upsert 到 accumulation 表
     * 6. 删除 processing Key
     */
    void syncInteractionCount();
}
```

### 6.2 定时任务的数据流转细节

#### 6.2.1 时间轮转逻辑

```
当前时间：2026-05-10 10:30
  ↓
计算目标小时：2026-05-10 09:00 (上一个整点)
  ↓
获取脏数据 Key：interact:dirty_set:202605100900
  ↓
原子重命名：
  RENAME interact:dirty_set:202605100900 
         interact:dirty_set:processing
  ↓
处理 processing 中的资源ID...
```

#### 6.2.2 批量同步伪代码

```java
@Scheduled(cron = "0 0 * * * *")  // 每小时0分执行
public void syncInteractionCount() {
    // 1. 计算上一小时的脏数据Key
    LocalDateTime lastHour = LocalDateTime.now().minusHours(1).withMinute(0).withSecond(0);
    String dirtyKey = "interact:dirty_set:" + formatHHHH(lastHour);
    String processingKey = "interact:dirty_set:processing";
    
    // 2. 原子重命名（防止重复处理）
    boolean renamed = redisDAO.renameDirtySet(dirtyKey, processingKey);
    if (!renamed) {
        logger.info("No dirty data for {}", dirtyKey);
        return;
    }
    
    try {
        // 3. 获取所有需要同步的资源ID
        Set<Long> resourceIds = redisDAO.getDirtyResources(processingKey);
        logger.info("Found {} dirty resources", resourceIds.size());
        
        // 4. 分批处理（单批500条，防止内存溢出）
        int batchSize = 500;
        for (int i = 0; i < resourceIds.size(); i += batchSize) {
            List<Long> batch = new ArrayList<>(
                resourceIds.stream()
                    .skip(i)
                    .limit(batchSize)
                    .collect(Collectors.toList())
            );
            
            // 5. 查询 Redis 计数
            List<InteractionDeltaDTO> deltas = new ArrayList<>();
            for (Long resourceId : batch) {
                Map<String, Long> counts = redisDAO.getAllCounts(resourceId);
                deltas.add(new InteractionDeltaDTO(
                    resourceId,
                    counts.getOrDefault("like", 0L),
                    counts.getOrDefault("favorite", 0L),
                    counts.getOrDefault("view", 0L),
                    counts.getOrDefault("share", 0L)
                ));
            }
            
            // 6. 批量 upsert 到 DB
            try {
                int upsertCount = accumulationMapper.batchUpsertCount(deltas);
                logger.info("Upserted {} records", upsertCount);
            } catch (Exception e) {
                logger.error("Failed to upsert batch", e);
                // 记录失败批次，继续处理下一批
            }
        }
        
        // 7. 删除 processing Key（标记完成）
        redisDAO.delete(processingKey);
        logger.info("Sync completed successfully");
        
    } catch (Exception e) {
        logger.error("Critical error during sync", e);
        // processing Key 保留，下次重试
        // 发送告警
    }
}
```

### 6.3 容错机制详解

#### 6.3.1 任务失败重试策略

| 失败场景 | 重试周期 | 最大重试次数 | 说明 |
|---------|---------|-----------|------|
| Redis 连接超时 | 立即重试 | 3次 | 暂时性故障 |
| DB 批量 upsert 失败 | 等待5分钟 | 3次 | 部分数据可能已写入 |
| 任务中断异常 | 下一小时自动继续 | 无限 | processing Key 保留 |

#### 6.3.2 脏数据池的自洗机制

```
脏数据Key的生命周期：
- 创建: interact:dirty_set:202605100800 (当小时)
- 转移: RENAME 为 interact:dirty_set:processing (下一小时0分)
- 完成: DEL processing (同步成功)
- 保留: processing 保留48小时 (容灾，防止数据永久丢失)

如果 processing 存在超过48小时：
  → 发送告警
  → 人工介入审查
  → 手动重试或删除
```

#### 6.3.3 数据一致性验证

```java
// 同步前的一致性检查
private boolean validateBeforeSync(List<InteractionDeltaDTO> deltas) {
    for (InteractionDeltaDTO delta : deltas) {
        // 检查 Redis 计数是否异常
        if (delta.getLikeCount() < 0 || delta.getViewCount() < 0) {
            logger.error("Negative count detected: {}", delta);
            return false;  // 中止本轮同步
        }
        
        // 检查与 status 表的一致性（点赞）
        Long dbCount = statusMapper.countByResourceAndType(
            delta.getResourceId(), 
            ResourceType.ARTICLE, 
            InteractionType.LIKE
        );
        
        if (!Objects.equals(dbCount, delta.getLikeCount())) {
            logger.warn("Inconsistency detected: Redis={}, DB={}", 
                delta.getLikeCount(), dbCount);
            // 记录但继续（最终一致性）
        }
    }
    return true;
}
```

---

## 第七阶段：配置 & 工具类

### 7.1 Redis 配置

```yaml
# application.yml
spring:
  redis:
    host: localhost
    port: 6379
    password: ${REDIS_PASSWORD}
    timeout: 500  # 毫秒
    database: 1
    
    jedis:
      pool:
        max-active: 20
        max-idle: 10
        min-idle: 5
        max-wait: -1ms
        
interaction:
  redis:
    dirty:
      expire: 172800  # 脏数据集合保留 48 小时
      batchSize: 500   # 单批处理大小
    temp:
      expire: 300      # 临时标记保留 5 分钟
```

### 7.2 异步线程池配置

```java
@Configuration
public class AsyncConfig {
    
    @Bean("interactionExecutor")
    public Executor interactionExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(10);
        executor.setMaxPoolSize(20);
        executor.setQueueCapacity(100);
        executor.setThreadNamePrefix("interaction-async-");
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setAwaitTerminationSeconds(60);
        return executor;
    }
}
```

### 7.3 定时任务配置

```java
@Configuration
public class ScheduleConfig {
    
    @Bean
    public TaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler scheduler = new ThreadPoolTaskScheduler();
        scheduler.setPoolSize(3);
        scheduler.setThreadNamePrefix("interaction-schedule-");
        scheduler.setWaitForTasksToCompleteOnShutdown(true);
        scheduler.setAwaitTerminationSeconds(60);
        return scheduler;
    }
}

// 定时任务 Bean
@Component
public class InteractionSyncJobImpl implements InteractionSyncJob {
    
    @Scheduled(cron = "0 0 * * * *")  // 每小时0分执行
    @Override
    public void syncInteractionCount() {
        // 实现见第六阶段
    }
}
```

### 7.4 通用工具类

```java
// 时间工具
public class DateTimeUtils {
    
    /**
     * 获取当前小时的 Key 后缀: yyyyMMddHH
     */
    public static String getCurrentHourKey() {
        return LocalDateTime.now()
            .format(DateTimeFormatter.ofPattern("yyyyMMddHH"));
    }
    
    /**
     * 获取指定小时的 Key 后缀
     */
    public static String getHourKey(LocalDateTime dateTime) {
        return dateTime.format(DateTimeFormatter.ofPattern("yyyyMMddHH"));
    }
}

// Redis Key 生成器
public class RedisKeyGenerator {
    
    public static String countKey(InteractionType type, Long resourceId) {
        return String.format("interact:count:%s:%d", type.name().toLowerCase(), resourceId);
    }
    
    public static String statKey(InteractionType type, Long resourceId) {
        return String.format("interact:stat:%s:%d", type.name().toLowerCase(), resourceId);
    }
    
    public static String dirtySetKey(String yyyyMMddHH) {
        return String.format("interact:dirty_set:%s", yyyyMMddHH);
    }
    
    public static String tempKey(InteractionType type, Long userId, Long resourceId) {
        return String.format("interact:%s:temp:%d:%d", 
            type.name().toLowerCase(), userId, resourceId);
    }
}
```

---

## 容错机制与数据完整性保证

### 8.1 故障场景分析表

| # | 故障场景 | 发生概率 | 影响范围 | 恢复策略 | 数据完整性 |
|---|--------|--------|--------|--------|----------|
| 1 | Redis 写入失败 | 低 | 实时计数不准 | Redis 重连重试，DB 最终同步 | ✅ 保证 |
| 2 | DB status 插入失败 | 极低 | 点赞操作失败 | 事务回滚，返回错误 | ✅ 保证 |
| 3 | MQ 消息丢失 | 低 | log 表无记录 | 异步，非关键数据 | ⚠️ 最终一致 |
| 4 | 定时任务超时 | 中 | 累计数据延迟同步 | processing Key 保留，下次重试 | ✅ 保证 |
| 5 | 网络分区 | 低 | 部分数据不可用 | 降级策略，使用缓存返回 | ⚠️ 可接受 |
| 6 | 并发重复点赞 | 中 | 同一用户重复点赞 | 唯一性约束 + 临时 Key | ✅ 保证 |

### 8.2 四层数据一致性保证

```
数据完整性金字塔：

┌─────────────────────────────────────┐
│  最强一致性: DB status 表           │ 实时同步，事务保护，唯一性约束
├─────────────────────────────────────┤
│  次强一致性: DB accumulation 表     │ 每小时同步一次，ON DUPLICATE KEY UPDATE
├─────────────────────────────────────┤
│  弱一致性: Redis (Hash+Bitmap)      │ 实时更新，可丢失，后续修复
├─────────────────────────────────────┤
│  最终一致性: 日志 log 表            │ 异步消费，可延迟，非关键
└─────────────────────────────────────┘
```

### 8.3 数据校验与修复机制

#### 8.3.1 每日数据对账

```java
@Scheduled(cron = "0 2 * * *")  // 每天凌晨2点
public void dailyDataReconciliation() {
    // 1. 从 status 表统计各资源的点赞数
    List<CountByResource> dbCounts = statusMapper.countGroupByResourceAndType();
    
    // 2. 从 accumulation 表获取当前计数
    List<InteractionAccumulation> currentAccums = accumulationMapper.findAll();
    
    // 3. 对比差异
    for (CountByResource dbCount : dbCounts) {
        Optional<InteractionAccumulation> accum = currentAccums.stream()
            .filter(a -> a.getResourceId().equals(dbCount.getResourceId()))
            .findFirst();
        
        if (accum.isEmpty() || !accum.get().getLikeCount().equals(dbCount.getCount())) {
            logger.warn("Data inconsistency detected: resourceId={}, expected={}, actual={}",
                dbCount.getResourceId(), dbCount.getCount(), 
                accum.map(InteractionAccumulation::getLikeCount).orElse(0L));
            
            // 修复：手动触发该资源的同步
            manualSyncResource(dbCount.getResourceId());
        }
    }
}
```

#### 8.3.2 Redis 缓存预热

```java
// 服务启动时预热高热资源
@PostConstruct
public void warmupRedisCache() {
    // 获取 TOP 1000 热点资源
    List<Long> hotResources = accumulationMapper
        .findTopByViewCountDesc(1000);
    
    for (Long resourceId : hotResources) {
        InteractionAccumulation accum = accumulationMapper
            .getByResourceId(resourceId);
        
        // 将计数加载到 Redis
        redisDAO.setCount(InteractionType.LIKE, resourceId, accum.getLikeCount());
        redisDAO.setCount(InteractionType.VIEW, resourceId, accum.getViewCount());
        // ...
    }
    
    logger.info("Redis cache warmed up for {} resources", hotResources.size());
}
```

---

## 实现检查清单

在开始编码前，请逐项确认以下设计：

### ✅ Redis Key 规范

- [ ] 确认 Key 命名规范: `interact:操作类型:维度:资源ID`
- [ ] 确认 Bitmap offset = userId (需处理大 uid)
- [ ] 确认脏数据 Set 按小时隔离: `interact:dirty_set:yyyyMMddHH`
- [ ] 确认临时 Key TTL = 5分钟 (防重)
- [ ] 确认所有 Redis Key 无中文，使用英文小写

### ✅ 数据库设计

- [ ] 确认 status 表的联合主键: (userId, resourceId, resourceType, interactionType)
- [ ] 确认 accumulation 表的唯一索引: (resourceId, resourceType)
- [ ] 确认 log 表的支撑索引: (resourceId, interactionType, gmtCreate)
- [ ] 确认批量 upsert SQL 使用 ON DUPLICATE KEY UPDATE
- [ ] 确认所有时间字段使用 LocalDateTime，格式统一

### ✅ Service 业务逻辑

- [ ] 确认点赞操作: 先 DB(强一致) → 再 Redis(最终一致)
- [ ] 确认取消点赞: DB DELETE + Redis SETBIT 0 + HINCRBY -1
- [ ] 确认浏览操作: 仅 Redis + 异步 log (无 Bitmap)
- [ ] 确认防重逻辑: 临时 Key (interact:like:temp:{uid}:{rid}, TTL=5min)
- [ ] 确认异常处理: DB 异常中止，Redis 异常记日志但不中止

### ✅ 异步消息

- [ ] 确认 MQ 选型: RabbitMQ / Kafka / Pulsar (项目已有)
- [ ] 确认事件结构: userId, resourceId, resourceType, interactionType, clientIp, userAgent
- [ ] 确认消费端: 幂等性处理，死信队列容错
- [ ] 确认发送方式: @Async 异步发送 (不阻塞主线程)

### ✅ 定时同步任务

- [ ] 确认执行频率: 每小时 0 分钟 (cron: 0 0 * * * *)
- [ ] 确认脏数据 RENAME: interact:dirty_set:{hour} → interact:dirty_set:processing
- [ ] 确认批量大小: 500条/批 (防内存溢出)
- [ ] 确认批量 upsert: 使用 ON DUPLICATE KEY UPDATE
- [ ] 确认超时保护: 如果任务失败，processing Key 保留 48小时后自动过期
- [ ] 确认完成标记: 同步成功后删除 processing Key

### ✅ 容错机制

- [ ] 确认四层数据一致性: DB status(强) > accumulation(最终) > Redis(弱) > log(最终)
- [ ] 确认故障重试: Redis 3次，DB 3次，定时任务无限重试
- [ ] 确认数据对账: 每日凌晨 2 点自动对账，检测并修复差异
- [ ] 确认监控告警: 记录所有关键操作的日志，异常情况发送告警
- [ ] 确认人工介入: 提供 API 手动触发资源同步或数据修复

### ✅ 性能优化

- [ ] 确认批量查询: batchIsLiked() 使用 Pipeline 减少网络 RTT
- [ ] 确认读写分离: 查询优先 Redis，回源 DB
- [ ] 确认缓存预热: 启动时加载 TOP 1000 热点资源
- [ ] 确认线程池: 异步任务单独线程池，核心 10，最大 20
- [ ] 确认连接池: Redis 最大 20，DB 连接池最大 50

---

## 总结

本任务清单涵盖了内容互动系统的完整架构设计，包括：

1. **多层存储架构**: 日志层、状态层、计数层、缓存层四层分离
2. **强一致 + 最终一致**: 关键数据强一致(status表)，计数数据最终一致(定时同步)
3. **异步解耦**: 日志记录异步化，避免主流程阻塞
4. **完善的容错**: 多级重试、脏数据保留、数据对账、人工恢复
5. **高效能**: 批量操作、缓存预热、连接池、线程池优化

**下一步**: 确认上述所有检查项无误后，即可开始代码实现阶段。

