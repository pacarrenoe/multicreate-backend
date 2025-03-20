package cl.multicreate.app.service;

import cl.multicreate.app.dto.AuthResponse;
import cl.multicreate.app.dto.LoginRequest;
import cl.multicreate.app.dto.RegisterRequest;
import cl.multicreate.app.entity.User;
import cl.multicreate.app.repository.UserRepository;
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

    // ========== REGISTRO ==========
    public AuthResponse register(RegisterRequest request) {
        // Verificar si el username o email existen
        if (userRepository.existsByUsername(request.getUsername())) {
            return new AuthResponse("FAIL", "El username ya está en uso.", null);
        }
        if (userRepository.existsByEmail(request.getEmail())) {
            return new AuthResponse("FAIL", "El email ya está en uso.", null);
        }

        // Crear usuario nuevo
        User user = new User();
        user.setUsername(request.getUsername());
        user.setEmail(request.getEmail());
        // Encriptamos la contraseña
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRole("USER");

        // Guardar en la BD
        userRepository.save(user);

        // Retornar respuesta
        return new AuthResponse("SUCCESS", "Usuario registrado con éxito.", null);
    }

    // ========== LOGIN ==========
    public AuthResponse login(LoginRequest request) {
        // Buscar al usuario por username o email
        User user = userRepository.findByUsernameOrEmail(request.getUsernameOrEmail(), request.getUsernameOrEmail())
                .orElse(null);

        if (user == null) {
            return new AuthResponse("FAIL", "Usuario/Email no encontrado.", null);
        }

        // Verificar la contraseña
        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return new AuthResponse("FAIL", "Contraseña incorrecta.", null);
        }

        // Si todo bien, generamos un token (ejemplo con JWT)
        // Puedes usar una librería como jjwt o jwt.io, etc.
        // Aquí simulamos un token simple. En la práctica, usarías un JWT real.
        String dummyToken = "token_simulado_" + user.getUsername();

        return new AuthResponse("SUCCESS", "Login exitoso.", dummyToken);
    }
}
