# 🌱 Spring Lab

Spring Boot 백엔드 입문 학습 프로젝트입니다.
Java + Spring Boot + MySQL + JWT 인증을 처음부터 직접 구현하며 백엔드 개발의 기초를 익혔습니다.

---

## 📚 학습 목표

- Spring Boot 프로젝트 구조 이해 및 세팅
- JPA를 활용한 MySQL 연동 및 CRUD 구현
- JWT 기반 회원가입 / 로그인 인증 구현
- 공통 응답 포맷 및 전역 예외 처리 설계
- Git Flow 브랜치 전략 실습

---

## 🛠 기술 스택

| 분류 | 기술 |
|------|------|
| Language | Java 19 |
| Framework | Spring Boot 4.0.4 |
| ORM | Spring Data JPA, Hibernate |
| DB | MySQL 9.6 |
| 인증 | Spring Security, JWT (jjwt 0.12.6) |
| 빌드 | Gradle |
| 기타 | Lombok |

---

## 📁 프로젝트 구조

도메인형 패키지 구조를 사용합니다. 기능별로 묶어서 관리하는 방식으로, 프로젝트가 커져도 유지보수가 용이합니다.

```
src/main/java/com/pattern/springlab/
│
├── SpringLabApplication.java          # 진입점
│
├── global/                            # 도메인과 무관한 공통 설정
│   ├── config/
│   │   └── SecurityConfig.java        # Spring Security 설정
│   ├── jwt/
│   │   ├── JwtProvider.java           # 토큰 생성 / 검증
│   │   └── JwtFilter.java             # 요청마다 토큰 확인
│   ├── response/
│   │   └── ApiResponse.java           # 공통 응답 포맷
│   └── exception/
│       ├── ErrorCode.java             # 에러 코드 Enum
│       ├── BusinessException.java     # 커스텀 예외
│       └── GlobalExceptionHandler.java # 전역 예외 처리
│
└── domain/                            # 도메인별 패키지
    ├── memo/                          # 메모 도메인
    │   ├── controller/
    │   │   └── MemoController.java
    │   ├── service/
    │   │   └── MemoService.java
    │   ├── repository/
    │   │   └── MemoRepository.java
    │   └── entity/
    │       └── Memo.java
    │
    └── user/                          # 유저 도메인
        ├── controller/
        │   └── UserController.java
        ├── service/
        │   └── UserService.java
        ├── repository/
        │   └── UserRepository.java
        ├── entity/
        │   └── User.java
        └── dto/
            ├── SignUpRequest.java
            ├── LoginRequest.java
            └── LoginResponse.java
```

---

## ⚙️ 환경 설정

### 환경 분리

| 파일 | 용도 |
|------|------|
| `application.yml` | 공통 설정 (active profile 지정) |
| `application-local.yml` | 로컬 개발 환경 (DB, JWT 설정) |
| `application-dev.yml` | 개발 서버 환경 |
| `application-prod.yml` | 운영 서버 환경 |

> ⚠️ `application-local.yml`은 `.gitignore`에 포함되어 있습니다. 민감정보 보호를 위해 절대 커밋하지 않습니다.

### application-local.yml 예시

```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/spring_lab?serverTimezone=Asia/Seoul&characterEncoding=UTF-8
    username: root
    password: {비밀번호}
    driver-class-name: com.mysql.cj.jdbc.Driver
  jpa:
    show-sql: true

jwt:
  secret: {시크릿키}
  access-expiration: 1800000    # 30분
  refresh-expiration: 604800000 # 7일

logging:
  level:
    com.pattern.springlab: DEBUG
```

---

## 🚀 로컬 실행 방법

### 1. MySQL 실행

```bash
brew services start mysql
mysql -u root -p
```

```sql
CREATE DATABASE spring_lab;
```

### 2. 프로젝트 실행

```bash
git clone https://github.com/heyoonyoon/spring-lab01.git
cd spring-lab01
```

`src/main/resources/application-local.yml` 파일을 생성하고 DB 정보를 입력한 뒤 IntelliJ에서 실행합니다.

### 3. 서버 확인

```
http://localhost:8080/hello
```

---

## 📌 API 명세

### 유저 API

| Method | URL | 설명 | 인증 필요 |
|--------|-----|------|-----------|
| POST | `/users/signup` | 회원가입 | ❌ |
| POST | `/users/login` | 로그인 (토큰 발급) | ❌ |

#### 회원가입 요청

```json
POST /users/signup

{
  "email": "test@gmail.com",
  "password": "password123",
  "nickname": "테스터"
}
```

#### 로그인 요청 / 응답

```json
POST /users/login

// 요청
{
  "email": "test@gmail.com",
  "password": "password123"
}

// 응답
{
  "success": true,
  "message": "success",
  "data": {
    "accessToken": "eyJhbGci..."
  }
}
```

---

### 메모 API

> 모든 요청에 Authorization 헤더 필요
> `Authorization: Bearer {accessToken}`

| Method | URL | 설명 |
|--------|-----|------|
| POST | `/memos` | 메모 생성 |
| GET | `/memos` | 메모 전체 조회 |
| PUT | `/memos/{id}` | 메모 수정 |
| DELETE | `/memos/{id}` | 메모 삭제 |

#### 공통 응답 포맷

```json
{
  "success": true,
  "message": "success",
  "data": { ... }
}
```

#### 에러 응답 포맷

```json
{
  "success": false,
  "message": "이미 사용중인 이메일입니다.",
  "data": null
}
```

---

## 🌿 브랜치 전략

Git Flow를 기반으로 한 브랜치 전략을 사용합니다.

```
main        # 운영 배포 브랜치
develop     # 개발 통합 브랜치
feature/*   # 기능 개발 브랜치
hotfix/*    # 긴급 버그 수정 브랜치
```

### 개발 흐름

```
1. GitHub에서 이슈 등록
2. develop에서 feature 브랜치 생성
   git checkout -b feature/기능명
3. 개발 & 커밋
   git commit -m "feat: 기능명 구현 (#이슈번호)"
4. develop으로 PR → Squash and merge
5. 브랜치 삭제
6. 기능 쌓이면 develop → main PR → Squash and merge
7. GitHub Releases로 버전 태그 (v1.0.0)
```

### 커밋 컨벤션

| 타입 | 설명 |
|------|------|
| `feat` | 새 기능 추가 |
| `fix` | 버그 수정 |
| `refactor` | 코드 리팩토링 |
| `chore` | 빌드, 설정 변경 |
| `docs` | 문서 수정 |

---

## 💡 핵심 학습 내용

### IoC / DI

```java
// Spring이 객체를 직접 생성하고 주입해줌 (생성자 주입)
@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository; // Spring이 넣어줌
}
```

### JPA Entity

```java
@Entity
@Table(name = "users")  // MySQL 예약어 충돌 방지
@Getter
@NoArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;  // BCrypt 암호화 저장
}
```

### 공통 응답 포맷

```java
@Getter
public class ApiResponse<T> {
    private final boolean success;
    private final String message;
    private final T data;

    public static <T> ApiResponse<T> ok(T data) {
        return new ApiResponse<>(true, "success", data);
    }

    public static <T> ApiResponse<T> fail(String message) {
        return new ApiResponse<>(false, message, null);
    }
}
```

### 전역 예외 처리

```java
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ApiResponse<Void>> handleBusinessException(BusinessException e) {
        ErrorCode errorCode = e.getErrorCode();
        return ResponseEntity
                .status(errorCode.getStatus())
                .body(ApiResponse.fail(errorCode.getMessage()));
    }
}
```

### JWT 인증 흐름

```
로그인 요청
   ↓
UserService에서 이메일 / 비밀번호 확인
   ↓
JwtProvider.createAccessToken() → 토큰 발급
   ↓
클라이언트가 토큰 저장
   ↓
이후 요청마다 Authorization: Bearer {token} 헤더 첨부
   ↓
JwtFilter에서 토큰 검증 → SecurityContext에 인증 정보 저장
   ↓
API 정상 응답
```

---

## 📝 배우면서 느낀 점

- **패키지 구조가 중요하다.** 레이어드 구조보다 도메인형 구조가 기능이 늘어날수록 훨씬 관리하기 쉽다.
- **@Transactional은 수정할 때 필수다.** 없으면 변경 감지(Dirty Checking)가 안 돼서 DB에 반영이 안 된다.
- **민감정보는 절대 커밋하면 안 된다.** `.gitignore` 설정은 프로젝트 시작할 때 무조건 먼저 해야 한다.
- **feature 브랜치 전략을 쓰면 히스토리가 깔끔해진다.** 기능마다 이슈 등록하고 브랜치 따는 습관을 들이자.
- **HTTP 상태코드를 알면 디버깅이 쉬워진다.** 401 뜨면 토큰 문제, 500 뜨면 서버 에러.

---


## 📅 개발 일지

| 날짜 | 버전 | 내용 |
|------|------|------|
| 2026.03.22 | v1.0.0 | 프로젝트 초기 세팅, 메모 CRUD, JWT 인증, 공통 응답/예외 처리 |