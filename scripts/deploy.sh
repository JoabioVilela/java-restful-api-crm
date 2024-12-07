#!/bin/bash
set -e  # Interrompe o script em caso de erro

echo "Deploying to Kubernetes..."

echo "KUBECONFIG is set to: $KUBECONFIG"
echo "MINIKUBE_HOME is set to: $MINIKUBE_HOME"

# Configurar o ambiente no Jenkins antes de chamar comandos kubectl:
minikube start
echo "Contexto atual no cluster Kubernetes:"
kubectl config current-context
kubectl get nodes

echo "Aplicando configurações do deployment..."
kubectl apply -f ./k8s/deployment.yaml

echo "Atualizando imagem do deployment..."
kubectl set image deployment/my-crm-service my-crm-service=joabio/my-crm-service:latest

echo "Deploy concluído com sucesso!"