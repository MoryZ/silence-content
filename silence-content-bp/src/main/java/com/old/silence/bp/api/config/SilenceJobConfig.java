package com.old.silence.bp.api.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import com.old.silence.platform.job.center.core.executor.impl.SilenceJobSpringExecutor;

/**
 * @author MurrayZhang
 */
@Configuration
public class SilenceJobConfig {
    private Logger logger = LoggerFactory.getLogger(SilenceJobConfig.class);

    @Value("${silence.job.admin.addresses}")
    private String adminAddresses;

    @Value("${silence.job.admin.accessToken}")
    private String accessToken;

    @Value("${silence.job.admin.timeout}")
    private int timeout;

    @Value("${silence.job.executor.appname}")
    private String appname;

    @Value("${silence.job.executor.address}")
    private String address;

    @Value("${silence.job.executor.ip}")
    private String ip;

    @Value("${silence.job.executor.port}")
    private int port;

    @Value("${silence.job.executor.logpath}")
    private String logPath;

    @Value("${silence.job.executor.logretentiondays}")
    private int logRetentionDays;


    @Bean
    public SilenceJobSpringExecutor silenceJobExecutor() {
        logger.info(">>>>>>>>>>> silence-job config init.");
        SilenceJobSpringExecutor silenceJobSpringExecutor = new SilenceJobSpringExecutor();
        silenceJobSpringExecutor.setAdminAddresses(adminAddresses);
        silenceJobSpringExecutor.setAppname(appname);
        silenceJobSpringExecutor.setAddress(address);
        silenceJobSpringExecutor.setIp(ip);
        silenceJobSpringExecutor.setPort(port);
        silenceJobSpringExecutor.setAccessToken(accessToken);
        silenceJobSpringExecutor.setTimeout(timeout);
        silenceJobSpringExecutor.setLogPath(logPath);
        silenceJobSpringExecutor.setLogRetentionDays(logRetentionDays);

        return silenceJobSpringExecutor;
    }

    /**
     * 针对多网卡、容器内部署等情况，可借助 "spring-cloud-commons" 提供的 "InetUtils" 组件灵活定制注册IP；
     *
     *      1、引入依赖：
     *          <dependency>
     *             <groupId>org.springframework.cloud</groupId>
     *             <artifactId>spring-cloud-commons</artifactId>
     *             <version>${version}</version>
     *         </dependency>
     *
     *      2、配置文件，或者容器启动变量
     *          spring.cloud.inetutils.preferred-networks: 'xxx.xxx.xxx.'
     *
     *      3、获取IP
     *          String ip_ = inetUtils.findFirstNonLoopbackHostInfo().getIpAddress();
     */


}
