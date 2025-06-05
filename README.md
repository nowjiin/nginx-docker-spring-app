# Spring Boot Docker

Spring Boot 애플리케이션을 Docker로 컨테이너화하고 Nginx 리버스 프록시를 구성하기 위해서 만든 프로젝트~

## 프로젝트 구조

```
├── app1/                          # Spring Boot App1 (포트 8080)
│   ├── src/main/java/
│   ├── src/main/resources/
│   │   ├── templates/index.html
│   │   └── application.yml
│   ├── build.gradle
│   └── Dockerfile
├── app2/                          # Spring Boot App2 (포트 8081)
│   ├── src/main/java/
│   ├── src/main/resources/
│   │   ├── templates/index.html
│   │   └── application.yml
│   ├── build.gradle
│   └── Dockerfile
├── docker-compose.yml
├── build-and-push.sh
└── README.md
```

## 실습 단계

### 1. Docker Hub에서 이미지 받기

```bash
# Docker 이미지 풀
docker pull nowjin/spring-app1:latest
docker pull nowjin/spring-app2:latest
```

### 2. Docker Compose로 실행

```bash
# 컨테이너 실행
docker compose up -d

# 로그 확인
docker compose logs -f

# 상태 확인
docker compose ps
```

### 3. 애플리케이션 접속

- **App1**: http://localhost:8080
- **App2**: http://localhost:8081

### 4. 기능 테스트

#### App1 (포트 8080)
- 메인 페이지: 파란색 테마
- Hello API: 이름 입력하여 인사말 받기
- 상태 확인 API: 서버 상태 확인

#### App2 (포트 8081)
- 메인 페이지: 주황색 테마
- 인사 API: 랜덤 인사말 생성
- 정보 확인 API: 서버 정보 조회
- 제곱 계산 API: 숫자 제곱값 계산

## API 엔드포인트

### App1 (localhost:8080)
- `GET /` - 메인 페이지
- `POST /api/hello` - Hello API
- `GET /api/status` - 상태 확인
- `GET /health` - 헬스체크

### App2 (localhost:8081)
- `GET /` - 메인 페이지
- `POST /api/greeting` - 인사 API
- `GET /api/info` - 정보 조회
- `POST /api/calculate` - 제곱 계산
- `GET /health` - 헬스체크

## 개발자용 명령어

### 로컬에서 빌드만 하기 도커 푸쉬 X

```bash
# App1 빌드
cd app1
./gradlew clean bootJar
docker build -t spring-app1:local .

# App2 빌드
cd app2
./gradlew clean bootJar
docker build -t spring-app2:local .
```

### Docker Hub에 푸시하기

```bash
# 스크립트 실행 권한 부여
chmod +x build-and-push.sh

# 빌드 및 푸시 (본인의 Docker Hub 사용자명으로 수정 필요)
./build-and-push.sh
```

## 컨테이너 관리

```bash
# 컨테이너 중지
docker compose down

# 이미지까지 삭제
docker compose down --rmi all

# 볼륨까지 삭제
docker compose down -v

# 컨테이너 재시작
docker compose restart

# 특정 서비스만 재시작
docker compose restart app1
```

## 로그 확인

```bash
# 전체 로그
docker compose logs

# 특정 앱 로그
docker compose logs app1
docker compose logs app2

# 실시간 로그 확인
docker compose logs -f app1
```

## 헬스체크

```bash
# App1 헬스체크
curl http://localhost:8080/health

# App2 헬스체크
curl http://localhost:8081/health
```

## Nginx 연동 준비

이 두 Spring Boot 앱은 Nginx 리버스 프록시와 연동하여 사용할 수 있도록 설계되어있습니다.

### Nginx 설정
```nginx
upstream app1 {
    server localhost:8080;
}

upstream app2 {
    server localhost:8081;
}

server {
    listen 80;
    
    location /app1 {
        proxy_pass http://localhost:8080;
    }
    
    location /app2 {
        proxy_pass http://localhost:8081;
    }
}
```

## 주의사항

1. **Docker Hub 사용자명**: `docker-compose.yml`과 `build-and-push.sh`에서 `your-dockerhub-username`을 본인의 실제 Docker Hub 사용자명으로 변경하세요.

2. **포트 충돌**: 8080, 8081 포트가 이미 사용 중인 경우 `docker-compose.yml`에서 포트를 변경하세요.

3. **방화벽**: Windows/Mac에서 Docker Desktop이 실행 중인지 확인하세요.

## 트러블슈팅

### 컨테이너가 시작되지 않는 경우
```bash
# 자세한 로그 확인
docker compose logs app1
docker compose logs app2

# 컨테이너 상태 확인
docker compose ps
```

### 포트 충돌 오류
```bash
# 포트 사용 확인 (Windows)
netstat -ano | findstr :8080

# 포트 사용 확인 (Linux/Mac)
lsof -i :8080
```

### 이미지 업데이트
```bash
# 최신 이미지 받기
docker compose pull

# 기존 컨테이너 삭제 후 재생성
docker compose up -d --force-recreate
```
