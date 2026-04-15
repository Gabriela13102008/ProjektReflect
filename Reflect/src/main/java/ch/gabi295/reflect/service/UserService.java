package ch.gabi295.reflect.service;

import ch.gabi295.reflect.dto.UpdateEmailResponse;
import ch.gabi295.reflect.dto.UserResponse;
import ch.gabi295.reflect.model.User;
import ch.gabi295.reflect.repository.UserRepository;
import ch.gabi295.reflect.security.JwtService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final CurrentUserService currentUserService;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public UserService(CurrentUserService currentUserService,
                       UserRepository userRepository,
                       PasswordEncoder passwordEncoder,
                       JwtService jwtService) {
        this.currentUserService = currentUserService;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public UserResponse getCurrentUserProfile() {
        User user = currentUserService.getCurrentUser();
        return new UserResponse(user.getId(), user.getUsername(), user.getEmail());
    }

    public UserResponse updateUsername(String newUsername) {
        User user = currentUserService.getCurrentUser();

        if (newUsername == null || newUsername.trim().length() < 3) {
            throw new IllegalArgumentException("Username muss mindestens 3 Zeichen lang sein.");
        }

        if (userRepository.existsByUsername(newUsername)) {
            throw new IllegalArgumentException("Username ist bereits vergeben.");
        }

        user.setUsername(newUsername.trim());
        userRepository.save(user);

        return new UserResponse(user.getId(), user.getUsername(), user.getEmail());
    }

    // EMAIL UPDATE -> gibt neuen Token zurück!
    public UpdateEmailResponse updateEmail(String newEmail) {
        User user = currentUserService.getCurrentUser();

        if (newEmail == null || !newEmail.contains("@")) {
            throw new IllegalArgumentException("Ungültige E-Mail Adresse.");
        }

        if (userRepository.existsByEmail(newEmail)) {
            throw new IllegalArgumentException("E-Mail ist bereits vergeben.");
        }

        user.setEmail(newEmail.trim());
        userRepository.save(user);

        // neuen Token generieren (mit neuer Email)
        String newToken = jwtService.generateToken(user.getEmail());

        return new UpdateEmailResponse(user.getId(), user.getUsername(), user.getEmail(), newToken);
    }

    public void updatePassword(String oldPassword, String newPassword) {
        User user = currentUserService.getCurrentUser();

        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new IllegalArgumentException("Altes Passwort ist falsch.");
        }

        if (newPassword == null || newPassword.length() < 6) {
            throw new IllegalArgumentException("Neues Passwort muss mindestens 6 Zeichen haben.");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }

    public void deleteAccount(String password) {
        User user = currentUserService.getCurrentUser();

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("Passwort ist falsch.");
        }

        userRepository.delete(user);
    }
}