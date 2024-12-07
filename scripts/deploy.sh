#!/bin/bash
echo "Deploying to Kubernetes..."

export KUBECONFIG=/home/joabio/.minikube/profiles/minikube/config
kubectl apply -f ./k8s/deployment.yaml
kubectl set image deployment/my-crm-service my-crm-service=joabio/my-crm-service:latest