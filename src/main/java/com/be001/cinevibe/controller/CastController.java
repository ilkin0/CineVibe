package com.be001.cinevibe.controller;

import com.be001.cinevibe.model.Cast;
import com.be001.cinevibe.service.CastService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1//casts")
public class CastController {
    private final CastService castService;

    public CastController(CastService castService) {
        this.castService = castService;
    }

    @GetMapping
    public ResponseEntity<List<Cast>> list() {
        return ResponseEntity.ok(castService.getList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cast> getById(@PathVariable Long id) {
        return ResponseEntity.ok(castService.findById(id));
    }

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody Cast cast) {
        castService.save(cast);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        castService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(@PathVariable Long id, @RequestBody Cast cast) {
        castService.update(id, cast);
        return ResponseEntity.ok().build();
    }
}
