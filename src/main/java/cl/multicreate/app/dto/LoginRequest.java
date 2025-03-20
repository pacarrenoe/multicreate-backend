package cl.multicreate.app.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class LoginRequest {

    @NotBlank
    private String usernameOrEmail;

    @NotBlank
    @Size(min = 6)
    private String password;

    public @NotBlank @Size(min = 6) String getPassword() {
        return password;
    }

    public void setPassword(@NotBlank @Size(min = 6) String password) {
        this.password = password;
    }

    public @NotBlank String getUsernameOrEmail() {
        return usernameOrEmail;
    }

    public void setUsernameOrEmail(@NotBlank String usernameOrEmail) {
        this.usernameOrEmail = usernameOrEmail;
    }
}
