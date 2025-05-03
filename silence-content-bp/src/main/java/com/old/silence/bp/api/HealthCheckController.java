package com.old.silence.bp.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author MurrayZhang
 */
@RestController
public class HealthCheckController {

    @GetMapping("/health")
    public String health() {
        return "ok";
    }
}
