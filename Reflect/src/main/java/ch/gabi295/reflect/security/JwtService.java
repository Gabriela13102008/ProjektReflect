package ch.gabi295.reflect.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.Base64;

@Service
public class JwtService {

    private final String secret;
    private final long expirationMs;

    public JwtService(
            @Value("${app.jwt.secret}") String secret,
            @Value("${app.jwt.expiration-ms:86400000}") long expirationMs
    ) {
        this.secret = secret;
        this.expirationMs = expirationMs;
    }

    public String generateToken(String email) {
        long expiresAt = Instant.now().toEpochMilli() + expirationMs;
        String encodedEmail = Base64.getUrlEncoder().withoutPadding()
                .encodeToString(email.getBytes(StandardCharsets.UTF_8));
        String payload = encodedEmail + "." + expiresAt;
        return payload + "." + sign(payload);
    }

    public String extractUsername(String token) {
        String[] parts = token.split("\\.");
        if (parts.length != 3) {
            return null;
        }

        return new String(Base64.getUrlDecoder().decode(parts[0]), StandardCharsets.UTF_8);
    }

    public boolean isTokenValid(String token, UserDetails userDetails) {
        String[] parts = token.split("\\.");
        if (parts.length != 3) {
            return false;
        }

        String payload = parts[0] + "." + parts[1];
        String expectedSignature = sign(payload);
        long expiresAt = Long.parseLong(parts[1]);

        return expectedSignature.equals(parts[2])
                && expiresAt > Instant.now().toEpochMilli()
                && userDetails.getUsername().equals(extractUsername(token));
    }

    private String sign(String payload) {
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            SecretKeySpec secretKey = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), "HmacSHA256");
            mac.init(secretKey);
            byte[] signatureBytes = mac.doFinal(payload.getBytes(StandardCharsets.UTF_8));
            return Base64.getUrlEncoder().withoutPadding().encodeToString(signatureBytes);
        } catch (Exception e) {
            throw new IllegalStateException("Could not sign token", e);
        }
    }
}
