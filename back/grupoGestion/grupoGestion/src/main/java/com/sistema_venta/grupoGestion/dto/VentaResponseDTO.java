package com.sistema_venta.grupoGestion.dto;

import java.time.LocalDate;
import java.util.List;

public class VentaResponseDTO {
    private Long id;
    private LocalDate fecha;
    private Double total;

    private Long clienteId;
    private String clienteNombre;
    private String clienteCorreo;
    private String clienteDni;

    private Long vendedorId;
    private String vendedorUsername;

    private List<DetalleResponseDTO> detalles;

    // Getters y Setters

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public LocalDate getFecha() { return fecha; }
    public void setFecha(LocalDate fecha) { this.fecha = fecha; }

    public Double getTotal() { return total; }
    public void setTotal(Double total) { this.total = total; }

    public Long getClienteId() { return clienteId; }
    public void setClienteId(Long clienteId) { this.clienteId = clienteId; }

    public String getClienteNombre() { return clienteNombre; }
    public void setClienteNombre(String clienteNombre) { this.clienteNombre = clienteNombre; }

    public String getClienteCorreo() { return clienteCorreo; }
    public void setClienteCorreo(String clienteCorreo) { this.clienteCorreo = clienteCorreo; }

    public String getClienteDni() { return clienteDni; }
    public void setClienteDni(String clienteDni) { this.clienteDni = clienteDni; }

    public Long getVendedorId() { return vendedorId; }
    public void setVendedorId(Long vendedorId) { this.vendedorId = vendedorId; }

    public String getVendedorUsername() { return vendedorUsername; }
    public void setVendedorUsername(String vendedorUsername) { this.vendedorUsername = vendedorUsername; }

    public List<DetalleResponseDTO> getDetalles() { return detalles; }
    public void setDetalles(List<DetalleResponseDTO> detalles) { this.detalles = detalles; }

    // Clase interna para detalles de la venta
    public static class DetalleResponseDTO {
        private String productoNombre;
        private Integer cantidad;
        private Double precio;
        private Double subtotal;

        public String getProductoNombre() { return productoNombre; }
        public void setProductoNombre(String productoNombre) { this.productoNombre = productoNombre; }

        public Integer getCantidad() { return cantidad; }
        public void setCantidad(Integer cantidad) { this.cantidad = cantidad; }

        public Double getPrecio() { return precio; }
        public void setPrecio(Double precio) { this.precio = precio; }

        public Double getSubtotal() { return subtotal; }
        public void setSubtotal(Double subtotal) { this.subtotal = subtotal; }
    }
}
