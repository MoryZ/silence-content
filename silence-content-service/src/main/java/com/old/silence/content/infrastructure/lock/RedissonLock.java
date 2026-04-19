package com.old.silence.content.infrastructure.lock;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

/**
 * 分布式锁Redisson实现方案
 */
@Component
public class RedissonLock implements DistributedLock {

    private static final Logger logger = LoggerFactory.getLogger(RedissonLock.class);

    @Autowired
    private RedissonClient redissonClient;

    @Override
    public RLock lock(String lockKey) {
        //TODO 第一版上线为避免生产配置问题影响主流程catch了所有异常,20251205版本之后删除
        try {
            var lock = redissonClient.getLock(lockKey);
            logger.info("start lock,lockKey:{}", lockKey);
            lock.lock();
            logger.info("lock success,lockKey:{}", lockKey);
            return lock;
        } catch (Exception e) {
            logger.error("lock error", e);
            return null;
        }
    }

    @Override
    public RLock lock(String lockKey, TimeUnit unit, long timeout) {
        var lock = redissonClient.getLock(lockKey);
        logger.info("start lock,lockKey:{},timeout:{}", lockKey,timeout);
        lock.lock(timeout, unit);
        logger.info("lock success,lockKey:{},timeout:{}", lockKey, timeout);
        return lock;
    }

    @Override
    public boolean tryLock(String lockKey, TimeUnit unit, long leaseTime) {
        var lock = redissonClient.getLock(lockKey);
        try {
            logger.info("start tryLock,lockKey:{},leaseTime:{}", lockKey,leaseTime);
            return lock.tryLock(0L, leaseTime, unit);
        } catch (InterruptedException e) {
            logger.warn("try to lock for lockKey:{} failed.cause:", lockKey, e);
            return false;
        }
    }

    @Override
    public boolean tryLock(String lockKey, TimeUnit unit, long waitTime, long leaseTime) {
        var lock = redissonClient.getLock(lockKey);
        try {
            logger.info("start tryLock,lockKey:{},waitTime:{},leaseTime:{}", lockKey, waitTime, leaseTime);
            return lock.tryLock(waitTime, leaseTime, unit);
        } catch (InterruptedException e) {
            logger.warn("after waiting :{} try to lock for lockKey:{} failed.cause:", waitTime, lockKey, e);
            return false;
        }
    }

    @Override
    public void unlock(String lockKey) {
        //TODO 第一版上线为避免生产配置问题影响主流程catch了所有异常,20251205版本之后删除
        try {
            var lock = redissonClient.getLock(lockKey);
            if (lock.isLocked()) {
                logger.info("unlock,lockKey:{}", lockKey);
                lock.unlock();
            } else {
                logger.info("unlock isLocked false,");
            }
        } catch (Exception e) {
            logger.error("lock error", e);
        }
    }

    @Override
    public void unlock(RLock lock) {
        if (lock.isLocked()) {
            logger.info("unlock,lockName:{}", lock.getName());
            lock.unlock();
        }
    }
}
