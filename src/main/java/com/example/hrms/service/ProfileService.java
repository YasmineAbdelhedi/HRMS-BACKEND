package com.example.hrms.service;

import com.example.hrms.entity.Profile;
import java.util.List;
import java.util.Optional;

public interface ProfileService {
    Profile save(Profile profile);
    Optional<Profile> findById(Long id);
    List<Profile> findAll();
    void deleteById(Long id);
}
