package com.sistema_venta.grupoGestion.service;


import com.sistema_venta.grupoGestion.dto.UsuarioDTO;
import com.sistema_venta.grupoGestion.entity.Rol;
import com.sistema_venta.grupoGestion.entity.Usuario;
import com.sistema_venta.grupoGestion.repository.RolRepository;
import com.sistema_venta.grupoGestion.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    @Autowired private UsuarioRepository usuarioRepository;
    @Autowired
    private RolRepository rolRepository;
    @Autowired private PasswordEncoder passwordEncoder;

    public Usuario registrarPrimerUsuario(UsuarioDTO dto) {
        if (usuarioRepository.existsByRoles_Nombre(Rol.NombreRol.ADMIN)) {
            throw new RuntimeException("El administrador ya ha sido creado");
        }

        Usuario usuario = new Usuario();
        usuario.setUsername(dto.getUsername());
        usuario.setPassword(passwordEncoder.encode(dto.getPassword()));
        usuario.setNombres(dto.getNombres());

        Rol rolAdmin = rolRepository.findByNombre(Rol.NombreRol.ADMIN)
                .orElseThrow(() -> new RuntimeException("Rol ADMIN no existe"));
        usuario.getRoles().add(rolAdmin);

        return usuarioRepository.save(usuario);
    }

    public Usuario registrarVendedor(UsuarioDTO dto) {
        // Obtener el usuario autenticado
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        String username;

        if (principal instanceof UserDetails) {
            username = ((UserDetails) principal).getUsername();
        } else {
            username = principal.toString();
        }

        Usuario admin = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Usuario autenticado no encontrado"));

        // Verificar si tiene rol ADMIN
        boolean esAdmin = admin.getRoles().stream()
                .anyMatch(rol -> rol.getNombre() == Rol.NombreRol.ADMIN);

        if (!esAdmin) {
            throw new RuntimeException("Solo el administrador puede registrar vendedores");
        }

        // Validar si ya existe el username
        if (usuarioRepository.existsByUsername(dto.getUsername())) {
            throw new RuntimeException("Usuario ya registrado");
        }

        // Crear vendedor
        Usuario vendedor = new Usuario();
        vendedor.setUsername(dto.getUsername());
        vendedor.setPassword(passwordEncoder.encode(dto.getPassword()));
        vendedor.setNombres(dto.getNombres());

        Rol rolVendedor = rolRepository.findByNombre(Rol.NombreRol.VENDEDOR)
                .orElseThrow(() -> new RuntimeException("Rol VENDEDOR no existe"));
        vendedor.getRoles().add(rolVendedor);

        return usuarioRepository.save(vendedor);
    }

}
