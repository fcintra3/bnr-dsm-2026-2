$base = 'http://localhost:8888'

foreach ($entity in @('matriculas', 'turmas', 'alunos', 'cursos', 'instrutores')) {
    $items = Invoke-RestMethod -Uri "$base/$entity" -Method Get
    foreach ($item in $items) {
        Invoke-RestMethod -Uri "$base/$entity/$($item.id)" -Method Delete
        Write-Host "Excluido: $entity/$($item.id)"
    }
}

Write-Host 'Banco limpo. Execute a colecao novamente.'
