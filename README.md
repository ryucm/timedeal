# **timedeal**

동시성 테스트를 목적으로 [Numble](https://www.numble.it/b1f4ecbb-67b7-488a-b7dc-ca8824f43a60)에서 진행된 프로그램에 참여한 개인 프로젝트입니다.

----
## 1. 목적
- 동시성 문제에 대한 경험
- 성능테스트 진행

## 2. 구현기능
- 회원: 가입/탈퇴/조회
- 상품: 등록/수정/삭제/목록/상세
- 구매: 구매하기

## 3. 개발환경
- Spring boot 2.7.9
- Junit
- nGrinder
- Jenkins
- postgreSQL
- NAVER Cloud Platform
- IntellJ Community

## 4. Architecture
![timedeal](https://user-images.githubusercontent.com/89899249/227121603-c4ef27a4-3746-4e30-9899-8dddacace3d9.png)

## 5. ERD
![image](https://user-images.githubusercontent.com/89899249/227125067-ac2ab4ff-5b16-42ac-8287-3c2c3853061e.png)

## 6. 기술적 고민
**Content**
- synchronized
- Java에서의 Lock
- Database의 Lock
- Distribute Lock
 
위에서 열거한 Lock에 관한 공부 내용은 블로그에 기술하였습니다.

[Java, Database, Synchronized 등 다양한 동시성 제어 방법 in Java (1)](https://ckd12394.tistory.com/39)

[Java, Database, Synchronized 등 다양한 동시성 제어 방법 in Java (2)](https://ckd12394.tistory.com/40)


### 6-1.위에 있는 각각의 Lock들은 어떤 환경에서 사용하는 것이 좋을까?
블로그에 각 Lock의 장단점과 특성에 대해 정리하였으며, 고민의 결과 아래와 같이 정리하였다.
- Synchronized : 단일 서버에서 사용
- Java의 Lock : 단일 서버에서 lock의 세밀한 동시성 제어가 필요할 때 사용
- Database의 Lock : 여러 개의 서버를 사용할 때
- Distribute Lock : 분산 시스템을 사용할 때

### 6-2. Service 단계에서 Controller 로 객체를 반환할 때에도 ResponseEntity로 넘겨주는 것이 맞을까?
 처음에는 Controller는 비즈니스 로직이 있는 Service를 호출하는 역할이니까 최대한 간결하게 작성해야겠다고 생각했다.
 또한 service에서 객체를 받아서 다시 ResponseEntity에 넣어서 반환해주는 것보다 애초에 Service에서 ResponseEntity로 반환을 한다면 Controller가 더 간결해지지 않을까 했다.
 다만 한편으로는 Service는 Service로직을 실행한 것만 담당하고 Controller는 전달의 역할이니 ResponseEntity는 Controller의 역할이 맞는 게 아닌가 했다.
 아직 이 질문에 대한 답은 얻지 못 했고 계속 자료를 찾아보고 있다.

### 7. TO-DO List
- 성능측정
- Service 테스트 코드 진행


