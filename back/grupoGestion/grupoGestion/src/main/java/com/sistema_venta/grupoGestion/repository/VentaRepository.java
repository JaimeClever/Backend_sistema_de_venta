package com.sistema_venta.grupoGestion.repository;

import com.sistema_venta.grupoGestion.entity.Venta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VentaRepository extends JpaRepository<Venta, Long> {
}