package com.example.flipkartms;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
@RestController
public class womnes_cloths {
    @GetMapping("/womens_cloths")
    public String getData() {
        return "Please Prebook your new cloths and confirm for payment ";
    }
}