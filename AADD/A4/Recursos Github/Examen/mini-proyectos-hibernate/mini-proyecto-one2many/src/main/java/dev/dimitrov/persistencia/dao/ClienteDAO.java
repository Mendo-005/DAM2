package dev.dimitrov.persistencia.dao;

import java.util.HashSet;
import java.util.Set;

import dev.dimitrov.dominio.modelo.Cliente;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

public class ClienteDAO {
    private EntityManager em;

    public ClienteDAO(EntityManager em){
        this.em = em;
    }

    public void addCliente(Cliente c){
        em.persist(c);
    }

    public Cliente getClienteById(Long id){
        Cliente c = em.find(Cliente.class, Short.valueOf(id+""));
        return c;
    }

    public void removeCliente(Cliente c){
        em.remove(c);
    }

    public void mergeCliente(Cliente c){
        em.merge(c);
    }

    public Set<Cliente> getAll(){
        TypedQuery<Cliente> query = em.createQuery("SELECT c FROM Cliente c",Cliente.class);
        return new HashSet<>(query.getResultList());
    }
}
