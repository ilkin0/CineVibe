package com.be001.cinevibe.repository;

import com.be001.cinevibe.model.WatchList;
import com.be001.cinevibe.repository.dto.MovieDTOMock;
import com.be001.cinevibe.repository.dto.WatchListDTO;
import com.be001.cinevibe.repository.exception.InvalidIdException;
import com.be001.cinevibe.repository.exception.UserNotFoundException;
import com.be001.cinevibe.repository.exception.WatchListNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

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

    public void removeMovieFromUserWatchList(Long userId, Long movieId) {
        if (userId == null || movieId == null) {
            throw new InvalidIdException("User id or movie id is null");
        }
        watchListRepository.deleteByUserIdAndMovieId(userId, movieId);
    }

    public void addToWatchList(Long userId, Long movieId) {
        watchListRepository.addMovieToUserWatchList(userId, movieId);
    }

    public WatchListDTO createWatchList(Long userId, String title, List<Long> movieIds) {
        WatchList watchList = watchListRepository.save(WatchList.builder()
                .user(userRepository.findById(userId).orElseThrow(
                        () -> new UserNotFoundException("User not found")))
                .movies(new HashSet<>(movieRepository.findAllById(movieIds)))
                .build());
        return mapWatchListToDTO(watchList);
    }

    private WatchListDTO mapWatchListToDTO(WatchList watchList) {
        return WatchListDTO.builder()
                .title(watchList.getTitle())
                .movieList(watchList.getMovies()
                        .stream().map(
                                movie -> MovieDTOMock.builder()
                                        .title(movie.getTitle())
                                        .averageRating(movie.getAverageRating())
                                        .releaseYear(movie.getReleaseYear())
                                        .posterImage(movie.getPosterImage())
                                        .build()).toList())
                .addedAt(watchList.getAddedAt())
                .build();
    }

}
