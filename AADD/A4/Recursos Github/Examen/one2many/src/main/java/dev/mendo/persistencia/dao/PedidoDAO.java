package dev.mendo.persistencia.dao;

import java.util.List;

import dev.mendo.dominio.modelo.Cliente;
import dev.mendo.dominio.modelo.Pedido;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

public class PedidoDAO {
    
    private EntityManager entityManager;
    public PedidoDAO(EntityManager entityManager) 
    {
        this.entityManager = entityManager;
    }

    public void persistPedido(Pedido pedido)
    {
        entityManager.persist(pedido);
    }

    public void mergePedido(Pedido pedido)
    {
        entityManager.merge(pedido);
    }

    public void removePedido(Pedido pedido)
    {
        Pedido manage = entityManager.contains(pedido) ? pedido : entityManager.merge(pedido);
        entityManager.remove(manage);
    }

    public Pedido findPedidoPorId(Long id)
    {
        return entityManager.find(Pedido.class, id);
    }

    public Double getImportePorClienteId(Cliente cliente)
    {
        Double importeTotal = 0.0;
        TypedQuery<Pedido> query = entityManager.createQuery("SELECT p FROM Pedido p WHERE p.cliente = :cliente", Pedido.class);
        query.setParameter("cliente", cliente);
       
        List<Pedido> importes = query.getResultList();    
        for (Pedido pedido : importes) 
        {
            importeTotal += pedido.getImporte();
        }
        return importeTotal;
        
    }

    public List<Cliente> getReporteGeneral()
    {
        TypedQuery<Cliente> query = entityManager.createQuery("SELECT p FROM Pedido p JOIN p.cliente c ORDER BY c.nombre",Cliente.class);
        return query.getResultList();
    } 
}
