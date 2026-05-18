package com.old.silence.bp.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import com.old.silence.job.client.core.annotation.JobExecutor;
import com.old.silence.job.client.core.dto.JobArgs;
import com.old.silence.job.client.core.executor.AbstractJobExecutor;
import com.old.silence.job.common.client.dto.ExecuteResult;
import com.old.silence.json.JacksonMapper;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import static com.old.silence.bp.job.ContentInteractionSyncJob.JOB_NAME;

/**
 * @author moryzang
 */
@Component
@JobExecutor(name = JOB_NAME)
public class TestWorkFlowAnnotationJobExecutor extends AbstractJobExecutor {

    public final String JOB_NAME = "testWorkFlowAnnotationJobExecutor";
    private static final Logger log = LoggerFactory.getLogger(TestWorkFlowAnnotationJobExecutor.class);
    private final JacksonMapper jacksonMapper;

    public TestWorkFlowAnnotationJobExecutor(JacksonMapper jacksonMapper) {
        this.jacksonMapper = jacksonMapper;
    }


    @Override
    protected ExecuteResult doJobExecute(JobArgs jobArgs) {
        log.info("{}. param:{}", JOB_NAME, jacksonMapper.toJson(jobArgs));
        executeCommand(jacksonMapper.toJson(jobArgs.getJobParams()));
        return ExecuteResult.success("测试获取成功");
    }

    private static String executeCommand(String command) {
        StringBuilder output = new StringBuilder();
        ProcessBuilder processBuilder;

        // 根据不同操作系统选择命令
        String os = System.getProperty("os.name").toLowerCase();
        if (os.contains("win")) {
            // Windows: ping 命令
            processBuilder = new ProcessBuilder("cmd.exe", "/c", command);
        } else {
            // Linux/Mac: 需要拆分命令
            String[] cmdParts = command.split(" ");
            processBuilder = new ProcessBuilder(cmdParts);
        }

        try {
            Process process = processBuilder.start();
            BufferedReader reader = new BufferedReader(
                    new InputStreamReader(process.getInputStream())
            );

            String line;
            while ((line = reader.readLine()) != null) {
                output.append(line).append("\n");
            }

            int exitCode = process.waitFor();
            if (exitCode != 0) {
                // 读取错误流
                BufferedReader errorReader = new BufferedReader(
                        new InputStreamReader(process.getErrorStream())
                );
                while ((line = errorReader.readLine()) != null) {
                    output.append("ERROR: ").append(line).append("\n");
                }
            }

        } catch (Exception e) {
            output.append("执行失败: ").append(e.getMessage());
        }

        return output.toString();
    }

}