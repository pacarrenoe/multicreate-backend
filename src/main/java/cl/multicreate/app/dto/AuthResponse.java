package cl.multicreate.app.dto;

public class AuthResponse {

    private String status;
    private String message;
    private String token;

    public AuthResponse() {
    }

    public AuthResponse(String status, String message, String token) {
        this.status = status;
        this.message = message;
        this.token = token;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
