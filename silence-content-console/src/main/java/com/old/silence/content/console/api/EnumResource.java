package com.old.silence.content.console.api;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.old.silence.content.console.vo.EnumDatas;

@RestController
@RequestMapping("/api/v1")
public class EnumResource {

    /**
     * 获取所有枚举数据
     */
    @GetMapping("/enums")
    public EnumDatas getAllEnums() {
        return EnumDatas.getInstance();
    }

}
