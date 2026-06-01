package com.hospital.pep.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "consultas")
public class Consulta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "medico_id", nullable = false)
    private Medico medico;

    @ManyToOne
    @JoinColumn(name = "paciente_id", nullable = false)
    private Paciente paciente;

    @Column(name = "data_hora", nullable = false)
    private LocalDateTime dataHora;

    @Column(columnDefinition = "TEXT")
    private String observacoes;

    // Getters e Setters
    public Long getId() { 
        return id; 
    }
    public void setId(Long id) { 
        this.id = id; 
    }
    public Medico getMedico() { 
        return medico; 
    }
    public void setMedico(Medico medico) { 
        this.medico = medico;
    }
    public Paciente getPaciente() { 
        return paciente; 
    }
    public void setPaciente(Paciente paciente) { 
        this.paciente = paciente; 
    }
    public LocalDateTime getDataHora() { 
        return dataHora; 
    }
    public void setDataHora(LocalDateTime dataHora) {
         this.dataHora = dataHora;
    }
    public String getObservacoes() { 
        return observacoes;
    }
    public void setObservacoes(String observacoes) { 
        this.observacoes = observacoes; 
    }
}