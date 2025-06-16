package com.sistema_venta.grupoGestion.controller;

import com.sistema_venta.grupoGestion.dto.VentaDTO;
import com.sistema_venta.grupoGestion.dto.VentaResponseDTO;
import com.sistema_venta.grupoGestion.entity.Venta;
import com.sistema_venta.grupoGestion.service.VentaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ventas")
public class VentaController {

    @Autowired
    private VentaService ventaService;


    @GetMapping
    public List<VentaResponseDTO> listarVentas() {
        return ventaService.listarVentasConDetalles();
    }

    @PostMapping
    public ResponseEntity<VentaResponseDTO> crearVenta(@RequestBody VentaDTO dto) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Venta venta = ventaService.registrarVenta(dto, username);

        VentaResponseDTO responseDTO = com.sistema_venta.grupoGestion.mapper.VentaMapper.toDTO(venta);
        return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
    }
}
