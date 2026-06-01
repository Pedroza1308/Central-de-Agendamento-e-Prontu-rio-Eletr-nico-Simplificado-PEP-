# Central de Agendamento - Prontuário Eletrônico Simplificado (PEP)

Este projeto consiste em um sistema simplificado de agendamento de consultas hospitalares, desenvolvido como um projeto prático focado no setor de Saúde Digital. A aplicação valida a disponibilidade de horários dos médicos, impedindo conflitos de agenda, e persiste as informações de forma segura em um banco de dados relacional.

## Tecnologias Utilizadas

### Backend
* **Java 17**
* **Spring Boot 3.2.0**
* **Spring Data JPA** (Persistência de dados)
* **Hibernate** (Mapeamento Objeto-Relacional)

### Frontend
* **HTML5**
* **CSS3** (Interface responsiva)
* **JavaScript (Vanilla)** (Requisições assíncronas com Fetch API e manipulação do DOM)

### Banco de Dados
* **PostgreSQL** (Banco de dados relacional)

---

## Arquitetura e Regras de Negócio

O projeto foi estruturado seguindo o padrão arquitetural **MVC (Model-View-Controller)**, garantindo a separação de responsabilidades:
* **Model (`Consulta`, `Medico`, `Paciente`):** Mapeamento direto das tabelas relacionais utilizando anotações JPA, aplicando conceitos de Orientação a Objetos (como relacionamentos `@ManyToOne`).
* **Repository (`ConsultaRepository`):** Abstração de acesso ao banco com queries dinâmicas derived pelo Spring Data.
* **Service (`ConsultaService`):** Camada de inteligência onde reside a validação principal: *impedir o duplo agendamento para o mesmo médico no mesmo dia e horário*.
* **Controller (`ConsultaController`):** Exposição de endpoints REST preparados para receber e responder payloads em formato JSON.

---

## Como Executar o Projeto

### 1. Pré-requisitos
* Java JDK 17 instalado.
* Maven instalado (ou utilizando o wrapper da IDE).
* PostgreSQL rodando localmente.

### 2. Configuração do Banco de Dados
Abra o seu terminal PostgreSQL ou pgAdmin e crie o banco de dados do projeto:
```sql
CREATE DATABASE hospital_db;
