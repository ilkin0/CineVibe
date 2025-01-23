package com.be001.cinevibe.repository;

import com.be001.cinevibe.model.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class WatchListService {

    private final UserRepository userRepository;
    private final MovieRepository movieRepository;


    public WatchListService(UserRepository userRepository, MovieRepository movieRepository) {
        this.userRepository = userRepository;
        this.movieRepository = movieRepository;
    }

    public void addToWatchList(Long userId, Long movieId){
        User user = userRepository.findById(userId).orElseThrow(() -> {
            throw new UsernameNotFoundException("User not found");
        });

        movieRepository.findById(movieId).orElseThrow(() -> {
            throw new RuntimeException("Movie not found");
        });


    }
}
