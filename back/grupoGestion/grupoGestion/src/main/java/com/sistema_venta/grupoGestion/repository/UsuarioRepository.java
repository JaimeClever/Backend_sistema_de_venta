package com.sistema_venta.grupoGestion.repository;

import com.sistema_venta.grupoGestion.entity.Rol;
import com.sistema_venta.grupoGestion.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByUsername(String username);
    boolean existsByUsername(String username);
    boolean existsByRoles_Nombre(Rol.NombreRol nombre);
}
