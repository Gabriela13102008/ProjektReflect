package ch.gabi295.reflect.dto;

public class AuthResponse {

    private final Long userId;
    private final String username;
    private final String email;
    private final String token;

    public AuthResponse(Long userId, String username, String email, String token) {
        this.userId = userId;
        this.username = username;
        this.email = email;
        this.token = token;
    }

    public Long getUserId() {
        return userId;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getToken() {
        return token;
    }
}
