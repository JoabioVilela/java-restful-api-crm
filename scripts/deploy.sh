#!/bin/bash
set -e  # Interrompe o script em caso de erro

echo "Deploying to Kubernetes..."

# Configurar variáveis de ambiente
export MINIKUBE_HOME="/tmp/.minikube"
export KUBECONFIG="/tmp/.kube/config"

# Criar diretórios necessários
mkdir -p $MINIKUBE_HOME
mkdir -p $(dirname $KUBECONFIG)

# Ajustar permissões do kubeconfig
sudo touch $KUBECONFIG
sudo chown $USER:$USER $KUBECONFIG
sudo chmod 600 $KUBECONFIG

echo "KUBECONFIG is set to: $KUBECONFIG"
echo "MINIKUBE_HOME is set to: $MINIKUBE_HOME"

# Configurar Minikube sem conflitos com permissões padrão
sudo -u joabio -i minikube start --driver=docker --kubeconfig=$KUBECONFIG

echo "Contexto atual no cluster Kubernetes:"
sudo -u joabio -i kubectl config current-context
sudo -u joabio -i kubectl get nodes

echo "Aplicando configurações do deployment..."
sudo -u joabio -i kubectl apply -f ./k8s/deployment.yaml

echo "Atualizando imagem do deployment..."
sudo -u joabio -i kubectl set image deployment/my-crm-service my-crm-service=joabio/my-crm-service:latest

echo "Deploy concluído com sucesso!"
