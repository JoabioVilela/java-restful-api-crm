#!/bin/bash
set -e  # Interrompe o script em caso de erro

echo "Deploying to Kubernetes..."

# Configurar variáveis de ambiente
export MINIKUBE_HOME="/tmp/.minikube"
export KUBECONFIG="/tmp/.kube/config"

# Criar diretórios necessários para o Minikube e kubeconfig
mkdir -p $MINIKUBE_HOME
mkdir -p $(dirname $KUBECONFIG)

# Garantir que o arquivo kubeconfig exista e ajustar permissões adequadamente
sudo touch $KUBECONFIG
sudo chown $USER:$USER $KUBECONFIG
sudo chmod 600 $KUBECONFIG

echo "KUBECONFIG is set to: $KUBECONFIG"
echo "MINIKUBE_HOME is set to: $MINIKUBE_HOME"

# Configurar Minikube sem passar o parâmetro `--kubeconfig`
export MINIKUBE_HOME
minikube start --driver=docker

echo "Minikube iniciado com sucesso!"

echo "Contexto atual no cluster Kubernetes:"
kubectl config current-context
kubectl get nodes

echo "Aplicando configurações do deployment..."
kubectl apply -f ./k8s/deployment.yaml

echo "Atualizando imagem do deployment..."
kubectl set image deployment/my-crm-service my-crm-service=joabio/my-crm-service:latest

echo "Deploy concluído com sucesso!"
