package com.PhysicalTrack.test;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TestController {
	@ResponseBody
    @GetMapping("/test1")
    public String helloWorld() {
        return "IT's PT Project. Hello world!!!";
    }
}