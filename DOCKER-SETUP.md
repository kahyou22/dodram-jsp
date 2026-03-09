# Docker 배포 설정 가이드

## 사전 준비 (서버에서 1회만 실행)

### 1. 기존 MySQL 데이터 백업
```bash
# 기존 MySQL이 실행 중인 상태에서 백업
mysqldump -u root -p dodram_db > ~/dodram_db_backup.sql
```

### 2. Docker 설치
```bash
# Docker 설치
curl -fsSL https://get.docker.com | sudo sh

# 현재 사용자를 docker 그룹에 추가 (sudo 없이 docker 명령 사용)
sudo usermod -aG docker $USER

# 로그아웃 후 다시 로그인 (그룹 적용)
exit
```

### 3. 프로젝트 디렉토리 생성 및 .env 파일 작성
```bash
mkdir -p ~/dodram-jsp
cd ~/dodram-jsp

# .env 파일 생성 (실제 값 입력)
cat > .env << 'EOF'
DB_ROOT_PASSWORD=여기에_루트비밀번호
DB_NAME=dodram_db
DB_USER=여기에_DB유저명
DB_PASSWORD=여기에_DB비밀번호
EOF
```

### 4. 기존 서비스 중지
```bash
# 포트 충돌 방지를 위해 기존 서비스 중지
sudo systemctl stop tomcat
sudo systemctl stop mysql
sudo systemctl disable tomcat
sudo systemctl disable mysql
```

### 5. Git Push (배포는 자동)
```bash
# 로컬(PC)에서 실행
git add .
git commit -m "ci: Docker 배포 구성 추가"
git push origin main
```
> GitHub Actions가 자동으로:
> 1. rsync로 소스를 서버 `~/dodram-jsp/`에 전송
> 2. 서버에서 `docker compose up -d --build` 실행

### 6. 배포 확인 (서버에서 실행)
```bash
cd ~/dodram-jsp

# 컨테이너 상태 확인
docker compose ps

# 로그 확인
docker compose logs -f
```

### 7. 기존 DB 데이터 복원
```bash
# MySQL 컨테이너에 백업 복원
docker exec -i dodram-mysql mysql -u root -p'루트비밀번호' dodram_db < ~/dodram_db_backup.sql
```

### 7. 동작 확인
```bash
# 컨테이너 상태 확인
docker compose ps

# 접속 테스트
curl -I http://localhost
```

---

## 접속 정보

| 서비스 | 접속 주소 |
|--------|----------|
| 웹사이트 | http://서버IP/ |
| phpMyAdmin | http://서버IP:8081/ |

---

## 유용한 명령어

```bash
# 로그 보기
docker compose logs -f

# 특정 서비스 로그
docker compose logs -f tomcat

# 컨테이너 재시작
docker compose restart

# 전체 중지
docker compose down

# 전체 중지 + DB 데이터 삭제 (주의!)
docker compose down -v
```
