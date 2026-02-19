package dev.mendo.persistencia.dao;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import dev.mendo.dominio.modelo.Empresa;
import dev.mendo.dominio.modelo.Tutor.Sex;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

public class EmpresaDAO {

    private EntityManager entityManager;
    public EmpresaDAO(EntityManager entityManager) {
        this.entityManager = entityManager;
    }
    
    public void actualizarEmpresa(Empresa empresa){
        entityManager.merge(empresa);
    }
    
    public Empresa buscarEmpresaPorId(Integer id){
        return entityManager.find(Empresa.class, id);
    }

  public List<Object[]> recogeEmpresasYTutoresNoInstituto() {

    String jpql = """
        SELECT e.nombre, t.nombre, e.email
        FROM Empresa e
        JOIN e.tutores t
        WHERE t.tipo <> :tipoExcluido
    """;

    TypedQuery<Object[]> query = entityManager.createQuery(jpql, Object[].class);
    query.setParameter("tipoExcluido", Sex.Instituto);

    return query.getResultList();
}

}
