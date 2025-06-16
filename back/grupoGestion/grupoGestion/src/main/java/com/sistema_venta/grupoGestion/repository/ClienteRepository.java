package com.sistema_venta.grupoGestion.repository;

import com.sistema_venta.grupoGestion.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface ClienteRepository extends JpaRepository<Cliente, Long> {

}
