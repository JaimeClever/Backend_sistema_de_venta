package com.sistema_venta.grupoGestion.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(prePostEnabled = true)
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth

                        // ✅ Registro del primer admin (público)
                        .requestMatchers("/api/usuarios/admin").permitAll()

                        // ✅ Registrar vendedores: solo el admin
                        .requestMatchers("/api/usuarios/vendedor").hasRole("ADMIN")

                        // ✅ CLIENTES
                        .requestMatchers("/api/clientes/**").hasAnyRole("ADMIN", "VENDEDOR")

                        // ✅ PRODUCTOS
                        .requestMatchers(HttpMethod.GET, "/api/productos/**").hasAnyRole("ADMIN", "VENDEDOR") // ver productos
                        .requestMatchers(HttpMethod.POST, "/api/productos/**").hasRole("ADMIN") // crear productos
                        .requestMatchers(HttpMethod.PUT, "/api/productos/**").hasRole("ADMIN")  // actualizar productos
                        .requestMatchers(HttpMethod.DELETE, "/api/productos/**").hasRole("ADMIN") // eliminar productos

                        // ✅ VENTAS: tanto admin como vendedor pueden vender y ver
                        .requestMatchers("/api/ventas/**").hasAnyRole("ADMIN", "VENDEDOR")

                        // Todo lo demás requiere autenticación
                        .anyRequest().authenticated()
                )
                .httpBasic(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
