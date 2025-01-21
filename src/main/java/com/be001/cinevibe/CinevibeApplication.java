package com.be001.cinevibe;

import com.be001.cinevibe.model.Movie;
import com.be001.cinevibe.model.User;
import com.be001.cinevibe.model.enums.MovieGenres;
import com.be001.cinevibe.model.enums.UserRole;
import com.be001.cinevibe.repository.MovieRepository;
import com.be001.cinevibe.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.time.LocalDateTime;
import java.util.Set;

@SpringBootApplication
public class CinevibeApplication {

    public static void main(String[] args) {
        SpringApplication.run(CinevibeApplication.class, args);
    }

}
