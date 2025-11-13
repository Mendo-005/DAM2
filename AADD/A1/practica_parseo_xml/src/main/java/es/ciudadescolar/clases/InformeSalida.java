package es.ciudadescolar.clases;

import java.util.List;

public class InformeSalida {
    
    private String nombreHospital;
    private String ciudad;

    private List<Medico> listaMedicos;
    private List<Paciente> listaPacientes;

    public String getNombreHospital() {
        return nombreHospital;
    }
    public void setNombreHospital(String nombreHospital) {
        this.nombreHospital = nombreHospital;
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

    public InformeSalida(String nombreHospital, String ciudad, List<Medico> listaMedicos, List<Paciente> listaPacientes) {
        this.nombreHospital = nombreHospital;
        this.ciudad = ciudad;
        this.listaMedicos = listaMedicos;
        this.listaPacientes = listaPacientes;
    }

    @Override
    public String toString() {
        return "InformeSalida [nombreHospital=" + nombreHospital + ", ciudad=" + ciudad + ", listaMedicos=" + listaMedicos
                + ", listaPacientes=" + listaPacientes + "]";
    }

}
