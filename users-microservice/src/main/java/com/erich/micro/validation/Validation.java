package com.erich.micro.validation;

import org.springframework.validation.BindingResult;

import java.util.HashMap;
import java.util.Map;

public class Validation {
    public static Map<String, String> Validators(BindingResult bindingResult) {
        Map<String, String> map = new HashMap<>();
        bindingResult.getFieldErrors().forEach(e -> {
            map.put(e.getField(), "The Field " + e.getField() + " " + e.getDefaultMessage());
        });
        return map;
    }

}
