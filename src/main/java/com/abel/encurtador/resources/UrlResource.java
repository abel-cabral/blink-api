package com.abel.encurtador.resources;

import com.abel.encurtador.services.UrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UrlResource {
    @Autowired
    private UrlService service;

    @GetMapping("/")
    ResponseEntity<String> encurteUrl() {
        return ResponseEntity.ok().body("Okay");
    }
}
