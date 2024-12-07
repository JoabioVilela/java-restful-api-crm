#!/bin/bash
set -e  # Interrompe o script em caso de erro

echo "Deploying to Kubernetes..."

echo "KUBECONFIG is set to: $KUBECONFIG"
echo "MINIKUBE_HOME is set to: $MINIKUBE_HOME"

# Configurar o ambiente no Jenkins antes de chamar comandos kubectl:
sudo -u joabio -i minikube start
echo "Contexto atual no cluster Kubernetes:"
sudo -u joabio -i kubectl config current-context
sudo -u joabio -i kubectl get nodes

echo "Aplicando configurações do deployment..."
sudo -u joabio -i kubectl apply -f ./k8s/deployment.yaml

echo "Atualizando imagem do deployment..."
sudo -u joabio -i kubectl set image deployment/my-crm-service my-crm-service=joabio/my-crm-service:latest

echo "Deploy concluído com sucesso!"