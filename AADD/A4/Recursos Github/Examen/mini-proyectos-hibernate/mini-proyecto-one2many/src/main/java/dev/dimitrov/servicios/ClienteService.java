package dev.dimitrov.servicios;

import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import dev.dimitrov.dominio.modelo.Cliente;
import dev.dimitrov.dominio.modelo.Pedido;
import dev.dimitrov.persistencia.dao.ClienteDAO;
import dev.dimitrov.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class ClienteService {
    public static final Logger LOG = LoggerFactory.getLogger(ClienteService.class);

    public Long addCliente(String nombre, String email){
        Long id = -1l;
        Cliente cliente = null;
        EntityManager em = null;
        ClienteDAO cdao = null;
        EntityTransaction et = null;
        try {
            em = JPAUtil.getEntityManager();
            cdao = new ClienteDAO(em);
            et = em.getTransaction();

            et.begin();
            
            cliente = new Cliente(nombre, email);
            cdao.addCliente(cliente);
            id = cliente.getIdCliente();

            et.commit();
        } catch (RuntimeException e) {
            et.rollback();
            LOG.error("Error añadiendo cliente: "+e.getMessage());
        }
        finally{
            JPAUtil.secureCloseEm(em);
        }
        return id;
    }

    public Long addPedidoCliente(Long idCliente, Pedido p){
        Long id = -1l;
        Cliente cliente = null;
        EntityManager em = null;
        ClienteDAO cdao = null;
        EntityTransaction et = null;
        try {
            em = JPAUtil.getEntityManager();
            cdao = new ClienteDAO(em);
            et = em.getTransaction();

            et.begin();
            
            cliente = cdao.getClienteById(idCliente);
            p.setCliente(cliente);
            cliente.addPedido(p);

            et.commit();
        } catch (RuntimeException e) {
            et.rollback();
            LOG.error("Error añadiendo pedido al cliente con id "+idCliente+": "+e.getMessage());
        }
        finally{
            JPAUtil.secureCloseEm(em);
        }
        return id;
    }

    public void removeCliente(Long idCliente){
        Cliente cliente = null;
        EntityManager em = null;
        ClienteDAO cdao = null;
        EntityTransaction et = null;
        try {
            em = JPAUtil.getEntityManager();
            cdao = new ClienteDAO(em);
            et = em.getTransaction();

            et.begin();
            
            cliente = cdao.getClienteById(idCliente);
            if(cliente != null){
                cdao.removeCliente(cliente);
            }
            else{
                throw new RuntimeException("Cliente inexistente");
            }
           
            
            et.commit();
            LOG.info("Eliminado el cliente con id: "+idCliente);
        } catch (RuntimeException e) {
            et.rollback();
            LOG.error("Error añadiendo pedido al cliente con id "+idCliente+": "+e.getMessage());
        }
        finally{
            JPAUtil.secureCloseEm(em);
        }
    }

    public String generateReport(){
        String report = "";
        ClienteDAO cdao = null;
        Set<Cliente> clientes = null;
        EntityManager em = null;
        EntityTransaction et = null;
        try {
            em = JPAUtil.getEntityManager();
            cdao = new ClienteDAO(em);
            et = em.getTransaction();
            et.begin();

            clientes = cdao.getAll();
            
            
            report = "nombre \t\t| email \t\t\t| total_gastado\n";
            for(Cliente c: clientes){
                report += c.getNombre()+" "+c.getEmail()+" "+c.getTotalPedidosImporte()+"\n";
            }
        

            et.commit();
        } catch (RuntimeException e) {
            et.rollback();
            LOG.error("Ocurrió un error mientras se generaba el importe");
        }
        finally{
            JPAUtil.secureCloseEm(em);
        }
        return report;
    }
}
