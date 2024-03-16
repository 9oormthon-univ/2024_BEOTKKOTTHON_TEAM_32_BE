#Base image
FROM openjdk:11-jdk
LABEL authors="이동헌"

# 앱 디렉토리 생성
WORKDIR /app

# 컨테이너의 파일시스템에 빌드된 jar파일 복사
COPY build/libs/balpyo-0.0.1-SNAPSHOT.jar /app/balpyo-0.0.1-SNAPSHOT.jar

# keystore 파일 복사
COPY src/main/resources/keystore.p12 /app/keystore.p12

# 앱 실행을 위한 사용자 계정 생성
RUN addgroup --system dockeruser && adduser --system --ingroup dockeruser dockeruser

# /app 폴더 소유권을 신규 사용자 계정으로 변경
RUN chown -R dockeruser:dockeruser /app

# 사용자 계정으로 전환
USER dockeruser

# 노출할 포트 명시
EXPOSE 443

# always do command
ENTRYPOINT ["java","-jar","/app/balpyo-0.0.1-SNAPSHOT.jar"]
