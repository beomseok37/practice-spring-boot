## 섹션 3

Querydsl

- Querydsl은 JPQL 빌더이다.
- 코드로 모든 걸 작성할 수 있어 컴파일 시점에 오류를 잡아낼 수 있다.
- 파라미터 바인딩을 자동으로 처리해준다.
- 스프링에서 여러 쓰레드에서 동시에 같은 EntityManger에 접근해도, 트랜잭션마다 별도의 영속성 컨텍스트를 제공한다.
- JPAQueryFactory를 생성할 때 제공하는 EntityManager 덕분에 동시성 문제를 걱정하지 않아도 된다.

결과 조회

- `fetch()` : 리스트 조회, 데이터 없으면 빈 리스트 반환
- `fetchOne()` : 단 건 조회 
  - 결과가 없으면 : `null`
  - 결과가 둘 이상이면 : `com.querydsl.core.NonUniqueResultException`
- `fetchFirst()` : `limit(1).fetchOne()`
- `fetchResults()` : 페이징 정보 포함, total count 쿼리 추가 실행
- `fetchCount()` : count 쿼리로 변경해서 count 수 조회

order

- `desc()` , `asc()` : 일반 정렬
- `nullsLast()` , `nullsFirst()` : null 데이터 순서 부여

페이징

- fetchResult()를 통해 전체 데이터 개수도 조회할 수 있었으나 group by 와 사용시 문제점이 생겨 deprecated 됨..
- 원래 count 쿼리는 성능상 문제가 될 경우가 있었으므로 그냥 따로 작성하자

집합

- JPQL이 제공하는 모든 집합 함수를 제공한다.
- tuple은 프로젝션과 결과반환에서 설명한다.

group by, having 모두 제공한다.

조인

- join(조인 대상, 별칭으로 사용할 Q타입)
- join(), innerJoin()
- leftJoin(), rightJoin()
- on 사용 가능
  - 조인 대상 필터링
  - 연관관계 없는 엔티티 외부 조인
- fetchJoin() 가능

서브쿼리

- select, where 절에서 사용 가능
- JPA JPQL 서브쿼리가 from 절은 서브쿼리를 지원하지 않기 때문에 Querydsl도 지원 X
  - 대부분 join으로 변경 가능..
  - 쿼리 두 번 실행
  - nativeSQL

case, when 사용 가능하다.

상수, 문자 더하기 가능하다.

