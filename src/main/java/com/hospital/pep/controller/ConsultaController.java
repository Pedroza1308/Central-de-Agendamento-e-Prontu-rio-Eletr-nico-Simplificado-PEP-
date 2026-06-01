package main.com.hospital.pep.controller;

import com.hospital.pep.model.Consulta;
import com.hospital.pep.service.ConsultaService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/consultas")
@CrossOrigin(origins = "*")
public class ConsultaController {

    private final ConsultaService consultaService;

    public ConsultaController(ConsultaService consultaService) {
        this.consultaService = consultaService;
    }

    @PostMapping
    public ResponseEntity<?> criarAgendamento(@RequestBody Consulta consulta) {
        try {
            Consulta novaConsulta = consultaService.agendarConsulta(consulta);
            return ResponseEntity.ok(novaConsulta);
        } catch (IllegalArgumentException e) {
            // Retorna um JSON com a mensagem de erro para o frontend ler facilmente
            return ResponseEntity.badRequest().body(Map.of("message", e.getMessage()));
        }
    }
}