package com.hospital.pep.repository;

import com.hospital.pep.model.Consulta;
import org.springframework.data.jpa.repository.JpaRepository;
import java.time.LocalDateTime;
import java.util.List;

public interface ConsultaRepository extends JpaRepository<Consulta, Long> {
    List<Consulta> findByMedicoIdAndDataHora(Long medicoId, LocalDateTime dataHora);
}