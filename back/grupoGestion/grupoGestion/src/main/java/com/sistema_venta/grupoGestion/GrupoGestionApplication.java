package com.sistema_venta.grupoGestion;


import com.sistema_venta.grupoGestion.entity.Rol;
import com.sistema_venta.grupoGestion.repository.RolRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GrupoGestionApplication {

	public static void main(String[] args) {
		SpringApplication.run(GrupoGestionApplication.class, args);
	}

	@Bean
	CommandLineRunner cargarRolesIniciales(RolRepository rolRepository) {
		return args -> {
			if (rolRepository.findByNombre(Rol.NombreRol.ADMIN).isEmpty()) {
				Rol rolAdmin = new Rol();
				rolAdmin.setNombre(Rol.NombreRol.ADMIN);
				rolRepository.save(rolAdmin);
			}

			if (rolRepository.findByNombre(Rol.NombreRol.VENDEDOR).isEmpty()) {
				Rol rolVendedor = new Rol();
				rolVendedor.setNombre(Rol.NombreRol.VENDEDOR);
				rolRepository.save(rolVendedor);
			}
		};
	}

}
