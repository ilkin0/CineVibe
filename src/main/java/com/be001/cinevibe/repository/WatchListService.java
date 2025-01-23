package com.be001.cinevibe.repository;

import com.be001.cinevibe.model.User;
import com.be001.cinevibe.model.WatchList;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WatchListService {

    private final WatchListRepository watchListRepository;
    private final UserRepository userRepository;
    private final MovieRepository movieRepository;


    public WatchListService(WatchListRepository watchListRepository, UserRepository userRepository, MovieRepository movieRepository) {
        this.watchListRepository = watchListRepository;
        this.userRepository = userRepository;
        this.movieRepository = movieRepository;
    }

    public List<WatchList> showWatchList(Long userId) {
        return watchListRepository.findByUserId(userId);
    }

    public void removeMovieFromUserWatchList(Long userId, Long movieId) throws RuntimeException{
        watchListRepository.deleteByUserIdAndMovieId(userId, movieId);
    }

    public void addToWatchList(Long userId, Long movieId) throws RuntimeException{
        watchListRepository.addMovieToUserWatchList(userId, movieId);
    }
}
