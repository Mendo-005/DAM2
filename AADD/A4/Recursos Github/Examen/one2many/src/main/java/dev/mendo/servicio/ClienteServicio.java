package dev.mendo.servicio;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dev.mendo.dominio.modelo.Cliente;
import dev.mendo.persistencia.dao.ClienteDAO;
import dev.mendo.persistencia.dao.PedidoDAO;
import dev.mendo.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class ClienteServicio 
{
    private static final Logger LOG = LoggerFactory.getLogger(ClienteServicio.class);
    
    public void crearCliente(Cliente cliente)
    {
        EntityManager entityManager = null;
        EntityTransaction transaction = null;
        ClienteDAO clienteDAO = null;
        try 
        {
            entityManager = JPAUtil.getEntityManager();
            transaction = JPAUtil.getEntityTransaction(entityManager);
            clienteDAO = new ClienteDAO(entityManager);

            transaction.begin();

            clienteDAO.persistCliente(cliente);

            transaction.commit();
            LOG.trace("Se ha creado el cliente");
        } 
        catch (RuntimeException e) 
        {
            LOG.error("Error creando al cliente: " + e.getMessage());
            try 
            {
                transaction.rollback();
                LOG.trace("Se ha realizado el rollback");  
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

    public void borrarCliente(Cliente cliente)
    {
        EntityManager entityManager = null;
        EntityTransaction transaction = null;
        ClienteDAO clienteDAO = null;
        try 
        {
            entityManager = JPAUtil.getEntityManager();
            transaction = JPAUtil.getEntityTransaction(entityManager);
            clienteDAO = new ClienteDAO(entityManager);

            transaction.begin();
            Cliente clienteABorrar = clienteDAO.findClientePorId(cliente.getId_cliente());
            if(clienteABorrar == null)
            {
                throw new RuntimeException("El cliente no esta en la base de datos");
            }
            else
            {
                clienteDAO.removeCliente(clienteABorrar);
            }

            transaction.commit();
            LOG.trace("Se ha borrado al cliente");
        } 
        catch (RuntimeException e) 
        {
            LOG.error("Error borrado al cliente: " + e.getMessage());
            try 
            {
                transaction.rollback();
                LOG.trace("Se ha realizado el rollback");  
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

     public List<String> reporteCliente()
    {
        EntityManager entityManager = null;
        EntityTransaction transaction = null;
        ClienteDAO clienteDAO = null;
        List<Cliente> clientes = null;
        List<String> reporte = null;
        PedidoDAO pedidoDAO = null;
        try 
        {
            entityManager = JPAUtil.getEntityManager();
            transaction = JPAUtil.getEntityTransaction(entityManager);
            clienteDAO = new ClienteDAO(entityManager);
            pedidoDAO = new PedidoDAO(entityManager);
            clientes = clienteDAO.getAll();
            reporte = new ArrayList<>();

            transaction.begin();
            
            for (Cliente cliente : clientes) 
            {
                String entrada = cliente.getNombre() + " | " + cliente.getEmail() + " | " +  pedidoDAO.getImportePorClienteId(cliente);
                reporte.add(entrada);
            }
            
           

            transaction.commit();
            LOG.trace("Se ha realizado el reporte");
        } 
        catch (RuntimeException e) 
        {
            LOG.error("Error al realizar el reporte: " + e.getMessage());
            try 
            {
                transaction.rollback();
                LOG.trace("Se ha realizado el rollback");  
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
