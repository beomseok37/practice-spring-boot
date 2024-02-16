## 섹션 3

공통 인터페이스 설정

- 스프링 부트 사용시 `@SpringBootApplication`에서 위치 지정(따로 설정할 필요 없음)
- 만약 위치가 달라진다면 `@EnableJpaRepository`로 스캔할 위치를 지정해주어야 한다.
- 인터페이스 작성 시 Spring Data JPA가 어플리케이션 로딩 시점에 구현체를 만들어준다. (프록시)
- `JpaRepository` 인터페이스 상속 시 `@Repository` 어노테이션을 설정하지 않아도 자동으로 컴포넌트 스캔이 되도록 한다.
  - JPA 예외를 스프링 예외로 변환하는 과정도 자동으로 처리
- 주요 메서드
  - `save(S)`
  - `delete(T)`
  - `findById(ID)`
  - `getOne(ID)` : 엔티티를 프록시로 조회
  - `findAll()`

쿼리 메서드
- 메서드 이름으로 쿼리 생성
- 메서드 이름으로 JPA NamedQuery 호출
- `@Query` 어노테이션을 통해 레포지토리 인터페이스에 직접 JPQL 쿼리 정의

메서드 이름으로 쿼리 생성

- `find...By`, `read...By` 등 By를 붙여주어야 한다.
- `COUNT`: long 타입을 반환한다.
- `EXISTS`: boolean 타입을 반환한다.
- 삭제: `delete...By`, `remove...By`
- `DISTINCT`: findDistinct 로 표기
- `LIMIT`: findFirst3, findFirst, findTop, findTop3
- 쿼리 이름이 길어질수록 지저분해진다.

NamedQuery

- 엔티티에 `@NamedQuery` 어노테이션을 통해 JPQL 쿼리를 생성
- `@Query`에 NamedQuery 이름을 붙여주면 호출할 수 있다.
- 실행할 Named 쿼리가 없다면 메서드 이름으로 쿼리 생성 전략을 사용한다.
- 권장하지는 않는 방법이다..
- `@NamedQuery`를 작성하고 `@Query`를 작성하지 않은 상태라면, 우선 해당 이름의 NamedQuery가 있는지 살펴보고 없다면 그 다음에 메서드 이름으로 쿼리를 생성해준다.

직접 JPQL 쿼리 정의

- `@Query` 어노테이션 내에 JPQL을 직접 정의
- 이름이 없는 NamedQuery와 같다.
- 어플리케이션 로딩 시점에 파싱을 통해 JPQL 내부 문법 오류를 잡아준다.

반환 타입

- 스프링 데이터 JPA는 유연한 반환 타입 지원
- `List<T>`, `T`, `Optional<T>` ...
- 컬렉션
  - 결과 없으면 빈 컬렉션 반환
- 단건 조회
  - 결과 없음: `null` 반환
  - 결과 2건 이상: `javax.persistence.NonUniqueResultException` 예외 발생

페이징

- `org.springframework.domain.Sort` : 정렬 기능
- `org.springframework.domain.Pageable`: 페이징 기능
- 반환 타입
  - `org.springframework.domain.Page` : 추가 count 쿼리 결과를 포함
  - `org.springframework.domain.Slice` : 추가 count 쿼리 없이 다음 페이지만 확인 가능(limit + 1 조회 실시)
  - `List<T>` : 추가 count 쿼리 없이 반환

```java
PageRequest pageRequest = PageRequest.of(0, 3, Sort.by(Sort.Direction.DESC,"username"));
```

```java
public interface Page<T> extends Slice<T> {
    int getTotalPages(); //전체 페이지 수
    long getTotalElements(); //전체 데이터 수
    <U> Page<U> map(Function<? super T, ? extends U> converter); //변환기
}
```

```java
public interface Slice<T> extends Streamable<T> {
    int getNumber(); //현재 페이지
    int getSize(); //페이지 크기
    int getNumberOfElements(); //현재 데이터에 나올 데이터 수
    List<T> getContent(); //조회된 데이터
    boolean hasContent(); // 조회된 데이터 존재 여부
    Sort getSort(); //정렬 정보
    boolean isFirst(); //현재 데이터가 첫 페이지인지 여부
    boolean isLast(); //현재 데이터가 마지막 페이지인지 여부
    boolean hasNext(); // 다음 페이지 여부
    boolean hasPrevious(); // 이전 페이지 여부

    Pageable getPageable(); //페이지 요청 정보
    Pageable nextPageable(); //다음 페이지 객체
    Pageable previousPageable();//이전 페이지 객체
    <U> Slice<U> map(Function<? super T, ? extends U> converter); //변환기
}
```

- `Slice`는 count관련 쿼리를 발생시키지 않는다. 대신 limit + 1 조회를 통해 다음 페이지가 있는지 확인한다.
- 반환 값을 컬렉션, 엔티티, DTO 등으로 변환하는 것이 가능하다.
- 카운트 쿼리는 복잡한 SQL 실행 시 분리해주는 것이 좋다.

> 하이버네이트 6 사용시 left join을 최적화해서 사용한다. left join 후 조인한 테이블의 값을 select나 where절에 사용하지 않는다면 Hibernate는 알아서 join 문을 실행시키지 않는다.
> 
> JPA는 이 경우 최적화해서 join없는 SQL을 만든다.
> 
> 그래도 SQL로 한 번에 조회하고 싶다면 페치 조인을 사용하자.

벌크성 수정, 삭제 쿼리

- `@Modifying` 어노테이션 사용
- 벌크성 쿼리를 실행하면 영속성 컨텍스트에는 반영되지 않는다. (그대로 DB에 전송하기 때문에)
- 그러므로 `@Modifying(clearAutomatically = true)` 옵션을 통해 영속성 컨텍스트를 초기화해줘야 한다.

EntityGraph

- 연관된 엔티들을 SQL로 한 번에 실행하는 방법이다.
- LAZY 전략으로 연관된 엔티티를 한 번에 가져올 수 있다.
- `@EntityGraph(attributePaths = {속성...})`
- LEFT OUTER JOIN 을 통해 연관된 값들을 한 번에 조회한다.

JPA HINT

- SQL 힌트가 아니라 JPA 구현체에게 제공하는 힌트이다.
- `@QueryHints(value = @QueryHint(name = "org.hibernate.readOnly", value = "true"))`

> 쿼리 메서드, @Query를 활용해 JPQL 쿼리를 작성할 수 있다.
> 
> 반환 타입은 컬렉션, 엔티티, DTO를 통해 반환 받을 수 있다.
> 
> Page 반환 타입을 통해 페이징 기능을 쉽게 사용할 수 있다.
> 
> 벌크성 연산 이후 같은 트랜잭션 내에서 조회를 하게 된다면 연속성 컨텍스트에 유의하면서 사용하자.

---

### 섹션 5

사용자 정의 레포지토리 구현

- 커스텀 레포지토리를 만들고 원하는 메서드를 선언해준다.
- 레포지토리가 커스텀 레포지토리 인터페이스를 상속받도록 한다.
- {레포지토리명}+Impl 이라는 클래스명에 커스텀 레포지토리에서 선언한 메서드를 구현할 수 있도록 해준다.
- 위에 정해진 클래스명 말고 다른 방식을 사용하고 싶다면 따로 설정해주어야 한다.

Auditing

- 등록일, 수정일, 등록자, 수정자 등을 추적할 수 있게 해준다.
- `@MappedSuperclass`를 선언한 베이스 클래스를 선언하고 원하는 필드를 추가하고 `@PrePersist`나 `@PreUpdate` 등을 통해 구현해주면 된다.
- `@EnableJpaAuditing`과 `@EntityListener(AuditingEntityListener.class)`를 통해 스프링 데이터 JPA에서 제공하는 어노테이션을 사용할 수도 있다.
  - `@CreatedDate`
  - `@LastModifiedDate`
  - `@CreatedBy`
  - `@LastModifiedBy`
  - 등록자, 수정자 처리는 AuditorAware 빈을 따로 생성해줘야 합니다.

페이징, 정렬

- 스프링 데이터가 제공하는 페이징과 정렬 기능을 스프링 MVC에서도 사용할 수 있다.
- 페이징, 정렬 관련 파라미터들을 그대로 `Pageable` 인자로 받아낼 수 있다.
  - page: 현재 페이지, 0부터 시작
  - size: 한 페이지에 노출할 데이터 건수 (default = 20)
  - sort: 정렬 조건을 정의
- `Page`를 그대로 반환하면 JSON 형식으로 반환된다.
- `Page`의 제너릭 엔티티를 DTO로 변환해서 반환하는 것도 가능하다.

---

### 섹션 6

SimpleJpaRepository

```java
@Repository
@Transactional(readOnly = true)
public class SimpleJpaRepository<T, ID> implements JpaRepositoryImplementation<T, ID> {
    @Transactional
    @Override
    public <S extends T> S save(S entity) {

        Assert.notNull(entity, "Entity must not be null");

        if (entityInformation.isNew(entity)) {
            entityManager.persist(entity);
            return entity;
        } else {
            return entityManager.merge(entity);
        }
    }
}
```

- `@Repository`가 적용되어 있어 스프링 컴포넌트 스캔의 대상이 되어 스프링 빈으로 등록된다. 그리고 JPA 예외들을 스프링이 추상화한 예외로 변환해준다.
- `@Transactional`
  - 모든 JPA의 변경은 트랜잭션 안에서 동작한다.
  - 서비스 계층에서 트랜잭션 시작하지 않으면 레포지토리에서 트랜잭션 시작
  - 서비스 계층에서 트랜잭션 시작하면 전파 받아서 사용
- `@Transactional(readOnly = true)`
  - 데이터를 단순히 조회만하고 변경하지 않을 때 사용
  - 트랜잭션 종료 시 플러시를 생략해서 약간의 성능 향상이 있음
- `save()` 메서드
  - 새로운 엔티티면 persist
  - 새로운 엔티티가 아니면 merge
  - 변경된 엔티티를 save로 저장하면 안되는 이유: merge는 DB에서 해당 엔티티가 존재하는지 확인하는 작업이 무조건 포함되어 있어 SELECT 쿼리가 한 번 나가게 된다.)

새로운 엔티티 판단 전략

- 식별자가 객체일 때 `null`로 판단
- 식별자가 자바 기본 타입일 때 0으로 판단
- `Persistable` 인터페이스를 구현해서 판단 로직 변경 가능
  - 식별자 생성 전략이 직접 할당이면 이미 식별자가 있는 상태로 `save()`가 호출된다.
  - 식별자가 있는 상황이므로 무조건 `merge()`가 호출되게 되므로 매우 비효율적이다.
  - `Persistable을` 구현해서 새로운 엔티티 확인 여부를 직접 구현하는 것이 효과적이다.
  - `@CreateDate`와 조합하여 `isNew()` 메서드를 통해 `createdDate==null`일 경우 새로운 엔티티로 판별하는 방법도 있다.

