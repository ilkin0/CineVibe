package com.be001.cinevibe.controller;

import com.be001.cinevibe.model.dto.CommentDTO;
import com.be001.cinevibe.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/comment")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    @GetMapping
    public ResponseEntity<List<CommentDTO>> getAll() {
        return ResponseEntity.ok(commentService.getAll());
    }

    @GetMapping("{id}")
    public ResponseEntity<CommentDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(commentService.getById(id));
    }

    @PostMapping
    public ResponseEntity<CommentDTO> create(@RequestBody String content) {
        return ResponseEntity.ok(commentService.create(content));
    }

}
