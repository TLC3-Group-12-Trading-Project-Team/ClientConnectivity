#!/bin/bash
set -e
scp  -o StrictHostKeyChecking=no -r ./.env ./docker-compose.staging.yml ubuntu@$EC2_PUBLIC_IP_ADDRESS:/home/ubuntu/TradeProject/ClientConnectivity
ssh -o StrictHostKeyChecking=no ubuntu@$EC2_PUBLIC_IP_ADDRESS << 'ENDSSH'
  cd /home/ubuntu/TradeProject/ClientConnectivity
  export $(cat ./.env | xargs)
  echo "$DOCKER_PASSWORD" | docker login -u "$DOCKER_USERNAME" --password-stdin
  docker pull elijahahianyo/trade-project:client-connectivity-prod
  docker-compose -f docker-compose.staging.yml  up -d
ENDSSH
