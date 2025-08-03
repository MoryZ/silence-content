package com.old.silence.content.console.api;

import java.util.List;
import java.util.Map;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.old.silence.content.api.FoodClient;
import com.old.silence.content.console.api.assembler.FoodQueryMapper;
import com.old.silence.content.console.dto.FoodConsoleQuery;
import com.old.silence.content.console.vo.FoodConsoleView;
import com.old.silence.content.console.vo.Promotion;

/**
 * @author moryzang
 * @Description
 */
@RestController
@RequestMapping("/api/v1")
public class FoodResource {

    private final FoodClient foodClient;
    private final FoodQueryMapper foodQueryMapper;

    public FoodResource(FoodClient foodClient,
                        FoodQueryMapper foodQueryMapper) {
        this.foodClient = foodClient;
        this.foodQueryMapper = foodQueryMapper;
    }

    @GetMapping("/foods/index")
    public Map<String, Object> index() {
        return Map.of("img_swiper", List.of("/static/uploads/default/banner_1.png", "/static/uploads/default/banner_2.png", "/static/uploads/default/banner_3.png"),
                "img_ad", "/static/uploads/default/image_ad.png",
                "img_category", List.of("/static/uploads/default/bottom_1.jpg", "/static/uploads/default/bottom_2.jpg", "/static/uploads/default/bottom_3.jpg", "/static/uploads/default/bottom_1.jpg")
        );
    }

    @GetMapping(value = "/foods", params = {"pageNo", "pageSize"})
    public Page<FoodConsoleView> query(FoodConsoleQuery foodConsoleQuery, Pageable pageable) {
        var foodQuery = foodQueryMapper.convert(foodConsoleQuery);
        return foodClient.query(foodQuery, pageable, FoodConsoleView.class);
    }

    @GetMapping(value = "/foods", params = {"!pageNo", "!pageSize"})
    public Map<String, Object> query(FoodConsoleQuery foodConsoleQuery) {
        var foodQuery = foodQueryMapper.convert(foodConsoleQuery);
        var foods = foodClient.query(foodQuery, FoodConsoleView.class).subList(0, 10);
        var promotion = List.of(new Promotion("35", "12"));
        return Map.of("list", foods, "promotion", promotion);
    }
}
