package com.sistema_venta.grupoGestion.mapper;

import com.sistema_venta.grupoGestion.dto.VentaResponseDTO;
import com.sistema_venta.grupoGestion.entity.DetalleVenta;
import com.sistema_venta.grupoGestion.entity.Venta;

import java.util.ArrayList;
import java.util.List;

public class VentaMapper {

    public static VentaResponseDTO toDTO(Venta venta) {
        VentaResponseDTO dto = new VentaResponseDTO();
        dto.setId(venta.getId());
        dto.setFecha(venta.getFecha());
        dto.setTotal(venta.getTotal());

        if (venta.getCliente() != null) {
            dto.setClienteId(venta.getCliente().getId());
            dto.setClienteNombre(venta.getCliente().getNombres());
            dto.setClienteCorreo(venta.getCliente().getCorreo());
            dto.setClienteDni(venta.getCliente().getDni());
        }

        if (venta.getVendedor() != null) {
            dto.setVendedorId(venta.getVendedor().getId());
            dto.setVendedorUsername(venta.getVendedor().getUsername());
        }

        List<VentaResponseDTO.DetalleResponseDTO> detalles = new ArrayList<>();
        for (DetalleVenta detalle : venta.getDetalles()) {
            VentaResponseDTO.DetalleResponseDTO detalleDTO = new VentaResponseDTO.DetalleResponseDTO();
            detalleDTO.setProductoNombre(detalle.getProducto().getNombre());
            detalleDTO.setCantidad(detalle.getCantidad());
            detalleDTO.setPrecio(detalle.getPrecio());
            detalleDTO.setSubtotal(detalle.getSubtotal());
            detalles.add(detalleDTO);
        }

        dto.setDetalles(detalles);
        return dto;
    }

    public static List<VentaResponseDTO> toDTOList(List<Venta> ventas) {
        List<VentaResponseDTO> lista = new ArrayList<>();
        for (Venta v : ventas) {
            lista.add(toDTO(v));
        }
        return lista;
    }
}
