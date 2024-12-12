package com.example.app.poc.controller;

import com.example.exception.model.CustomTestException;
import com.google.common.collect.Maps;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class TestController {

    @GetMapping("/role1/test")
    public ResponseEntity<Object> testRole1EndPoint() {
        return ResponseEntity.status(HttpStatus.OK).body(Maps.immutableEntry("result", "OK"));
    }

    @GetMapping("/role1/test-custom-exception")
    public ResponseEntity<Object> testRole1CustomTestExceptionEndPoint() {
        throw new CustomTestException("a custom test exception occurred");
    }

    @GetMapping("/role2/test")
    public ResponseEntity<Object> testRole2EndPoint() {
        return ResponseEntity.status(HttpStatus.OK).body(Maps.immutableEntry("result", "OK"));
    }
}
