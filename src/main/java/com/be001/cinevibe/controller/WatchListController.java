package com.be001.cinevibe.controller;


import com.be001.cinevibe.dto.WatchListDTO;
import com.be001.cinevibe.service.WatchListService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/watchlists")
public class WatchListController {

    private final WatchListService watchListService;

    public WatchListController(WatchListService watchListService) {
        this.watchListService = watchListService;
    }

    @GetMapping("/list")
    public ResponseEntity<List<WatchListDTO>> allWatchList(@RequestParam Long userId) {
        return ResponseEntity.ok(watchListService.showAllWatchList(userId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<WatchListDTO> getWatchList(@PathVariable Long id) {
        return ResponseEntity.ok(watchListService.showWatchListById(id));
    }

    @PostMapping
    public ResponseEntity<WatchListDTO> createWatchList(@RequestParam Long userId,
                                                        @RequestParam String title,
                                                        @RequestBody(required = false) List<Long> movieIds) {
        return ResponseEntity.ok(watchListService.createWatchList(userId, title, movieIds));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWatchList(@PathVariable Long id) {
        watchListService.removeWatchListById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{userId}/{watchListId}/movies/{movieId}")
    public ResponseEntity<String> addMovieToWatchList(@PathVariable("userId") Long userId, @PathVariable("watchListId") Long watchListId, @PathVariable("movieId") Long movieId) throws RuntimeException {
        watchListService.addToWatchList(userId, watchListId, movieId);
        return ResponseEntity.ok("Movie added successfully");
    }

    //userId will be removed from path once we can get user from Context

    @DeleteMapping("/{userId}/{watchListId}/movies/{movieId}")
    public ResponseEntity<String> removeMovieFromWatchList(@PathVariable("userId") Long userId, @PathVariable("watchListId") Long watchListId, @PathVariable("movieId") Long movieId) throws RuntimeException {
        watchListService.removeMovieFromUserWatchList(userId, watchListId, movieId);
        return ResponseEntity.ok("Movie removed successfully");
    }
}
