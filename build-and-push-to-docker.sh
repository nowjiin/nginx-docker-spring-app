#!/bin/bash

# Docker Hub 사용자명 설정 (본인의 Docker Hub 사용자명으로 변경하세요)
DOCKER_USERNAME="your-docker-hub-name"

# 현재 디렉토리 확인
echo "Current directory: $(pwd)"
echo "Contents: $(ls -la)"

# App1 빌드 및 푸시
echo "Building and pushing App1..."
if [ -d "Docker-App-1" ]; then
    cd Docker-App-1
    ./gradlew clean bootJar
    docker build -t ${DOCKER_USERNAME}/spring-app1:latest .
    docker push ${DOCKER_USERNAME}/spring-app1:latest
    cd ..
else
    echo "Error: Docker-App-1 directory not found!"
    exit 1
fi

# App2 빌드 및 푸시
echo "Building and pushing App2..."
if [ -d "Docker-App-2" ]; then
    cd Docker-App-2
    ./gradlew clean bootJar
    docker build -t ${DOCKER_USERNAME}/spring-app2:latest .
    docker push ${DOCKER_USERNAME}/spring-app2:latest
    cd ..
else
    echo "Error: Docker-App-2 directory not found!"
    exit 1
fi

echo "Build and push completed!"
echo "You can now run: docker-compose up -d"
