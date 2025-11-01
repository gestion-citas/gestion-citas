package com.cibertec.gestioncitas.web.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DevToolsController {
    
    @GetMapping("/.well-known/appspecific/com.chrome.devtools.json")
    public ResponseEntity<Void> handleDevToolsRequest() {
        return ResponseEntity.ok().build();
    }
    
    @GetMapping("/error")
    public ResponseEntity<Void> handleError() {
        return ResponseEntity.ok().build();
    }
}
