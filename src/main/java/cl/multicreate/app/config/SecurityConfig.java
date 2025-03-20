package cl.multicreate.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SecurityConfig {

    // PasswordEncoder para encriptar y verificar contraseñas
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // AuthenticationManager (si lo necesitas en tus servicios)
    @Bean
    public AuthenticationManager authenticationManager(
            AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    // Configuración de seguridad básica
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        // Desactivar la protección CSRF (para facilitar las pruebas con Postman, etc.)
        http.csrf(csrf -> csrf.disable());

        // Configurar rutas públicas y privadas
        http.authorizeHttpRequests(auth -> {
            auth
                    // Permitimos acceso sin autenticación a los endpoints de /api/auth
                    .requestMatchers("/api/auth/**").permitAll()
                    // Cualquier otra ruta requiere autenticación
                    .anyRequest().authenticated();
        });

        // Podemos usar HTTP Basic o JWT. Aquí, como ejemplo, lo dejamos con Basic deshabilitado,
        // pero si quieres habilitarlo, puedes hacerlo con:
        // http.httpBasic(Customizer.withDefaults());

        // O si deseas forzar login form:
        // http.formLogin(Customizer.withDefaults());

        return http.build();
    }
}
