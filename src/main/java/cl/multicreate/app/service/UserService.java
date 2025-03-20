package cl.multicreate.app.service;

import cl.multicreate.app.dto.AuthResponse;
import cl.multicreate.app.dto.CreateUserRequest;
import cl.multicreate.app.entity.User;
import cl.multicreate.app.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public AuthResponse createUserWithRole(CreateUserRequest request) {
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
        user.setRole(request.getRole());

        userRepository.save(user);
        return new AuthResponse("EXITO", "Usuario creado con rol: " + request.getRole(), null);
    }

    public AuthResponse changeUserRole(Long userId, String newRole) {
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            return new AuthResponse("ERROR", "Usuario no encontrado", null);
        }

        user.setRole(newRole);
        userRepository.save(user);
        return new AuthResponse("EXITO", "Rol actualizado a: " + newRole, null);
    }
}
