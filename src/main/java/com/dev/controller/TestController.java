package com.dev.controller;

import com.google.common.collect.ImmutableMap;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * Created by zgq7 on 2019/6/6.
 */
@RestController
@RequestMapping(value = "/dev")
public class TestController extends BaseController {

    @GetMapping(value = "")
    public Map<Object, Object> get() {
        return ImmutableMap.of("code", "get");
    }

    @PutMapping(value = "t")
    public Map<Object, Object> t() {
        return ImmutableMap.of("code", "put");
    }

    @PostMapping(value = "s")
    public Map<Object, Object> s() {
        return ImmutableMap.of("code", "post");
    }

}
