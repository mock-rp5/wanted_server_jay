# wanted_server_jay
- 구인구직 서비스인 원티드의 비즈니스로직을 참고해 구현한 웹서비스 입니다.
- 이 Repository는 웹서비스의 REST API를 구현한 저장소입니다.

## 1.기술 스택
[그림]
- Server 
  - Spring Boot 2.4
  - MySQL
  - JDBC template
- Client
  - React
- Infra
  - AWS EC2
  - AWS RDS
  - NginX
  - Gabia(domain) 

## 2.ERD
https://www.erdcloud.com/d/qMffhXfDwRnC4JEwo
![스크린샷 2022-06-14 오전 1 07 27](https://user-images.githubusercontent.com/51395712/173397367-b377aabb-030b-49d1-89b5-c0d6a0b72616.png)

## 3.API 명세서
https://docs.google.com/spreadsheets/d/1TxzPsIBu0F-TqPO4o3nWENj8gSE6rAPDWN0KDxwPrfo/edit?usp=sharing
- 원티드의 주요 비즈니스 로직 구현
- 유저, 이력서, 채용공고 관련 API 60개 구현
- 네이버 SENS 문자 API 연동
- 카카오 소셜 로그인 API 연동
  
