package com.sistema_venta.grupoGestion.service;

import com.sistema_venta.grupoGestion.dto.VentaDTO;
import com.sistema_venta.grupoGestion.dto.VentaResponseDTO;
import com.sistema_venta.grupoGestion.entity.*;
import com.sistema_venta.grupoGestion.mapper.VentaMapper;
import com.sistema_venta.grupoGestion.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


@Service
public class VentaService {

    @Autowired
    private VentaRepository ventaRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public Venta registrarVenta(VentaDTO dto, String usernameAutenticado) {
        Cliente cliente = clienteRepository.findById(dto.getClienteId())
                .orElseThrow(() -> new RuntimeException("Cliente no encontrado"));

        Usuario vendedor = usuarioRepository.findByUsername(usernameAutenticado)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Venta venta = new Venta();
        venta.setFecha(LocalDate.now());
        venta.setCliente(cliente);
        venta.setVendedor(vendedor);

        List<DetalleVenta> detalles = new ArrayList<>();
        double total = 0.0;

        for (VentaDTO.ItemVentaDTO item : dto.getItems()) {
            Producto producto = productoRepository.findById(item.getProductoId())
                    .orElseThrow(() -> new RuntimeException("Producto no encontrado"));

            if (producto.getStock() < item.getCantidad()) {
                throw new RuntimeException("Stock insuficiente para: " + producto.getNombre());
            }

            producto.setStock(producto.getStock() - item.getCantidad()); // Descontar stock
            productoRepository.save(producto);

            DetalleVenta detalle = new DetalleVenta();
            detalle.setProducto(producto);
            detalle.setCantidad(item.getCantidad());
            detalle.setPrecio(producto.getPrecio());
            detalle.setSubtotal(producto.getPrecio() * item.getCantidad());
            detalle.setVenta(venta);

            detalles.add(detalle);
            total += detalle.getSubtotal();
        }

        venta.setTotal(total);
        venta.setDetalles(detalles);

        return ventaRepository.save(venta);
    }

    public List<VentaResponseDTO> listarVentasConDetalles() {
        List<Venta> ventas = ventaRepository.findAll();
        return VentaMapper.toDTOList(ventas);
    }

}