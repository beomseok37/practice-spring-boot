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

- `fetchResult()`를 통해 전체 데이터 개수도 조회할 수 있었으나 group by 와 사용시 문제점이 생겨 deprecated 됨..
- 원래 count 쿼리는 성능상 문제가 될 경우가 있었으므로 그냥 따로 작성하자

집합

- JPQL이 제공하는 모든 집합 함수를 제공한다.
- tuple은 프로젝션과 결과반환에서 설명한다.

group by, having 모두 제공한다.

조인

- `join(조인 대상, 별칭으로 사용할 Q타입)`
- `join()`, `innerJoin()`
- `leftJoin()`, `rightJoin()`
- `on` 사용 가능
  - 조인 대상 필터링
  - 연관관계 없는 엔티티 외부 조인
- `fetchJoin()` 가능

서브쿼리

- `select`, `where` 절에서 사용 가능
- JPA JPQL 서브쿼리가 `from` 절은 서브쿼리를 지원하지 않기 때문에 Querydsl도 지원 X
  - 대부분 `join`으로 변경 가능..
  - 쿼리 두 번 실행
  - nativeSQL

case, when 사용 가능하다.

상수, 문자 더하기 가능하다.

프로젝션

- 대상이 하나일 경우 해당 필드의 타입으로 받을 수 있다.
- 프로젝션 대상이 여러 개일 경우 튜플을 통해 조회할 수 있다.
- 조회하려는 필드가 여러 개일 경우 DTO로 선언해서 받을 수 있다.
  - `Projections.bean()` : 프로퍼티 setter를 통해 DTO생성
  - `Projections.fields()` : 필드 직접 접근
    - 필드명이 엔티티와 다를 경우 `{QEntity}.{source}.as({alias})`, `ExpressionUtils.as(source, alias)`
  - `Projections.constructor()` : 생성자 사용
  - `@QueryProjection` : 컴파일러가 타입 체크까지 할 수 있어 가장 안전하지만, DTO가 QueryDSL 어노테이션을 유지해야한다는 점과 DTO까지 Q파일을 생성해야 하는 단점이 있다.

동적 쿼리

- `BooleanBuilder`를 통해 `null`이 아닐 경우만 연결시키는 방법이 있다.
- `where`절에서 `null`은 무시된다. `BooleanExpression`을 활용하면 반환값이 `null`일 경우 자동 제외된다.

SQL Function

- JPA와 같이 Dialect에 등록된 내용만 호출할 수 있다. (등록하는 방법도 있긴 하다..)
- ansi 표준 함수들은 querydsl에 상당부분 내장하고 있다.

> JPAQueryFactory에 주입하는 엔티티 메니저의 동시성 문제는 걱정안해도 된다.
> 
> 스프링이 주입해주는 엔티티 매니저는 실제 동작 시점에 진짜 엔티티 매니저를 찾아주는 프록시용 가짜 엔티티 매니저이다.
> 
> 가짜 엔티티 매니저는 실제 사용 시점에 트랜잭션 단위로 실제 엔티티 매니저를 할당해준다.

profile

- 실행되는 관점에 따라 실행되지 않게 할 때 사용된다.

Spring Data JPA + QueryDSL

- 사용자 정의 리포지토리 생성
- JpaRepository와 함께 커스텀 레포지토리를 상속받아 생성
- 사용자 정의 레포지토리의 구현체 내부에서 QueryDSL을 사용해 구현

QueryDsl + Page

- Pageable 인자를 통해 offset과 limit을 얻어온다.
- PageImpl 객체로 반환해 Page 관련 정보를 모두 전달할 수 있다.
- fetchCount와 fetchResult 모두 deprecate 됐으므로 그냥 count관련 쿼리는 필요할 때만 사용되도록 하자

Sort

- 조건이 조금만 복잡해져도 Pageable의 Sort 기능을 사용하기 어렵다
- 루트 엔티티 범위를 넘어서는 정렬 기능이 필요하다면 스프링 데이터 페이징이 제공하는 Sort를 사용하기 보다는 파라미터를 받아 직접 처리하는 것이 낫다.

