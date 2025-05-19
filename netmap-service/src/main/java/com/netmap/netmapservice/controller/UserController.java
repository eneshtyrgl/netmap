package com.netmap.netmapservice.controller;

import com.netmap.netmapservice.domain.request.UpdateProfileRequest;
import com.netmap.netmapservice.domain.response.ProfileResponse;
import com.netmap.netmapservice.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PreAuthorize("#id.toString() == authentication.principal.userId.toString()")
    @GetMapping("/{id}")
    public ResponseEntity<ProfileResponse> getProfile(@PathVariable UUID id) {
        return ResponseEntity.ok(userService.getProfile(id));
    }

    @PreAuthorize("#id.toString() == authentication.principal.userId.toString()")
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateProfile(@PathVariable UUID id,
                                              @RequestBody UpdateProfileRequest request) {
        userService.updateProfile(id, request);
        return ResponseEntity.noContent().build();
    }
}
