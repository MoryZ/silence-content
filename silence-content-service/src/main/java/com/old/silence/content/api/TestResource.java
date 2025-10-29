package com.old.silence.content.api;

import org.springframework.boot.SpringApplication;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Arrays;
import java.util.Base64;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author moryzang
 */
@RestController
@RequestMapping("/api/v1/ai")
public class TestResource {
    private final RestTemplate restTemplate = new RestTemplate();
    private final ObjectMapper objectMapper = new ObjectMapper();

    /*public static void main(String[] args) {
        SpringApplication.run(TestResource.class, args);
        System.out.println("🚀 AI 服务已启动: http://localhost:8080");
        System.out.println("📝 测试接口: POST http://localhost:8080/api/ai/generate-prompt");
        System.out.println("🎨 文生图接口: POST http://localhost:8080/api/ai/generate-image-sd");
    }*/

    /**
     * 生成图片提示词（使用 Ollama）
     */
    @PostMapping("/generate-prompt")
    public Map<String, Object> generatePrompt(@RequestBody Map<String, String> request) {
        String theme = request.get("theme");
        System.out.println("🎯 收到提示词生成请求: " + theme);

        try {
            String prompt = generateImagePromptWithOllama(theme);

            Map<String, Object> result = new HashMap<>();
            result.put("success", true);
            result.put("theme", theme);
            result.put("prompt", prompt);
            result.put("timestamp", new Date());

            System.out.println("✅ 生成的提示词: " + prompt);
            return result;

        } catch (Exception e) {
            System.err.println("❌ 生成提示词失败: " + e.getMessage());

            Map<String, Object> result = new HashMap<>();
            result.put("success", false);
            result.put("error", e.getMessage());
            return result;
        }
    }

    /**
     * 文生图（使用 Stable Diffusion）
     */
    @PostMapping("/generate-image-sd")
    public ResponseEntity<?> generateImageSD(@RequestBody Map<String, String> request) {
        String prompt = request.get("prompt");
        System.out.println("🎨 收到文生图请求: " + prompt);

        try {
            // 如果没安装 SD，返回模拟结果
            if (!isStableDiffusionRunning()) {
                System.out.println("⚠️  Stable Diffusion 未运行，返回模拟响应");
                return simulateImageResponse(prompt);
            }

            byte[] imageData = generateImageWithSD(prompt);

            System.out.println("✅ 图片生成成功，大小: " + imageData.length + " bytes");
            return ResponseEntity.ok()
                    .contentType(MediaType.IMAGE_PNG)
                    .body(imageData);

        } catch (Exception e) {
            System.err.println("❌ 文生图失败: " + e.getMessage());
            return ResponseEntity.badRequest()
                    .body(Map.of("success", false, "error", e.getMessage()));
        }
    }

    /**
     * 调用 Ollama 生成提示词
     */
    private String generateImagePromptWithOllama(String theme) {
        String ollamaUrl = "http://localhost:11434/api/generate";

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("model", "llama3.1:8b");
        requestBody.put("prompt", "请为以下主题生成一个详细的英文图片描述提示词，包含场景、风格、光线、色彩等细节。主题：" + theme);
        requestBody.put("stream", false);

        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(
                    ollamaUrl, requestBody, Map.class);

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                return (String) response.getBody().get("response");
            }
        } catch (Exception e) {
            System.err.println("❌ Ollama 调用失败，返回模拟提示词");
            // 返回模拟提示词
            return "masterpiece, best quality, 1girl, " + theme +
                    ", detailed background, cinematic lighting, vibrant colors, 4k resolution";
        }

        return "high quality image of " + theme + ", detailed, professional photography";
    }

    /**
     * 调用 Stable Diffusion 生成图片
     */
    private byte[] generateImageWithSD(String prompt) {
        String sdUrl = "http://localhost:7860/sdapi/v1/txt2img";

        Map<String, Object> requestBody = new HashMap<>();
        requestBody.put("prompt", prompt);
        requestBody.put("negative_prompt", "poor quality, blurry, distorted, bad anatomy");
        requestBody.put("steps", 20);
        requestBody.put("width", 512);
        requestBody.put("height", 512);
        requestBody.put("cfg_scale", 7.0);
        requestBody.put("sampler_name", "DPM++ 2M Karras");

        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(
                    sdUrl, requestBody, Map.class);

            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                List<String> images = (List<String>) response.getBody().get("images");
                if (images != null && !images.isEmpty()) {
                    return Base64.getDecoder().decode(images.get(0));
                }
            }
        } catch (Exception e) {
            throw new RuntimeException("Stable Diffusion 调用失败: " + e.getMessage());
        }

        throw new RuntimeException("生成图片失败");
    }

    /**
     * 检查 Stable Diffusion 是否运行
     */
    private boolean isStableDiffusionRunning() {
        try {
            restTemplate.getForEntity("http://localhost:7860", String.class);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 模拟图片响应（当 SD 未运行时）
     */
    private ResponseEntity<?> simulateImageResponse(String prompt) {
        Map<String, Object> result = new HashMap<>();
        result.put("success", true);
        result.put("message", "Stable Diffusion 未运行，这是模拟响应");
        result.put("prompt", prompt);
        result.put("suggested_actions", Arrays.asList(
                "1. 安装 Stable Diffusion WebUI",
                "2. 启动服务: python launch.py --listen",
                "3. 访问: http://localhost:7860"
        ));
        result.put("timestamp", new Date());

        return ResponseEntity.ok(result);
    }

    /**
     * 健康检查
     */
    @GetMapping("/health")
    public Map<String, Object> health() {
        Map<String, Object> health = new HashMap<>();
        health.put("status", "UP");
        health.put("service", "Simple AI Main");
        health.put("timestamp", new Date());
        health.put("ollama_available", checkOllama());
        health.put("sd_available", isStableDiffusionRunning());
        return health;
    }

    private boolean checkOllama() {
        try {
            restTemplate.getForEntity("http://localhost:11434", String.class);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
}
