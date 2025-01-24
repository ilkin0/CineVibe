package com.be001.cinevibe.service;

import com.be001.cinevibe.dto.UserProfile;
import com.be001.cinevibe.exceptions.NoDataFound;
import com.be001.cinevibe.mapper.ProfileMapper;
import com.be001.cinevibe.model.User;
import com.be001.cinevibe.repository.UserRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
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

    public UserProfile updateProfilePicture(MultipartFile file) throws NoDataFound, IOException, IOException {
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

            UserProfile userProfile = getProfile();
            userProfile.setUrlProfile(fileUrl);

            repository.save(mapper.toEntity(user, userProfile));

            return userProfile;
        }
        throw new NoDataFound("No principal found!");

    }

    public void deactivateAccount(Long id) throws NoDataFound {
        User user = repository.findById(id).orElseThrow(() -> new NoDataFound("No user found by given id"));
        user.setEnabled(false);
        repository.save(user);
    }

    public void activateAccount(Long id) throws NoDataFound {
        User user = repository.findById(id).orElseThrow(() -> new NoDataFound("No user found by given id"));
        user.setEnabled(true);
        repository.save(user);
    }

    public List<UserProfile> findAllProfiles(Pageable pageable) {
        return repository
                .findAll(pageable)
                .stream()
                .map(mapper::toProfile)
                .toList();
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
    }
}
