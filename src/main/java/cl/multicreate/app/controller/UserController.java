package cl.multicreate.app.controller;

import cl.multicreate.app.dto.AuthResponse;
import cl.multicreate.app.dto.CreateUserRequest;
import cl.multicreate.app.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public ResponseEntity<AuthResponse> createUserWithRole(
            @Valid @RequestBody CreateUserRequest request) {
        AuthResponse response = userService.createUserWithRole(request);
        return ResponseEntity.ok(response);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{userId}/role")
    public ResponseEntity<AuthResponse> changeRole(
            @PathVariable Long userId,
            @RequestParam("newRole") String newRole) {
        AuthResponse response = userService.changeUserRole(userId, newRole);
        return ResponseEntity.ok(response);
    }
}
