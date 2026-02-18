package dev.dimitrov.persistencia.dao;

import java.util.HashSet;
import java.util.Set;

import dev.dimitrov.dominio.modelo.Pedido;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

public class PedidoDAO {
    private EntityManager em;

    public PedidoDAO(EntityManager em){
        this.em = em;
    }

    public void addPedido(Pedido p){
        em.persist(p);
    }

    public Pedido getPedidoById(Long id){
        Pedido p = em.find(Pedido.class, id);

        return p;
    } 

    public void removePedido(Pedido p){
        em.remove(p);
    }

    public void mergePedido(Pedido p){
        em.merge(p);
    }

    public Set<Pedido> getAll(){
        TypedQuery<Pedido> query = em.createQuery("SELECT p FROM Pedido p",Pedido.class);
        return new HashSet<>(query.getResultList());
    }
}
