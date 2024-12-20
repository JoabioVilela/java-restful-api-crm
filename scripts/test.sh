#!/bin/bash
# Script de execução de testes automatizados com JUnit

echo "Executando testes JUnit..."

# Exibe erros e termina a execução em caso de falha
set -e

# Apenas executa os testes sem gerar o arquivo JAR novamente
mvn test

# Captura o status do comando Maven
if [ $? -eq 0 ]; then
    echo "Todos os testes JUnit passaram com sucesso."
else
    echo "Falha nos testes JUnit. Verifique os logs acima."
    exit 1
fi