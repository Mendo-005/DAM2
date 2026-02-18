package dev.dimitrov;

import java.time.LocalDate;

import javax.swing.JPanel;

import dev.dimitrov.dominio.modelo.Perfil;
import dev.dimitrov.dominio.modelo.Usuario;
import dev.dimitrov.servicios.UsuarioServicio;
import dev.dimitrov.util.JPAUtil;

public class Main {
    public static void main(String[] args) {
        UsuarioServicio us = new UsuarioServicio();
        Usuario u1 = new Usuario("lola", "fdz", LocalDate.now(), "lola@gmail.com");
        Perfil p1 = new Perfil("lolito","3dfskopsaf");
        Perfil p2 = new Perfil("nuevoPerfil","dqwej12ji123jio");
        us.addUsuarioConPerfil(u1, p1);
        //us.actualizarPerfilUsuario(u1, p2);
        
        us.generarInforme();
        JPAUtil.close();
    }
}