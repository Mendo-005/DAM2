package es.ciudadescolar.hospital;

import java.util.List;

public class InformeSalida {

    private List<Medico>listaMedicos;
    private List<Paciente>listaPacientes;
    private List<Cita>listaCitas;

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
    public List<Cita> getListaCitas() {
        return listaCitas;
    }
    public void setListaCitas(List<Cita> listaCitas) {
        this.listaCitas = listaCitas;
    }
    public InformeSalida(List<Medico> listaMedicos, List<Paciente> listaPacientes, List<Cita> listaCitas) {
        this.listaMedicos = listaMedicos;
        this.listaPacientes = listaPacientes;
        this.listaCitas = listaCitas;
    }
    
    public InformeSalida() {
        // Constructor vacío para Jackson
    }
    @Override
    public String toString() {
        return "InformeSalida [listaMedicos=" + listaMedicos + ", listaPacientes=" + listaPacientes + ", listaCitas="
                + listaCitas + "]";
    }

    
    
}
