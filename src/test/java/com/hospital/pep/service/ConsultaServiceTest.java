package com.hospital.pep.service;

import com.hospital.pep.model.Consulta;
import com.hospital.pep.model.Medico;
import com.hospital.pep.model.Paciente;
import com.hospital.pep.repository.ConsultaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ConsultaServiceTest {

    @Mock
    private ConsultaRepository consultaRepository;

    @InjectMocks
    private ConsultaService consultaService;

    private Medico medico;
    private Paciente paciente;
    private LocalDateTime horarioConsulta;

    @BeforeEach
    void setUp() {
        // Inicializa os mocks do Mockito antes de cada teste
        MockitoAnnotations.openMocks(this);

        // Instancia objetos base para usar nos testes
        medico = new Medico();
        medico.setId(1L);
        medico.setNome("Dra. Catarina");

        paciente = new Paciente();
        paciente.setId(1L);
        paciente.setNome("Pedro");

        horarioConsulta = LocalDateTime.of(2026, 6, 15, 14, 0);
    }

    @Test
    @DisplayName("Deve agendar uma consulta com sucesso quando o horário estiver livre")
    void deveAgendarConsultaComSucesso() {
        // Cenário (Given)
        Consulta novaConsulta = new Consulta();
        novaConsulta.setMedico(medico);
        novaConsulta.setPaciente(paciente);
        novaConsulta.setDataHora(horarioConsulta);
        novaConsulta.setObservacoes("Rotina");

        // Simulando que o banco retorna uma lista VAZIA (horário livre)
        when(consultaRepository.findByMedicoIdAndDataHora(1L, horarioConsulta))
                .thenReturn(Collections.emptyList());

        // Simulando a persistência salvando e retornando a consulta com um ID gerado
        when(consultaRepository.save(any(Consulta.class))).thenAnswer(invocation -> {
            Consulta c = invocation.getArgument(0);
            c.setId(100L); // Simula o ID gerado pelo banco
            return c;
        });

        // Execução (When)
        Consulta consultaSalva = consultaService.agendarConsulta(novaConsulta);

        // Verificação (Then)
        assertNotNull(consultaSalva);
        assertEquals(100L, consultaSalva.getId());
        assertEquals("Dra. Catarina", consultaSalva.getMedico().getNome());
        
        // Garante que o método save() foi realmente chamado uma vez
        verify(consultaRepository, times(1)).save(novaConsulta);
    }

    @Test
    @DisplayName("Deve lançar exceção quando o médico já possuir consulta no mesmo horário")
    void deveLancarExcecaoQuandoHorarioOcupado() {
        // Cenário (Given)
        Consulta novaConsulta = new Consulta();
        novaConsulta.setMedico(medico);
        novaConsulta.setPaciente(paciente);
        novaConsulta.setDataHora(horarioConsulta);

        // Simulando que o banco JÁ POSSUI uma consulta idêntica naquele horário
        when(consultaRepository.findByMedicoIdAndDataHora(1L, horarioConsulta))
                .thenReturn(List.of(new Consulta()));

        // Execução & Verificação (When & Then)
        // O assertThrows garante que a exceção esperada foi disparada pelo service
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            consultaService.agendarConsulta(novaConsulta);
        });

        // Valida se a mensagem de erro da regra de negócio está correta
        assertEquals("Erro: O médico já possui uma consulta agendada para este horário.", exception.getMessage());
        
        // Garante que o save() NUNCA foi executado no banco devido ao erro
        verify(consultaRepository, never()).save(any(Consulta.class));
    }
}