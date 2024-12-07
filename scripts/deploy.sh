#!/bin/bash
set -e  # Interrompe o script em caso de erro

echo "Deploying to Kubernetes..."

# Configurar variáveis de ambiente para o minikube
export MINIKUBE_HOME="/tmp/.minikube"
export KUBECONFIG="/tmp/.kube/config"

echo "KUBECONFIG is set to: $KUBECONFIG"
echo "MINIKUBE_HOME is set to: $MINIKUBE_HOME"

# Criar diretórios caso não existam
mkdir -p $MINIKUBE_HOME
mkdir -p $(dirname $KUBECONFIG)

# Ajustar permissões do kubeconfig
sudo chown $USER:$USER $KUBECONFIG
sudo chmod 600 $KUBECONFIG

# Configurar o ambiente no Jenkins antes de chamar comandos kubectl:
sudo -u joabio -i minikube start --driver=docker --home=$MINIKUBE_HOME --kubeconfig=$KUBECONFIG

echo "Contexto atual no cluster Kubernetes:"
sudo -u joabio -i kubectl config current-context
sudo -u joabio -i kubectl get nodes

echo "Aplicando configurações do deployment..."
sudo -u joabio -i kubectl apply -f ./k8s/deployment.yaml

echo "Atualizando imagem do deployment..."
sudo -u joabio -i kubectl set image deployment/my-crm-service my-crm-service=joabio/my-crm-service:latest

echo "Deploy concluído com sucesso!"