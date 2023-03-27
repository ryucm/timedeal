# timedeal

# 목차

- [개발환경](#개발환경)
- [프로젝트 설명](#프로젝트-설명)
- [설계](#설계)
- [API](#API)
- [기술적 고민](#기술적-고민)
- [TO-DO](#TO-DO)

----



## 개발환경

<div align=left>
<img src="https://img.shields.io/badge/Oracle open jdk 11-007396?style=for-the-badge&logo=java&logoColor=white">
<img src="https://img.shields.io/badge/springboot 2.7.0-6DB33F?style=for-the-badge&logo=springboot&logoColor=white">
<img src="https://img.shields.io/badge/spring data jpa-6DB33F?style=for-the-badge&logo=spring&logoColor=white">
<img src="https://img.shields.io/badge/gradle -02303A?style=for-the-badge&logo=gradle&logoColor=white">
<img src="https://img.shields.io/badge/junit5-25A162?style=for-the-badge&logo=junit5&logoColor=white">
<img src="https://img.shields.io/badge/naver cloud-03C75A?style=for-the-badge&logo=naver&logoColor=white">

## 프로젝트 설명

동시성 테스트를 목적으로 [Numble](https://www.numble.it/b1f4ecbb-67b7-488a-b7dc-ca8824f43a60)에서 진행된 프로그램에 참여한 개인 프로젝트입니다.

### 구현기능
- [회원] 가입/탈퇴/조회
- [상품] 등록/수정/삭제/목록/상세
- [구매] 구매하기

### 요구사항
- 각 상품마다 '구매 가능한 시간'이 있다.
 - '구매 가능 시간' 전에는 구매가 불가능하다.
 - '구매 가능 시간'이 도래하면 구매가 가능하다.
- 각 상품마다 '구매 가능한 수량'이 있다.
 - 정해진 수량을 모두 판매하면 더 이상 구매할 수 없다.
- 유저는 일반 유저와 어드민 유저를 구분해야 한다.
- 상품 등록은 어드민 권한을 가진 유저만 등록 가능하다.
- 상품별로 구매한 유저 리스트를 조회할 수 있어야 한다.
- 유저별로 구매한 상품 리스트를 조회할 수 있어야 한다.
- 상품을 구매한 사람 수와 상품의 재고수량이 일치해야 한다.
- 구매하기 API는 구매 성공/실패만 구분해도 된다.

## 설계

### ERD
![image](https://user-images.githubusercontent.com/89899249/227125067-ac2ab4ff-5b16-42ac-8287-3c2c3853061e.png)


### Architecture
![timedeal](https://user-images.githubusercontent.com/89899249/227121603-c4ef27a4-3746-4e30-9899-8dddacace3d9.png)

## API

### 회원

|    | 기능       | HTTP Method | HTTP Path                   |
|----|----------|-------------|-----------------------------|
| ✅ | [회원] 가입  | POST        | /member                     |
| ✅ | [회원] 조회  | GET         | /member?memberId={memberId} |
| ✅ | [회원] 로그인 | POST        | /member/login               |
| ✅ | [회원] 로그인 | POST        | /member/logout              |
| ✅ | [회원] 수정  | PUT         | /member?memberId={memberId} |
| ✅ | [회원] 탈퇴  | DELETE      | /member                     |


### 상품

|     | 기능      | HTTP Method | HTTP Path             |
|-----|---------|-------------|-----------------------|
| ✅   | [상품] 등록 | POST        | /item                 |
| ✅   | [상품] 조회 | GET         | /item?itemId={itemId} |
| ✅   | [상품] 목록 | GET         | /item/all?            |
| ✅   | [상품] 수정 | PUT         | /item?itemId={itemId}     |
| ✅   | [상품] 삭제 | DELETE      | /item?itemId={itemId}    |

### 구매

|   | 기능                   | HTTP Method | HTTP Path                   |
|---|----------------------|-------------|-----------------------------|
| ✅ | [구매] 구매              | POST        | /orders                     |
| ✅ | [구매] 회원별 구매 상품 리스트   | GET        | /orders/{userId}/user       |
| ✅ | [구매] 상품별 구매 유저 리스트   | GET         | /orders/{productId}/product |

---

|     | 기능            |  
|-----|---------------| 
| 진행중 | 테스트코드         | 
| ✅   | error handler | 
| ✅   | API 문서        |  
| ✅   | CI/CD 구축      |  
|     | 성능 테스트        |  

## 기술적 고민
**Content**
- synchronized
- Java에서의 Lock
- Database의 Lock
- Distribute Lock
 
위에서 열거한 Lock에 관한 공부 내용은 블로그에 기술하였습니다.

[Java, Database, Synchronized 등 다양한 동시성 제어 방법 in Java (1)](https://ckd12394.tistory.com/39)

[Java, Database, Synchronized 등 다양한 동시성 제어 방법 in Java (2)](https://ckd12394.tistory.com/40)


### 위에 있는 각각의 Lock들은 어떤 환경에서 사용하는 것이 좋을까?
블로그에 각 Lock의 장단점과 특성에 대해 정리하였으며, 고민의 결과 아래와 같이 정리하였다.
- Synchronized : 단일 서버에서 사용
- Java의 Lock : 단일 서버에서 lock의 세밀한 동시성 제어가 필요할 때 사용
- Database의 Lock : 여러 개의 서버를 사용할 때
- Distribute Lock : 분산 시스템을 사용할 때

### Service 단계에서 Controller 로 객체를 반환할 때에도 ResponseEntity로 넘겨주는 것이 맞을까?
 처음에는 Controller는 비즈니스 로직이 있는 Service를 호출하는 역할이니까 최대한 간결하게 작성해야겠다고 생각했다.
 또한 service에서 객체를 받아서 다시 ResponseEntity에 넣어서 반환해주는 것보다 애초에 Service에서 ResponseEntity로 반환을 한다면 Controller가 더 간결해지지 않을까 했다.
 다만 한편으로는 Service는 Service로직을 실행한 것만 담당하고 Controller는 전달의 역할이니 ResponseEntity는 Controller의 역할이 맞는 게 아닌가 했다.
 아직 이 질문에 대한 답은 얻지 못 했고 계속 자료를 찾아보고 있다.

## TO-DO
- [ ] 성능측정
- [ ] 동시성 처리 방법 변경 후 성능 테스트
 - [ ] JPA Optimistic Lock 활용
 - [ ] Redis Lock 활용
 - [ ] DB Lock 활용
- [ ] 테스트 코드