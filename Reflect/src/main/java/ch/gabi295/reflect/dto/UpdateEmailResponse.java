package ch.gabi295.reflect.dto;

public class UpdateEmailResponse {

    private final Long id;
    private final String username;
    private final String email;
    private final String token;

    public UpdateEmailResponse(Long id, String username, String email, String token) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.token = token;
    }

    public Long getId() { return id; }
    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public String getToken() { return token; }
}