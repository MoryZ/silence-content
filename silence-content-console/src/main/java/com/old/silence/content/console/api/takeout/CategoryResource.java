package com.old.silence.content.console.api.takeout;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.old.silence.content.api.CategoryClient;
import com.old.silence.content.api.dto.CategoryQuery;
import com.old.silence.content.console.vo.CategoryConsoleView;
import com.old.silence.content.console.vo.Promotion;

import java.util.List;
import java.util.Map;

/**
 * @author moryzang
 */
@RestController
@RequestMapping("/api/v1")
public class CategoryResource {

    private final CategoryClient categoryClient;

    public CategoryResource(CategoryClient categoryClient) {
        this.categoryClient = categoryClient;
    }

    @GetMapping(value = "/categories", params = {"!pageNo", "!pageSize"})
    public Map<String, Object> query() {
        CategoryQuery categoryQuery = new CategoryQuery();
        var foods = categoryClient.queryAll(categoryQuery, CategoryConsoleView.class);
        var promotion = List.of(new Promotion("35", "12"));
        return Map.of("list", foods, "promotion", promotion);
    }
}
