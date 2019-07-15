package com.dev.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Created by zgq7 on 2019/7/9.
 */
public class BaseController {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<String> exception() {
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
