package com.old.silence.bp.job;

import org.springframework.stereotype.Component;
import com.old.silence.platform.job.center.core.handler.IJobHandler;

/**
 * @author MurrayZhang
 */
@Component
public class HelloWorldHandler extends IJobHandler {

    @Override
    public void execute() {
        System.out.println("Hello World");
    }
}
