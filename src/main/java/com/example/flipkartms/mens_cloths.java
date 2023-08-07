package com.example.flipkartms;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
public class mens_cloths {
    @GetMapping("/mens_cloths")
    public String getData() {
        return "Please prebook your cloths,Premium offer is getting now ";
    }
}