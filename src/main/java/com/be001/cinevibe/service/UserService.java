package com.be001.cinevibe.service;

import com.be001.cinevibe.dto.UserProfileDTO;
import com.be001.cinevibe.exceptions.NoDataFound;
import com.be001.cinevibe.mapper.ProfileMapper;
import com.be001.cinevibe.model.User;
import com.be001.cinevibe.repository.UserRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Slf4j
@Service
public class UserService {

    private final UserRepository repository;

    private final ProfileMapper mapper;

    public UserService(UserRepository userRepository, @Qualifier("profileMapperImpl") ProfileMapper mapper) {
        this.repository = userRepository;
        this.mapper = mapper;
    }

    public User findByUsername(String username){
        return repository.findByUsername(username).orElseThrow();
    }

    public UserProfileDTO getProfile() throws NoDataFound {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof User user) {
            log.info("Get profile request by " + user.getEmail());
            return mapper.toProfile(user);
        }
        throw new NoDataFound("No principal found!");
    }

    public UserProfileDTO updateUsername(String username) throws NoDataFound {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof User user) {
            user.setUsername(username);
            log.info("Change profile request by " + user.getEmail());
            return mapper.toProfile(repository.save(user));
        }
        throw new NoDataFound("No principal found!");
    }

    public UserProfileDTO updateEmail(String email) throws NoDataFound {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (principal instanceof User user) {
            user.setEmail(email);
            log.info("Email update request by " + email);
            return mapper.toProfile(repository.save(user));
        }
        throw new NoDataFound("No Principal Found");
    }

    public UserProfileDTO updateProfilePicture(MultipartFile file) throws NoDataFound, IOException, IOException {
        if (file.isEmpty()) {
            throw new NoDataFound("No file chosen!");
        }


        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof User user) {

            String uploadDir = System.getProperty("user.dir") + "/profile-pictures";
            String fileName = user.getEmail() + "_" + file.getOriginalFilename();
            File targetFile = new File(uploadDir, fileName);

            if (!targetFile.getParentFile().exists()) {
                targetFile.getParentFile().mkdirs();
            }

            file.transferTo(targetFile);

            String fileUrl = "/profile-pictures/" + fileName;

            UserProfileDTO userProfile = getProfile();
            userProfile.setUrlProfile(fileUrl);

            repository.save(mapper.toEntity(user, userProfile));
            log.info("Profile picture update request by " + user.getEmail());
            return userProfile;
        }
        throw new NoDataFound("No principal found!");

    }

    public void deactivateAccount(Long id) throws NoDataFound {
        User user = repository.findById(id).orElseThrow(() -> new NoDataFound("No user found by given id"));
        user.setEnabled(false);
        repository.save(user);
        log.warn("Account is disabled: " + user.getEmail());
    }

    public void activateAccount(Long id) throws NoDataFound {
        User user = repository.findById(id).orElseThrow(() -> new NoDataFound("No user found by given id"));
        user.setEnabled(true);
        repository.save(user);
        log.warn("Account is enabled: " + user.getEmail());
    }

    public List<UserProfileDTO> findAllProfiles(Pageable pageable) {
        return repository.findAll(pageable).stream().map(mapper::toProfile).toList();
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
        log.warn("Account is deleted by id" + id);
    }

    public User addFollowers(Long followingId) throws NoDataFound {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof User follower) {

            User following = repository.findById(followingId)
                    .orElseThrow(() -> new RuntimeException("Following not found"));

            follower.getFollows().add(following);
            return repository.save(follower);
        }
        throw new NoDataFound("No principal found!");
    }

    public User removeFollowers(Long followingId) throws NoDataFound {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof User follower) {

            User following = repository.findById(followingId)
                    .orElseThrow(() -> new RuntimeException("Following not found"));

            follower.getFollows().remove(following);
            return repository.save(follower);
        }
        throw new NoDataFound("No principal found!");
    }

    public boolean existsByUsername(String username) {
        boolean b = repository.existsByUsername(username);
        if (!b) log.info("User with username :" + username + " doesn't exist.");
        return b;
    }
    public boolean existsByEmail(String email) {
        boolean b = repository.existsByUsername(email);
        if (!b) log.info("User with email :" + email + " doesn't exist.");
        return b;
    }

    public User save(User user){
        log.info("User saved.");
        return repository.save(user);
    }
}
