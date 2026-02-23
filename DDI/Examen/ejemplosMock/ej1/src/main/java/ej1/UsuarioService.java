package ej1;

public class UsuarioService {

    private UsuarioRepository repo;

    public UsuarioService(UsuarioRepository repo) {

        this.repo = repo;

    }

    public String obtenerNombre(int id) {

        return repo.buscarNombrePorId(id);

    }

}