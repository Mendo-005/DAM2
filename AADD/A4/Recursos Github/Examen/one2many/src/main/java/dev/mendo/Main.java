package dev.mendo;

import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dev.mendo.dominio.modelo.Cliente;
import dev.mendo.dominio.modelo.Pedido;
import dev.mendo.servicio.ClienteServicio;
import dev.mendo.servicio.PedidoServicio;

public class Main {

    private static final Logger LOG = LoggerFactory.getLogger(Main.class);

    public static void main(String[] args) 
    {
        LOG.info("======= APP INICIADA ===============");

        // 1)
        Cliente cliente = new Cliente("Jacobo Sanz", "jsanz@empresa.es");
        ClienteServicio clienteServicio = new ClienteServicio();
        clienteServicio.crearCliente(cliente);

        // 2)
        Pedido pedido1 = new Pedido(LocalDate.of(2026, 01, 29), 100.5);
        Pedido pedido2 = new Pedido(LocalDate.of(2026, 01, 30), 40.5);
        Pedido pedido3 = new Pedido(LocalDate.of(2026, 01, 19), 10.0);
        PedidoServicio pedidoServicio = new PedidoServicio();
        pedidoServicio.annadirPedido(cliente, pedido1);
        pedidoServicio.annadirPedido(cliente, pedido2);
        pedidoServicio.annadirPedido(cliente, pedido3);


        // 3)
        //clienteServicio.borrarCliente(cliente);

        // 4)
        pedidoServicio.borrarPedido(cliente, pedido3);

        // 5)
        List<String> reporte = clienteServicio.reporteCliente();
        LOG.info("nombre \t\t| email \t\t\t| total_gastado");
        for (String entrada : reporte) 
        {   
            LOG.info(entrada);    
        }

        LOG.info("======= APP CERRADA ===============");
    }
}