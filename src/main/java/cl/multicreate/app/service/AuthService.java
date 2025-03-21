package cl.multicreate.app.service;

import cl.multicreate.app.dto.AuthResponse;
import cl.multicreate.app.dto.LoginRequest;
import cl.multicreate.app.dto.RegisterRequest;
import cl.multicreate.app.entity.User;
import cl.multicreate.app.repository.UserRepository;
import cl.multicreate.app.security.JwtUtils;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class AuthService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtUtils jwtUtils;

    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByUsername(request.getUsername())) {
            return new AuthResponse("ERROR", "El username ya está en uso", null);
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            return new AuthResponse("ERROR", "El email ya está en uso", null);
        }

        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole("ROLE_USER");
        userRepository.save(user);

        return new AuthResponse("EXITO", "Usuario registrado correctamente", null);
    }

    public AuthResponse login(LoginRequest request) {
        User user = userRepository
                .findByUsernameOrEmail(request.getUsernameOrEmail(), request.getUsernameOrEmail())
                .orElse(null);

        if (user == null) {
            return new AuthResponse("ERROR", "Usuario o email no encontrado", null);
        }
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return new AuthResponse("ERROR", "Contraseña incorrecta", null);
        }

        String token = jwtUtils.generateToken(user.getUsername(), user.getRole());
        return new AuthResponse("ERROR", "Login exitoso", token);
    }
}
