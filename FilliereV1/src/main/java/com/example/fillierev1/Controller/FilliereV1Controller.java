package com.example.fillierev1.Controller;

import com.example.fillierev1.DTO.FilliereRequest;
import com.example.fillierev1.DTO.FilliereResponse;
import com.example.fillierev1.Services.FilliereService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/fillieres") // <-- préfixe
public class FilliereV1Controller {

    private final FilliereService filliereService;

    public FilliereV1Controller(FilliereService filliereService) {
        this.filliereService = filliereService;
    }

    @PostMapping
    public ResponseEntity<FilliereResponse> create(@RequestBody FilliereRequest request) {
        FilliereResponse created = filliereService.create(request);
        return ResponseEntity
                .created(URI.create("/api/v1/fillieres/" + created.getIdfilliere())) // attention au nom du getter
                .body(created);
    }

    @GetMapping("/hello")
    public String hello() {
        return "✅ Microservice Filliere fonctionne correctement !";
    }
}
