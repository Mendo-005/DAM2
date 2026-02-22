package dev.mendo.servicio;

import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dev.mendo.dominio.modelo.Empresa;
import dev.mendo.dominio.modelo.Tutor;
import dev.mendo.persistencia.dao.EmpresaDAO;
import dev.mendo.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;

public class EmpresaService {

    private static final Logger LOG = LoggerFactory.getLogger(EmpresaService.class);

    public void actualizarEmpresa(Integer id_empresa, String direccionNueva){
        EntityManager entityManager = JPAUtil.getEntityManager();
        EntityTransaction transaction = JPAUtil.getEntityTransaction(entityManager);
        
        try {           

            transaction.begin();
            EmpresaDAO empresaDAO = new EmpresaDAO(entityManager);
            Empresa empresaAActualizar = empresaDAO.buscarEmpresaPorId(id_empresa);
            if (empresaAActualizar==null) 
            {
                LOG.warn("No se encuentra ninguna empresa con id: " + id_empresa);    
            }
            else
            {
                LOG.debug(empresaAActualizar.toString());
                empresaAActualizar.setDireccion(direccionNueva);
                empresaDAO.actualizarEmpresa(empresaAActualizar);
                transaction.commit();
                LOG.debug("Se ha actualizado la empresa: "+empresaAActualizar.toString());
            }
        } catch (RuntimeException e) {
           LOG.error("Error actualizando la empresa: " + e.getMessage());
           try {
            transaction.rollback();
            LOG.debug("Se ha realizado el rollback con exito");
        } catch (Exception e1) {
            LOG.error("Error en la ejecucion del rollback: " + e.getMessage());
        }
        }finally{

            if (entityManager.isOpen() || entityManager != null) 
            {
                entityManager.close();
            }
        }
    }

    public String listaDeEmpresas(){

    EntityManager entityManager = null;
    EntityTransaction transaction = null;
    EmpresaDAO empresaDAO = null;
    StringBuilder consulta = new StringBuilder();

    try {

        entityManager = JPAUtil.getEntityManager();
        transaction = entityManager.getTransaction();
        transaction.begin();

        empresaDAO = new EmpresaDAO(entityManager);

        List<Object[]> resultados = empresaDAO.recogeEmpresasYTutoresNoInstituto();

        for (Object[] fila : resultados) {

            String nombreEmpresa = (String) fila[0];
            String nombreTutor = (String) fila[1];
            String emailEmpresa = (String) fila[2];

            consulta.append(nombreEmpresa)
                    .append("|")
                    .append(nombreTutor)
                    .append("|")
                    .append(emailEmpresa)
                    .append("\n");

        }

        transaction.commit();

    } catch (RuntimeException e) {

        LOG.error("Error en la consulta: " + e.getMessage());
        transaction.rollback();

    } finally {

        if (entityManager != null && entityManager.isOpen()) {
            entityManager.close();
        }
    }

    return consulta.toString();
}

    
}
