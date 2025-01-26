package com.be001.cinevibe.service;

import com.be001.cinevibe.model.Cast;
import com.be001.cinevibe.repository.CastRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CastService {
    private final CastRepository castRepository;

    public CastService(CastRepository castRepository) {
        this.castRepository = castRepository;
    }

    public List<Cast> getList() {
        return castRepository.findAll();
    }

    public Cast findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Id can not be null");
        }
        Optional<Cast> cast = castRepository.findById(id);
        return cast.orElse(null);
    }

    public void save(Cast cast) {
        if (cast != null) {
            castRepository.save(cast);
        }
    }

    public void deleteById(Long id) {
        if (id != null) {
            castRepository.deleteById(id);
        }
    }

    public void update(Long id, Cast updatedCast) {
        if (id == null) {
            throw new IllegalArgumentException("Id cannot be null");
        }
        Cast cast = castRepository.findById(id).orElseThrow();
        if (updatedCast.getBiography() != null) {
            cast.setBiography(updatedCast.getBiography());
        }
        if (updatedCast.getName() != null) {
            cast.setName(updatedCast.getName());
        }
        if (updatedCast.getImageUrl() != null) {
            cast.setImageUrl(updatedCast.getImageUrl());
        }
        castRepository.save(cast);
    }
}
