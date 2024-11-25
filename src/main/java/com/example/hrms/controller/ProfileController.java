package com.example.hrms.controller;

import com.example.hrms.entity.Profile;
import com.example.hrms.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/profiles")
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    @PostMapping
    public Profile createProfile(@RequestBody Profile profile) {
        return profileService.save(profile);
    }

    @GetMapping("/{id}")
    public Optional<Profile> getProfileById(@PathVariable Long id) {
        return profileService.findById(id);
    }

    @GetMapping
    public List<Profile> getAllProfiles() {
        return profileService.findAll();
    }

    @DeleteMapping("/{id}")
    public void deleteProfile(@PathVariable Long id) {
        profileService.deleteById(id);
    }

    @PutMapping("/{id}")
    public Profile updateProfile(@PathVariable Long id, @RequestBody Profile profile) {
        profile.setId(id);
        return profileService.save(profile);
    }
}
