package dev.dimitrov;
import java.time.LocalDate;

import dev.dimitrov.dominio.modelo.Pedido;
import dev.dimitrov.servicios.ClienteService;
import dev.dimitrov.util.JPAUtil;

public class Main {
    public static void main(String[] args) {
        
        try {
            ClienteService cs = new ClienteService();
            // ClienteDAO cdao = new ClienteDAO(JPAUtil.getEntityManager());
            Long id = cs.addCliente("Carles Puyol", "catalan@gmail.com");
            
            System.out.println("LA ID ES "+id);
            Pedido p = new Pedido(LocalDate.now(), 777d);
            cs.addPedidoCliente(id, p);
            String r = cs.generateReport();
            System.out.println(r);
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally{
            JPAUtil.close();
        }
        

        
    }
}