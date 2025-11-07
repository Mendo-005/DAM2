package es.ciudadescolar.clases;

import java.util.List;

public class InformeSalida {
    
    private String nombreCiudad;
    private String ciudad;

    private List<Medico> listaMedicos;
    private List<Paciente> listaPacientes;

    public String getNombreCiudad() {
        return nombreCiudad;
    }
    public void setNombreCiudad(String nombreCiudad) {
        this.nombreCiudad = nombreCiudad;
    }
    public String getCiudad() {
        return ciudad;
    }
    public void setCiudad(String ciudad) {
        this.ciudad = ciudad;
    }
    public List<Medico> getListaMedicos() {
        return listaMedicos;
    }
    public void setListaMedicos(List<Medico> listaMedicos) {
        this.listaMedicos = listaMedicos;
    }
    public List<Paciente> getListaPacientes() {
        return listaPacientes;
    }
    public void setListaPacientes(List<Paciente> listaPacientes) {
        this.listaPacientes = listaPacientes;
    }


    public InformeSalida() {
    }

    public InformeSalida(String nombreCiudad, String ciudad, List<Medico> listaMedicos, List<Paciente> listaPacientes) {
        this.nombreCiudad = nombreCiudad;
        this.ciudad = ciudad;
        this.listaMedicos = listaMedicos;
        this.listaPacientes = listaPacientes;
    }

    @Override
    public String toString() {
        return "InformeSalida [nombreCiudad=" + nombreCiudad + ", ciudad=" + ciudad + ", listaMedicos=" + listaMedicos
                + ", listaPacientes=" + listaPacientes + "]";
    }

}
