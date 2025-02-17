package com.be001.cinevibe.service;

import com.be001.cinevibe.dto.UserProfileDTO;
import com.be001.cinevibe.exceptions.NoDataFound;
import com.be001.cinevibe.mapper.ProfileMapper;
import com.be001.cinevibe.model.User;
import com.be001.cinevibe.repository.UserRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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

    public UserProfileDTO getProfile() throws NoDataFound {
        Optional<User> currentPrincipal = getPrincipal();
        if (currentPrincipal.isPresent()) return mapper.toProfile(currentPrincipal.get());
        throw new NoDataFound("No principal found!");
    }

    public UserProfileDTO updateUsername(String username) throws NoDataFound {
        Optional<User> currentPrincipal = getPrincipal();
        if (currentPrincipal.isPresent()) {
            currentPrincipal.get().setUsername(username);
            return mapper.toProfile(repository.save(currentPrincipal.get()));
        }
        throw new NoDataFound("No principal found!");
    }

    public UserProfileDTO updateEmail(String email) throws NoDataFound {
        Optional<User> currentPrincipal = getPrincipal();
        if (currentPrincipal.isPresent()) {
            currentPrincipal.get().setEmail(email);
            return mapper.toProfile(repository.save(currentPrincipal.get()));
        }
        throw new NoDataFound("No Principal Found");
    }

//    public UserProfileDTO updateProfilePicture(MultipartFile file) throws NoDataFound, IOException {
//        if (file.isEmpty()) {
//            throw new NoDataFound("No file chosen!");
//        }
//
//        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        if (principal instanceof User user) {
//
//            String uploadDir = System.getProperty("user.dir") + "/profile-pictures";
//            String fileName = user.getEmail() + "_" + file.getOriginalFilename();
//            File targetFile = new File(uploadDir, fileName);
//
//            if (!targetFile.getParentFile().exists()) {
//                targetFile.getParentFile().mkdirs();
//            }
//
//            file.transferTo(targetFile);
//
//            String fileUrl = "/profile-pictures/" + fileName;
//
//            UserProfileDTO userProfile = getProfile();
//            userProfile.setUrlProfile(fileUrl);
//
//            repository.save(mapper.toEntity(user, userProfile));
//            log.info("Profile picture update request by " + user.getEmail());
//            return userProfile;
//        }
//        throw new NoDataFound("No principal found!");
//
//    }

    public void deactivateAccount(Long id) throws NoDataFound {
        User user = repository.findById(id).orElseThrow(() -> new NoDataFound("No user found by given id"));
        user.setEnabled(false);
        repository.save(user);
        log.warning("Account is disabled: " + user.getEmail());
    }

    public void activateAccount(Long id) throws NoDataFound {
        User user = repository.findById(id).orElseThrow(() -> new NoDataFound("No user found by given id"));
        user.setEnabled(true);
        repository.save(user);
        log.warning("Account is enabled: " + user.getEmail());
    }

    public List<UserProfileDTO> findAllProfiles(Pageable pageable) {
        return repository.findAll(pageable).stream().map(mapper::toProfile).toList();
    }

    public void deleteById(Long id) {
        repository.deleteById(id);
        log.warning("Account is deleted by id" + id);
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

    public Optional<User> getPrincipal() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof User user) {
            return Optional.of(user);
        }
        return Optional.empty();
    }

    public ResponseEntity<UserProfileDTO> updateProfile(UserProfileDTO profileInfo) {
        if (getPrincipal().isPresent()) {
            String newEmail = profileInfo.getEmail();
            String newUsername = profileInfo.getUsername();
            UserProfileDTO userProfileDTO = new UserProfileDTO(newEmail, newUsername);
            return ResponseEntity.ok(mapper.toProfile(repository.save(mapper.toEntity(getPrincipal().get(), userProfileDTO))));
        }
        return ResponseEntity.notFound().build();
    }
}
