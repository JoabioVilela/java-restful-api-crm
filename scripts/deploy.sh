#!/bin/bash
echo "Deploying to Kubernetes..."

echo "KUBECONFIG is set to: $KUBECONFIG"
echo "MINIKUBE_HOME is set to: $MINIKUBE_HOME"

echo "Contexto atual no cluster Kubernetes:"
kubectl config current-context
kubectl get nodes

kubectl apply -f ./k8s/deployment.yaml
kubectl set image deployment/my-crm-service my-crm-service=joabio/my-crm-service:latest