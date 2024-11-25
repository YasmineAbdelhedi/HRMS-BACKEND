package com.example.hrms.service;

import com.example.hrms.entity.Profile;
import com.example.hrms.repository.ProfileRepository;
import com.example.hrms.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProfileServiceImpl implements ProfileService {

    @Autowired
    private ProfileRepository profileRepository;

    @Override
    public Profile save(Profile profile) {
        return profileRepository.save(profile);
    }

    @Override
    public Optional<Profile> findById(Long id) {
        return profileRepository.findById(id);
    }

    @Override
    public List<Profile> findAll() {
        return profileRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        profileRepository.deleteById(id);
    }
}
