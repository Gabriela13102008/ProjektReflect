package ch.gabi295.reflect.controller;

import ch.gabi295.reflect.dto.UpdateEmailRequest;
import ch.gabi295.reflect.dto.UpdatePasswordRequest;
import ch.gabi295.reflect.dto.UpdateUsernameRequest;
import ch.gabi295.reflect.dto.UserResponse;
import ch.gabi295.reflect.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public UserResponse getMe() {
        return userService.getCurrentUserProfile();
    }

    @PutMapping("/username")
    public ResponseEntity<?> updateUsername(@RequestBody UpdateUsernameRequest request) {
        try {
            return ResponseEntity.ok(userService.updateUsername(request.getUsername()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/email")
    public ResponseEntity<?> updateEmail(@RequestBody UpdateEmailRequest request) {
        try {
            return ResponseEntity.ok(userService.updateEmail(request.getEmail()));
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/password")
    public ResponseEntity<?> updatePassword(@RequestBody UpdatePasswordRequest request) {
        try {
            userService.updatePassword(request.getOldPassword(), request.getNewPassword());
            return ResponseEntity.ok("Passwort erfolgreich geändert");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping
    public ResponseEntity<?> deleteAccount(@RequestParam String password) {
        try {
            userService.deleteAccount(password);
            return ResponseEntity.ok("Account gelöscht");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}