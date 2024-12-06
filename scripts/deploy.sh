
#!/bin/bash
# Deploy Script
echo "Deploying to Kubernetes..."

# kubectl apply -f kubernetes/
# Aplica o arquivo de deployment.yaml do diretório k8s
kubectl apply -f ./k8s/deployment.yaml
kubectl set image deployment/my-crm-service my-crm-service=joabio/my-crm-service:latest