package com.dev.controllers;


import com.dev.utils.Persist;
import com.dev.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    @Autowired
    private Persist persist;
    @Autowired
    private Utils utils;



}
