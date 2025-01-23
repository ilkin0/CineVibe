package com.be001.cinevibe.repository;


import com.be001.cinevibe.model.WatchList;
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
    public List<WatchList> allWatchList(@RequestParam Long userId){
        return watchListService.showWatchList(userId);
    }

    @PostMapping("/{userId}/movies/{movieId}")
    public ResponseEntity<String> addMovieToWatchList(@PathVariable("userId") Long userId, @PathVariable("movieId") Long movieId) throws RuntimeException{
        watchListService.addToWatchList(userId, movieId);
        return ResponseEntity.ok("Movie added successfully");
    }

    @DeleteMapping("/{userId}/movies/{movieId}")
    public ResponseEntity<String> removeMovieFromWatchList(@PathVariable("userId") Long userId, @PathVariable("movieId") Long movieId) throws RuntimeException{
        watchListService.removeMovieFromUserWatchList(userId, movieId);
        return ResponseEntity.ok("Movie removed successfully");
    }
}
