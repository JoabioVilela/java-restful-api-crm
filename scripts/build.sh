#!/bin/bash
# Build Script
echo "Building project..."
mvn dependency:resolve
mvn clean package -X

# Verificar se o arquivo JAR foi gerado
if [ -f ./target/app.jar ]; then
    echo "JAR file exists!"
else
    echo "JAR file not found!"
fi

ls -al ./target  # Listar os arquivos na pasta target
