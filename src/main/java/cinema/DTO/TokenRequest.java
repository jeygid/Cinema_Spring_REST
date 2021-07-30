package cinema.DTO;

import java.util.UUID;

public class TokenRequest {
    private UUID token;

    public TokenRequest(UUID token) {
        this.token = token;
    }

    public TokenRequest() {
    }

    public UUID getToken() {
        return token;
    }
}
