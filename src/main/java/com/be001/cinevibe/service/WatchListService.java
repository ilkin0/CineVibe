package com.be001.cinevibe.service;

import com.be001.cinevibe.model.Movie;
import com.be001.cinevibe.model.User;
import com.be001.cinevibe.model.WatchList;
import com.be001.cinevibe.repository.MovieRepository;
import com.be001.cinevibe.repository.UserRepository;
import com.be001.cinevibe.repository.WatchListRepository;
import com.be001.cinevibe.dto.MovieDTO;
import com.be001.cinevibe.dto.WatchListDTO;
import com.be001.cinevibe.exception.InvalidIdException;
import com.be001.cinevibe.exception.UserNotFoundException;
import com.be001.cinevibe.exception.WatchListNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class WatchListService {

    private final WatchListRepository watchListRepository;
    private final UserRepository userRepository;
    private final MovieRepository movieRepository;


    public WatchListService(WatchListRepository watchListRepository,
                            UserRepository userRepository, MovieRepository movieRepository) {
        this.watchListRepository = watchListRepository;
        this.userRepository = userRepository;
        this.movieRepository = movieRepository;
    }

    public WatchListDTO showWatchListById(Long watchListId) {
        WatchList watchList = watchListRepository.findWatchListById(watchListId)
                .orElseThrow(() -> new WatchListNotFoundException("WatchList not found"));
        return mapWatchListToDTO(watchList);
    }


    public List<WatchListDTO> showAllWatchList(Long userId) {
        List<WatchList> allWatchList = watchListRepository.findAllByUserId(userId);
        return allWatchList.stream().map(this::mapWatchListToDTO).toList();
    }

    public void removeWatchListById(Long watchListId) {
        if (!watchListRepository.existsById(watchListId)) {
            throw new WatchListNotFoundException("WatchList not found");
        }
        watchListRepository.deleteById(watchListId);
    }

    public void removeMovieFromUserWatchList(Long userId, Long watchListId, Long movieId) {
        if (userId == null || movieId == null || watchListId == null) {
            throw new InvalidIdException("User id or movie id is null");
        }
        watchListRepository.deleteByUserIdAndMovieId(userId, watchListId, movieId);
    }

    public void addToWatchList(Long userId, Long watchListId, Long movieId) {

        boolean exists = watchListRepository.existsByUserWatchListAndMovie(userId, watchListId, movieId);

        if(exists){
            throw new IllegalArgumentException("This movie is already added to the specified watchlist");
        }
        watchListRepository.addMovieToUserWatchList(userId, watchListId, movieId);
    }

    public WatchListDTO createWatchList(Long userId, String title, List<Long> movieIds) {
        User user = userRepository.findById(userId).orElseThrow(
                () -> new UserNotFoundException("User not found"));
        Set<Movie> movies = new HashSet<>(movieRepository.findAllById(movieIds));

        WatchList watchList = watchListRepository.save(WatchList.builder()
                .user(user)
                .movies(movies)
                .build());
        return mapWatchListToDTO(watchList);
    }

    private WatchListDTO mapWatchListToDTO(WatchList watchList) {
        return WatchListDTO.builder()
                .title(watchList.getTitle())
                .movieList(watchList.getMovies()
                        .stream().map(
                                movie -> MovieDTO.builder()
                                        .title(movie.getTitle())
                                        .averageRating(movie.getAverageRating())
                                        .releaseYear(movie.getReleaseYear())
                                        .posterImage(movie.getPosterImage())
                                        .build()).toList())
                .addedAt(watchList.getAddedAt())
                .build();
    }

}
