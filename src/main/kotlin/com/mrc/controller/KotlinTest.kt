package com.mrc.controller
import com.mrc.framework.Global
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping
class KotlinTest {
    @GetMapping("/member/test")
        fun get(): String = Global.getDataInfo().lPad("안녕하세요", 10, ">>>")
}