package com.be001.cinevibe.controller;

import com.be001.cinevibe.model.Cast;
import com.be001.cinevibe.service.CastService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/casts")
public class CastController {
    private final CastService castService;

    public CastController(CastService castService) {
        this.castService = castService;
    }

    @GetMapping
    public List<Cast> list() {
        return castService.getList();
    }

    @GetMapping("/{id}")
    public Cast getById(@PathVariable Long id) {
        return castService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void save(@RequestBody Cast cast) {
        castService.save(cast);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        castService.deleteById(id);
    }

    @PutMapping("/{id}")
    public void update(@PathVariable Long id, @RequestBody Cast cast) {
        castService.update(id, cast);
    }

}
