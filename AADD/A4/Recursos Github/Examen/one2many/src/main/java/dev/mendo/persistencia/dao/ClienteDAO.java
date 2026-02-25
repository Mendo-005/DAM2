package dev.mendo.persistencia.dao;

import java.util.List;

import dev.mendo.dominio.modelo.Cliente;
import dev.mendo.dominio.modelo.Pedido;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

public class ClienteDAO {

    private EntityManager entityManager;
    public ClienteDAO(EntityManager entityManager) 
    {
        this.entityManager = entityManager;
    }

    public void persistCliente(Cliente cliente)
    {
        entityManager.persist(cliente);
    }

    public void mergeCliente(Cliente cliente)
    {
        entityManager.merge(cliente);
    }

    public void removeCliente(Cliente cliente)
    {
        Cliente manage = entityManager.contains(cliente) ? cliente : entityManager.merge(cliente);
        entityManager.remove(manage);
    }

    public Cliente findClientePorId(Long id)
    {
        return entityManager.find(Cliente.class, id);
    }

    public List<Cliente> getAll()
    {
        TypedQuery<Cliente> query = entityManager.createQuery("SELECT c FROM Cliente c", Cliente.class);
        return query.getResultList(); 
    }

    
}
