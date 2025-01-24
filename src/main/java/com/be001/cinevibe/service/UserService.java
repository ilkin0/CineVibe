package com.be001.cinevibe.service;

import com.be001.cinevibe.dto.UserProfile;
import com.be001.cinevibe.exceptions.NoDataFound;
import com.be001.cinevibe.mapper.ProfileMapper;
import com.be001.cinevibe.model.User;
import com.be001.cinevibe.repository.UserRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class UserService {
    Logger log = Logger.getLogger(UserService.class.getName());

    private final UserRepository repository;

    private final ProfileMapper mapper;

    public UserService(UserRepository userRepository, @Qualifier("profileMapperImpl") ProfileMapper mapper) {
        this.repository = userRepository;
        this.mapper = mapper;
    }

    public User findByUsername(String username) {
        return null;
    }

    public UserProfile getProfile() throws NoDataFound {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof User user) {
            log.info("Get profile request by " + user.getEmail());
            return mapper.toProfile(user);
        }
        throw new NoDataFound("No principal found!");
    }

    public UserProfile updateUsername(String username) throws NoDataFound {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof User user) {
            user.setUsername(username);
            log.info("Change profile request by " + user.getEmail());
            return mapper.toProfile(repository.save(user));
        }
        throw new NoDataFound("No principal found!");
    }

    public UserProfile updateEmail(String email) throws NoDataFound {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof User user) {
            user.setEmail(email);
            return mapper.toProfile(repository.save(user));
        }
        throw new NoDataFound("No Principal Found");
    }
}
