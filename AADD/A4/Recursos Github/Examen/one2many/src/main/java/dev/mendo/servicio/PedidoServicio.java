package dev.mendo.servicio;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dev.mendo.dominio.modelo.Cliente;
import dev.mendo.dominio.modelo.Pedido;
import dev.mendo.persistencia.dao.ClienteDAO;
import dev.mendo.persistencia.dao.PedidoDAO;
import dev.mendo.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class PedidoServicio 
{
    private static final Logger LOG = LoggerFactory.getLogger(PedidoServicio.class);
    
    public void annadirPedido(Cliente cliente, Pedido pedido)
    {
        EntityManager entityManager = null;
        EntityTransaction transaction = null;
        ClienteDAO clienteDAO = null;
        PedidoDAO pedidoDAO = null;
        try 
        {
            entityManager = JPAUtil.getEntityManager();
            transaction = JPAUtil.getEntityTransaction(entityManager);
            clienteDAO = new ClienteDAO(entityManager);
            pedidoDAO = new PedidoDAO(entityManager);

            transaction.begin();

            Cliente clienteVacio = clienteDAO.findClientePorId(cliente.getId_cliente());
            if(clienteVacio == null)
            {
                throw new RuntimeException("El cliente no esta en la base de datos");
            }
            else
            {
                pedido.setCliente(clienteVacio);
                clienteVacio.addPedido(pedido); // Lo añado al cliente
                pedidoDAO.persistPedido(pedido); // Persisto el pedido
            }

            transaction.commit();
            LOG.debug("Se ha annadido el pedido");
        } 
        catch (RuntimeException e) 
        {
            LOG.error("Error annadiendo el pedido: " + e.getMessage());
            try 
            {
                transaction.rollback();
                LOG.debug("Se ha realizado el rollback");  
            } catch (Exception e1) 
            {
                LOG.error("Error al realizar el rollback");
            }
        }
        finally
        {
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();    
            }
        }
    }

    public void borrarPedido(Cliente cliente, Pedido pedido)
    {
        EntityManager entityManager = null;
        EntityTransaction transaction = null;
        ClienteDAO clienteDAO = null;
        PedidoDAO pedidoDAO = null;
        try 
        {
            entityManager = JPAUtil.getEntityManager();
            transaction = JPAUtil.getEntityTransaction(entityManager);
            clienteDAO = new ClienteDAO(entityManager);
            pedidoDAO = new PedidoDAO(entityManager);

            transaction.begin();
            Cliente clienteConPedidos = clienteDAO.findClientePorId(cliente.getId_cliente());
            if(clienteConPedidos == null)
            {
                throw new RuntimeException("El cliente no esta en la base de datos");
            }
            if (clienteConPedidos.getPedidos() == null) 
            {    
                throw new RuntimeException("El cliente no tiene pedidos");
            }
            
            Pedido pedidoABorrar = pedidoDAO.findPedidoPorId(pedido.getId_pedido());
            pedidoDAO.removePedido(pedidoABorrar);
            
            transaction.commit();
            LOG.debug("Se ha borrado al pedido");
        } 
        catch (RuntimeException e) 
        {
            LOG.error("Error borrado al pedido: " + e.getMessage());
            try 
            {
                transaction.rollback();
                LOG.debug("Se ha realizado el rollback");  
            } catch (Exception e1) 
            {
                LOG.error("Error al realizar el rollback");
            }
        }
        finally
        {
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();    
            }
        }
    }

    public List<Cliente> getReporteGeneral() {
        List<Cliente> reporte = null;
        EntityManager entityManager = null;
        EntityTransaction transaction = null;
        PedidoDAO pedidoDAO = null;
        try 
        {
            entityManager = JPAUtil.getEntityManager();
            transaction = JPAUtil.getEntityTransaction(entityManager);
            pedidoDAO = new PedidoDAO(entityManager);

            transaction.begin();
            
            reporte = pedidoDAO.getReporteGeneral();
            
            transaction.commit();
            LOG.debug("Se ha borrado al pedido");
        } 
        catch (RuntimeException e) 
        {
            LOG.error("Error borrado al pedido: " + e.getMessage());
            try 
            {
                transaction.rollback();
                LOG.debug("Se ha realizado el rollback");  
            } catch (Exception e1) 
            {
                LOG.error("Error al realizar el rollback");
            }
        }
        finally
        {
            if (entityManager != null && entityManager.isOpen()) {
                entityManager.close();    
            }
        }
        return reporte;
    }
    
}
