package com.hospital.pep.service;

import com.hospital.pep.model.Consulta;
import com.hospital.pep.repository.ConsultaRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ConsultaService {

    private final ConsultaRepository consultaRepository;

    public ConsultaService(ConsultaRepository consultaRepository) {
        this.consultaRepository = consultaRepository;
    }

    public Consulta agendarConsulta(Consulta consulta) {
        List<Consulta> consultasExistentes = consultaRepository.findByMedicoIdAndDataHora(
            consulta.getMedico().getId(), 
            consulta.getDataHora()
        );

        if (!consultasExistentes.isEmpty()) {
            throw new IllegalArgumentException("Erro: O médico já possui uma consulta agendada para este horário.");
        }

        return consultaRepository.save(consulta);
    }
}