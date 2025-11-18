package es.ciudadescolar.hospital;

import java.time.LocalDate;

public class Cita 
{
    private String id;
    private LocalDate fecha;
    private String estado;
    private Paciente paciente;
    private Medico medico;
    private String motivo;
    
    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public LocalDate getFecha() {
        return fecha;
    }
    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }
    public String getEstado() {
        return estado;
    }
    public void setEstado(String estado) {
        this.estado = estado;
    }
    
    public String getMotivo() {
        return motivo;
    }
    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }
    public Paciente getPaciente() {
        return paciente;
    }
    public void setPaciente(Paciente paciente) {
        this.paciente = paciente;
    }
    public Medico getMedico() {
        return medico;
    }
    public void setMedico(Medico medico) {
        this.medico = medico;
    }
    @Override
    public String toString() 
    {
        if (medico!=null && paciente != null)
        return "Cita [id=" + id + ", fecha=" + fecha + ", estado=" + estado + ", paciente=" + paciente.getId() + ", medico="
                + medico.getId() + ", motivo=" + motivo + "]";
        else
        return "Cita sin medico ni paciente [id=" + id + ", fecha=" + fecha + ", estado=" + estado + ", motivo=" + motivo + "]";
    }

    
}
