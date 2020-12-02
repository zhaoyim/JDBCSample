package com.sample.controller;

import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@RequestMapping("sample/v1/query")
@RestController
public class TestController {
    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String hello() {
        return "Hello World!";
    }

    @PostMapping(value = "")
    public Object createQuery(@RequestBody String statement, HttpServletRequest request, HttpServletResponse response) {
        List<Object> l = new ArrayList<Object>();
        l.add("aaa");
        l.add("bbb");
        l.add("ccc");
        l.add(statement);
        return l;
    }
}
