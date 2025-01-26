package com.be001.cinevibe.controller;


import com.be001.cinevibe.service.WatchListService;
import com.be001.cinevibe.dto.WatchListDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/watchlist")
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
    public ResponseEntity<WatchListDTO> createWatchList( @RequestParam Long userId,
                                                         @RequestParam String title,
                                                         @RequestBody(required = false) List<Long> movieIds) {
        return ResponseEntity.ok(watchListService.createWatchList(userId, title, movieIds));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWatchList(@PathVariable Long id){
        watchListService.removeWatchListById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{userId}/{movieId}")
    public ResponseEntity<String> addMovieToWatchList(@PathVariable("userId") Long userId, @PathVariable("movieId") Long movieId) throws RuntimeException{
        watchListService.addToWatchList(userId, movieId);
        return ResponseEntity.ok("Movie added successfully");
    }

    @DeleteMapping("/{userId}/{movieId}")
    public ResponseEntity<String> removeMovieFromWatchList(@PathVariable("userId") Long userId, @PathVariable("movieId") Long movieId) throws RuntimeException{
        watchListService.removeMovieFromUserWatchList(userId, movieId);
        return ResponseEntity.ok("Movie removed successfully");
    }
}
