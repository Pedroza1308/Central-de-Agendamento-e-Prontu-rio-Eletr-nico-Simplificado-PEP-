document.getElementById('formAgendamento').addEventListener('submit', function(event) {
    event.preventDefault();

    const medicoId = document.getElementById('medico').value;
    const pacienteId = document.getElementById('paciente').value;
    const dataHora = document.getElementById('dataHora').value;
    const observacoes = document.getElementById('notas').value;

    const payload = {
        medico: { id: parseInt(medicoId) },
        paciente: { id: parseInt(pacienteId) },
        dataHora: dataHora,
        observacoes: observacoes
    };

    const divMensagem = document.getElementById('mensagem');

    fetch('/api/consultas', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(payload)
    })
    .then(async response => {
        const isJson = response.headers.get('content-type')?.includes('application/json');
        const data = isJson ? await response.json() : null;
        
        if (!response.ok) {
            const errorMsg = (data && data.message) ? data.message : 'Erro desconhecido ao agendar.';
            throw new Error(errorMsg);
        }
        
        divMensagem.style.backgroundColor = '#d4edda';
        divMensagem.style.color = '#155724';
        divMensagem.innerHTML = '✅ Consulta agendada com sucesso!';
        divMensagem.style.display = 'block';
        document.getElementById('formAgendamento').reset();
    })
    .catch(error => {
        divMensagem.style.backgroundColor = '#f8d7da';
        divMensagem.style.color = '#721c24';
        divMensagem.innerHTML = '❌ ' + error.message;
        divMensagem.style.display = 'block';
    });
});