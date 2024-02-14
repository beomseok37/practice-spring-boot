## 섹션 1

지금 시대는 객체를 관계형 DB에서 관리하고 있다. (물론 NoSQL도 있음)

SQL 중심적인 개발의 문제점

- 지나치게 반복되는 CRUD 코드 작성
- 객체를 SQL로
- SQL을 다시 객체로
- 새로운 필드가 추가될 경우 모든 SQL 수정

-> SQL에 의존적인 개발을 피하기 어렵다...

객체를 영구히 저장하기 위한 현실적인 대안은 관계형 데이터베이스이다.

객체를 관계형 데이터베이스에 저장할 경우 우리는 객체와 데이터베이스를 연결해주는 매퍼 역할을 한다.

객체 vs 관계형 데이터베이스

- 객체 상속 관계 : Table 슈퍼타입 서브타입 관계

  - Insert: 하나의 객체를 분해해 각각의 테이블에 저장해주어야 한다.
  - Select: 각각의 테이블에서 조회, 객체 생성 ...
  - 조회, 삽입 시에 추가적인 작업이 필요...

  -> 자바 컬렉션에 저장하듯 사용할 수 없을까?

- 연관관계

  - 객체는 참조, 테이블은 외래 키를 사용
  - 객체 다운 모델링을 사용할 경우 SQL문에 사용 시 불편함..
  - 객체는 연관된 객체를 모두 탐색할 수 있지만, SQL문을 통해 모든 연관된 객체를 항상 미리 로딩할 수는 없다.

  -> 진정한 의미의 계층 분할이 어렵다.

- 비교

  - SQL문을 통해 얻은 값을 통해 객체를 생성했을 경우 항상 새로운 객체를 생성해줌으로 같은 ID를 통해 생성된 객체일지라도 다른 값이 된다.
  - 자바 컬렉션에 저장 후 조회할 경우 같은 객체로 인식한다.

  -> SQL로 동일 값을 얻어올 경우 같은 값으로 인식해주기 위한 추가작업이 필요

객체, SQL 사용시 객체답게 모델링하기 위해 매핑 작업만 늘어난다.

객체를 자바 컬렉션에 저장하듯이 사용할 수 있는 방법을 고민하다 만들어진 것이 JPA이다.

JPA?

> - Java Persistent API(JPA)
> - 자바 진영의 ORM 기술 표준

ORM

- Object-relational mapping(객체 관계 매핑)
- 객체는 객체대로, RDB는 RDB대로 설계 가능
- ORM이 객체와 RDB 중간에서 매핑

JPA

- JPA는 어플리케이션과 JDBC 사잉에서 동작한다.
- 객체를 DB에 삽입 시 필요한 객체 분석, 쿼리 작성, JDBC api 연결 등을 모두 알아서 해준다.
- JPA는 인터페이스의 모음으로 하이버네이트 구현체를 사용한다.

JPA 사용 이유

- SQL 중심적인 개발에서 객체 중심 개발으로
- 생산성: 저장, 조회, 수정, 삭제 등을 모두 한 줄의 코드로 작성할 수 있다.
- 유지보수: 필드 추가 시 모든 SQL문을 변경해줄 필요가 없다.
- 패러다임 불일치 해결
  - 상속, 연관관계, 객체 탐색, 객체 비교 등을 알아서 처리해준다.
- 성능₩
  - 1차 캐시와 동일성 보장
  - 트랜잭션을 지원하는 쓰기 지연
  - 지연 로딩
- 데이터 접근 추상화와 벤더 독립성
- 표준

---

## 섹션 2

JPA 실습

Dialect

- JPA는 특정 데이터베이스에 종속되지 않는다.
- 데이터베이스마다 문법과 함수에 약간씩 차이가 있다.
- JPA는 방언을 사용함으로써 하나의 코드로도 여러 데이터베이스에 적용할 수 있다.
- 하이버네이트는 40가지 이상의 데이터베이스 방언을 지원한다.

JPA 구동 방식

- Persistence 클래스를 통해 설정정보를 기반으로 EntityManagerFactory를 생성
- EntityManagerFactory가 필요할 때마다 EntityManager를 만들어 구동한다.
- EntityManagerFactory는 하나만 생성해서 어플리케이션 전체에서 공유한다.
- EntityManager는 쓰레드간 공유하면 안된다. (사용하면 버려야 한다.)
- JPA의 모든 데이터 변경은 트랜잭션 안에서 실행해야 한다.

JPQL

- EntityManager를 통해서 단순한 조회나 삽입, 수정, 삭제를 수행할 수 있지만, 조금 복잡한 쿼리를 실행할 수는 없다.
- SQL을 추상화한 객체 지향 쿼리 언어를 제공해준다.
- SQL 문법과 유사하면서, 엔티티 객체를 대상으로 쿼리할 수 있게 해준다.

-> 객체 지향 SQL

---

## 섹션 3

영속성 컨텍스트

- 엔티티를 영구 저장하는 환경이라는 뜻
- 논리적인 개념이다.(눈에 보이지 않는다.)
- EntityManager를 통해 영속성 컨텍스트에 접근할 수 있다.

엔티티 생명주기

- 비영속: 영속성 컨텍스트와 관계가 없는 새로운 상태
  - 객체를 생성했지만 EntityManager를 통해 persist하지 않은 경우
- 영속: 영속성 컨텍스트에 의해 관리되고 있는 상태
  - 객체 생성후 persist를 통해 영속화한 경우
- 준영속: 영속성 컨텍스트에 저장되었다가 분리된 상태
  - 영속화된 엔티티를 영속성 컨텍스트에서 분리한 경우
- 삭제: 삭제된 상태
  - 객체를 삭제한 경우

영속성 컨텍스트의 이점

- 1차 캐시
  - 엔티티 생성 후 영속성 컨텍스트에 넣을 경우 PK를 키로, Entity를 밸류로 1차 캐시에 저장하게 된다.
  - 1차 캐시에 저장된 엔티티는 이후 다시 조회하게 될 경우 DB에 접근하지 않고 1차 캐시에 있는 엔티티를 읽어 주게 된다.
- 동일성 보장
  - 1차 캐시로 반복 가능한 읽기 등급의 트랜잭션 격리 수준을 데이터베이스가 아닌 어플리케이션 차원에서 제공한다.
- 트랜잭션을 지원하는 쓰기 지연
  - EntityManager를 통해 persist하는 경우 바로 DB에 insert를 하는 것이 아니라 쓰기 지연 SQL 저장소에 insert SQL을 저장해두고 transaction이 commit될 경우 insert SQL을 통해 삽입하고 commit 된다.
- 변경 감지 (Dirty Checking)
  - 영속성 컨텍스트에 의해 관리되고 있는 객체는 1차 캐시에 저장될 때 첫 모습을 스냅샨으로 저장해둔다.
  - flush가 발생할 경우 스냅샷과 현재 엔티티를 비교해 변경된 부분이 있다면 update SQL을 생성해 쓰기 지연 SQL 저장소에 저장한다.
  - update 관련 SQL을 DB에 전달하고 commit 된다.
- 지연 로딩 (Lazy Loading)

플러시

> 영속성 컨텍스트의 변경내용을 데이터베이스에 반영

- 변경된 부분은 없는지 확인한다.
- 수정된 엔티티와 관련된 update를 위해 쓰기 지연 SQL 저장소에 등록한다.
- 쓰기 지연 SQL 저장소의 쿼리를 데이터베이스에 전송 (등록, 수정, 삭제 쿼리)
- `em.flush()`를 통해 직접 호출할 수 있다.
- 트랜잭션 커밋, JPQL 쿼리 실행 시 플러시가 자동 호출된다.
  - 여러 객체를 영속화한 다음 같은 트랜잭션 내부에서 JPQL문을 통해 해당 객체들을 조회하는 쿼리를 실행할 경우 아직 DB에 저장된 내용이 없으므로 조회문으로 객체들을 읽을 수 없다. 그러므로 JPQL 실행 시 일단 flush를 실행한 다음 JPQL 쿼리를 실행한다.
- 플러시를 했다고 영속성 컨텍스트를 비우지 않는다. 영속성 컨텍스트의 내용과 DB의 내용을 동기화하는 작업이다.
- 트랜잭션이라는 작업 단위가 있기 때문에 가능한 것이다. 삽입을 하든, 수정을 하든 트랜잭션 단위 내부에서 커밋 직전에만 동기화하면 되니까.

준영속 상태

- 영속 상태의 엔티티를 영속성 컨텍스트에서 분리한 상태
- 영속성 컨텍스트가 제공하는 기능을 사용할 수 없게 된다.

정리

영속성 컨텍스트

- 엔티티를 저장하기 위한 환경이다.
- 1차 캐시, 지연 SQL 저장소가 존재한다.
- 1차 캐시에서는 엔티티의 PK 값을 키로 엔티티 객체를 밸류로 저장해두고, 1차 캐시에 조회하려는 엔티티가 존재하는 경우 DB에 접근하지 않고 영속성 컨텍스트의 엔티티를 조회할 수 있다.
- 1차 캐시에 저장된 엔티티의 초기 상태를 스냅샷으로 저장해두고 있어 flush 시점에 현재 엔티티의 상태와 스냅샷을 비교해 변경이 있을 경우 이를 DB에 자동 반영해준다.
- 엔티티에 대한 삽입, 수정 삭제 등이 일어난 경우 지연 SQL 저장소에 해당 SQL을 일단 저장해두고 트랜잭션이 커밋되기 직전 시점에 이를 DB에 전달해주게 된다.

엔티티 생명주기

- 비영속 상태: 기본적으로 그냥 생성된 객체
- 영속 상태: 영속성 컨텍스트에 의해 관리되고 있는 객체
- 준영속 상태: 영속성 컨텍스트의 관리에서 분리된 상태
- 삭제 상태: 삭제된 상태

---

## 섹션 4

엔티티 매핑

- 객체와 테이블 매핑: `@Entity`, `@Table`
- 필드와 컬럼 매핑: `@Column`
- 기본 키 매핑: `@Id`
- 연관관계 매핑: `@ManyToOne`, `@OneToMany`

`@Entity`

- `@Entity`가 선언된 클래스는 JPA가 관리해준다.
- JPA를 사용해 테이블과 매핑할 클래스는 `@Entity` 필수
- 기본 생성자를 무조건 선언해야한다. (파라미터가 없는 `public`, `protected` 생성자)
- final 클래스, enum, interface, inner 클래스에는 사용할 수 없다.
- DB에 저장할 필드에 final 선언 할 수 없다.
- name 속성: JPA에서 사용할 엔티티 이름을 지정 (기본값: 클래스 이름 그대로 사용)

@Table

- 엔티티와 매핑할 DB의 테이블 지정
- 속성:
  - name: 매핑할 테이블 이름 (기본값: 엔티티 이름을 사용)
  - catalog: 데이터베이스 catalog 매핑
  - schema: 데이터베이스 schema 매핑
  - unqiueConstraints(DDL): DDL 생성 시 유니크 제약 조건 생성

데이터베이스 스키마 자동 생성
- `@Entity`로 매핑된 객체들에 대한 테이블 관련 DDL을 어플리케이션 실행 시점에 자동으로 생성해준다.
- `@Entity`가 선언된 객체들을 통해 테이블을 만들어주기 때문에 테이블 중심에서 객체 중심으로 이동해준다.
- 데이터베이스 방언을 사용하기 때문에 똑같은 코드로도 다양한 DB에 맞는 DDL이 생선된다.
- 어플리케이션 초기 시점마다 생성되는 DDL은 개발 시점에만 사용해야 한다!
- 생성된 DDL은 운영 서버에서는 사용하지 않거나 적절히 다듬어 사용한다.
- hibernate.hbm2ddl.auto 속성
  - `create`: 기존 테이블 제거 후 다시 생성 (Drop + Create)
  - `create-drop`: 테이블 생성 후 어플리케이션 종료 시점에 drop
  - `update`: 변경 부분만 반영
  - `validate`: 엔티티와 테이블이 정상 매핑되었는지 확인
  - `none`: 사용하지 않음

> 운영 장비에는 create, create-drop, update 절대 사용하면 안된다.
> 
> 개발 초기 단계에는 create, update
> 
> 테스트 서버는 update, validate
> 
> 스테이징과 운영 서버는 validate, none

DDL 생성 시 사용할 수 있는 조건

- 제약 조건 추가: `@Column`의 length, `nullable`과 같은 속성을 부여하면 ddl 생성 시 관련 제약 조건이 추가된다.

```java
// 길이 제한, not null 추가
@Column(length = 10, nullable = false)
private String name;
```

```bash
Hibernate: 
    create table Member (
        id bigint not null,
        name varchar(10) not null,
        primary key (id)
    )
```

- 유니크 제약 조건 추가

```java
// @Column을 통한 unique 제약 조건
@Column(unique = true)
private int age;

// @Table을 통해서만 unique 제약 조건의 이름을 명시해줄 수 있다. 
@Table(uniqueConstraints = {
        @UniqueConstraint(name = "NAME_AGE_UNIQUE",
                    columnNames = {"NAME", "AGE"} )})
```

`@Column`을 통한 unique 제약 조건

```
Hibernate: 
    create table Member (
        age integer unique,
        id bigint not null,
        name varchar(10) not null,
        primary key (id)
    )
```

`@Table`을 통한 unique 제약 조건

```
Hibernate: 
    create table Member (
        age integer not null,
        id bigint not null,
        name varchar(10) not null,
        primary key (id),
        constraint NAME_AGE_UNIQUE unique (NAME, AGE)
    )
```

-> `@Column`의 unique 속성은 간단한 unique 조건에서만 사용


매핑 어노테이션

| 어노테이션       | 설명                   |
|-------------|----------------------|
| @Column     | 컬럼 매핑                |
| @Temporal   | 날짜 타입 매핑             |
| @Enumerated | enum 타입 매핑           |
|@Lob| BLOB, CLOB 매핑        |
|@Transient| 특정 필드를 컬럼에 매핑하지 않는다. |

`@Column`

|속성|설명|기본값|
|-----|-----|-----|
|name|필드와 매핑할 테이블의 컬럼 이름|객체의 필드 이름|
|insertable, updateable|등록, 변경 가능 여부|True|
|nullbale(DDL)|null 값의 허용 여부, false일 경우 DDL 생성 시 not null 제약 조건||
|unique(DDL)|간단한 유니크 제약조건을 걸 경우||
|columnDefinition(DDL)|데이터베이스 컬럼 정보를 직접 줄 수 있다.|필드의 자바 타입과 방언 정보|
|length(DDL)|문자 길이 제약조건, String 타입에만 사용한다.|255|
|precision,scale(DDL)|BigDecimal,BigInteger 타입에서 사용. precision은 소수점을 포함한 전체 자리수, scale은 소수의 자리수. 아주 정밀한 소수를 다룰 때 사용|precision=19, scale=2|

`@Enumerated`

- enum 타입 매핑 시 사용
- EnumType.STRING을 무조건 사용한다. enum의 이름을 데이터베이스에 그대로 저장한다.

`@Temporal`

- 날짜 타입을 매핑할 때 사용
- 최신 하이버네이트의 LocalDate, LocalDateTime을 사용할 때는 생략 가능
- value
  - DATE: 날짜만, DB date 타입과 매핑
  - TIME: 시간만, DB time 타입과 매핑
  - TIMESTAMP: 날짜와 시간, DB timestamp 타입과 매핑

`@Lob`

- 데이터베이스 BLOB, CLOB 타입과 매핑
- 필드 타입이 문자면 CLOB 매핑, 나머지는 BLOB 매핑

`@Transient`

- 필드 중 DB 컬럼에 매핑하고 싶지 않은 값에 선언
- 데이터베이스에 저장되지 않고 조회되지 않는다.
- 메모리 상에서 임시로 어떤 값을 보관하고 싶을 때 사용한다.

기본 키 매핑

- 직접 할당: `@Id`만 사용
- 자동 생성: `@GeneratedValue`
  - IDENTITY: id 생성을 데이터베이스에 위임
  - SEQUENCE: 데이버테이스 쉬컨스 오브젝트 사용, ORACLE, `@SequenceGenerator`가 필요하다.
  - TABLE: 키 생성용 테이블 사용, 모든 DB에서 사용한다. `@TableGenerator`가 필요하다.
  - AUTO(DEFAULT): 현재 선언된 방언에 따라 자동 지정해준다.

`GenerationType.IDENTITY`

- MySQL(AUTO_INCREMENT), PostgreSQL, SQL Server, DB2에서 사용
- 영속성 컨텍스트에서 관리받기 위해서는 ID 값을 알아야 한다.
- JPA는 보통 커밋 직전에 몰아서 INSERT를 실행하므로 AUTO_INCREMENT가 설정된 엔티티의 경우 아직 ID 값을 알지 못한다.
- 그러므로, IDENTITY 전략이 설정되었을 경우 `em.persist()` 시 바로 INSERT 쿼리가 발생하여 식별자를 조회할 수 있게 해준다.

`GenerationType.SEQUENCE`

- 데이터베이스 시퀀스는 유일한 값을 순서대로 생성하는 특별한 데이터베이스 오브젝트이다. (예: 오라클 시퀀스)
- 오라클, PostgreSQL, DB2, H2 데이터베이스에서 사용
- SEQUENCE 또한, id 값을 알아야 영속성 컨텍스트에서 저장하고 관리되기 때문에 DB에서 id 값을 가져와 설정해준다. insert 쿼리는 commit 직전에 발생한다.
- id 값을 가져오는 빈도는 allocationSize를 통해 설정할 수 있다.
- `@SequenceGenerator`
  - name: 식별자 생성기 이름
  - sequenceName: 데이터베이스에 등록되어 있는 시퀀스 이름
  - initialValue: DDL 생성 시에만 사용, 시퀀스 DDL을 생성할 때 처음 시작하는 수를 지정
  - allocationSize: 시퀀스 한 번 호출에 증가하는 수 (성능 최적화에 사용됨), 데이터베이스 시퀀스 값이 하나씩 증가하도록 설정되어 있으면 이 값을 반드시 1로 설정해야 한다.
  - catalog, schema: 데이터베이스 catalog, schema

```java
@Entity
@SequenceGenerator(
    name = "MEMBER_SEQ_GENERATOR",
    sequenceName = "MEMBER_SEQ",
    initialValue = 1, allocationSize = 1)
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE,
            generator = "MEMBER_SEQ_GENERATOR")
    private Long id;
```

TABLE
- 키 생성 전용 테이블을 하나 만들어서 데이터베이스 시퀀스를 흉내내는 전략
- 장점은 모든 데이터베이스에 적용 가능하지만, 단점은 성능이다.
- 시퀀스는 새로운 id값을 전달하는 것에 최적화되어 있지만, 이 테이블은 그렇지 않기 때문에 성능적으로 좋지는 않다.

식별자 전략

- 기본 키 제약 조건: null 아님, 유일, 변하면 안된다.
- 이 조건을 만족하는 자연키를 찾기는 어렵다. 대리키(대체키: 비즈니스와 상관없는 값)를 사용하자.
- 권장: Long 형 + 대체키 + 키 생성전략 사용 -> AUTO_INCREMENT, SEQUENCE, 회사 내 전략을 통해 생성해서 사용하자!

---

## 섹션 5

연관 관계가 필요한 이유

> '객체지향 설계의 목표는 자율적인 객체의 협력 공동체를 만드는 것이다.' - 조영호(객체지향의 사실과 오해)

객체를 DB 테이블에 맞춰 설계할 경우 다른 객체의 외래키 값을 통해 관계를 맺게 된다.

하나의 객체를 조회하고 이 객체와 연관된 객체를 얻기 위해 외래키 값을 직접 다뤄서 다른 조회하는 것은 객체 지향적인 방법이 아니며 비효율적이다.

객체를 테이블에 맞추어 데이터 중심으로 설계하면 좋은 협력 관계를 만들 수 없다.

- 테이블은 외래키 조인을 통해 연관된 테이블을 찾을 수 있다.
- 객체는 참조를 통해 연관된 객체를 찾을 수 있다. 객체 상에서 외래키를 통해 연관된 객체를 찾는 것은 비효율 적이다.
- 테이블과 객체는 이러한 차이점이 있다는 것을 명심하자

단방향 연관관계

- Member와 Team이라는 객체가 있을 경우 Member 객체가 Team에 관계를 맺고 있다고 가정하자.
- Member 클래스 필드 상에서 Team 객체를 가지고 있다면 Member 객체 상에서 Team 객체에 대한 조회가 가능하다.
- 테이블 중심의 설계와 다르게 외래키 값이 아닌 실제 객체에 대한 참조를 필드값으로 가진다!
- `@OneToOne`, `@ManyToOne`, `@OneToMany` 등을 통해 관계 설정의 차수를 선언 할 수 있다.
- `@JoinColumn`을 통해 참조한 객체를 매핑시킬 외래키 컬럼 명을 정해준다.
- 연관관계 저장, 수정, 조회 시에도 `setXxx()`, `getXxx()`를 통해 연관관계를 저장할 수 있다.

```java
@Entity
public class Member {

  @ManyToOne
  @JoinColumn(name = "team_id")
  private Team team;
}
```

양방향 연관관계

> 양방향 연관관계에서는 주인을 결정해야 한다! 

- 양방향 연관관계는 단방향 연관관계가 두 개 있는 것뿐이다. 
- 각 객체가 연관된 객체의 참조값을 필드에 가지고 있는 것이다.
- mappedBy는 아래에서 설명...

```java
@Entity
public class Team{
  @OneToMany(mappedBy = "team")
  List<Member> members = new ArrayList<>();
}
```

연관관계의 주인

- 테이블은 외래키를 기준으로 양방향 연관관계를 맺는다.
- 객체는 각 객체가 연관된 객체로 향하는 단방향 연관관계 2개로 양방향 연관관계를 맺는다.
- 하나의 외래키로 양방향 관계를 맺는 테이블과 다르게 양방향 관계를 가질 시 두 개의 연관관계가 있는 객체는 어떤 것으로 외래키를 관리할지 결정해주어야 한다. 이것을 연관관계의 주인이라고 한다.
  - 극단적인 상황으로 Member의 team 객체에는 값이 저장이 되고 Team의 members에는 값이 저장되지 않거나, Team의 members에 값을 넣어주었으나 Member의 Team에 값을 설정해주지 않았을 경우 어떤 것을 기준으로 DB에 넣어주어야 할지 모르게 된다.
- 그러므로 두 연관관계 중 연관관계의 주인을 결정해 외래키를 관리하도록 해야한다.
  - 양방향 매핑 규칙
    - 연관관계 주인만이 외래키를 관리한다. (등록, 수정)
    - 주인이 아닌쪽은 읽기만 가능하다.
    - 주인은 mappedBy 속성을 사용하지 않는다.
    - 주인이 아니면 mappedBy 속성을 통해 이 연관관계의 주인을 지정해준다.
- 연관관계의 주인은 테이블의 외래키를 컬럼으로 가지고 있는 곳을 주인으로 하자(거의 Many쪽이 주인이 된다.)

양방향 매핑 주의할 점

- 주인 쪽에 연관관계를 주입하지 않으면 입력하지 않으면 안된다. 연관관계의 주인만이 값을 수정하고 등록할 수 있기 때문에 역방향만 연관관계를 설정하면 DB에 반영되지 않는다.
  - 사실 양방향 연관관계라면 두 객체에 모두 값을 넣어주는 것이 좋다.
    - 테스트 시 JPA 관계 없이 순수 자바 코드로만 테스트하는 경우를 대비
    - db에 insert하기 전에 조회할 경우 역방향에서는 조회되지 않을 수 있기 때문
  - 연관관계 편의 메서드를 생성해 한 번에 두 개 모두 관계를 주입해주자
- 양방향 매핑 시 무한 루프를 주의해야 한다. (toString(), lombok, JSON 생성 라이브러리)
- 단방향 매핑만으로도 이미 연관관계 매핑은 완료된 것이다. (양방향 매핑은 역방향 조회만 추가된 것뿐 테이블에는 영향이 없다.)
- 단방향 매핑을 잘 하고 양방향은 필요할 때 추가하도록 하자(JPQL 시 역방향 탐색이 많이 발생하긴 함)

> 연관관계의 주인은 외래 키의 위치를 기준으로 정하자
> 
> 처음부터 단방향 매핑을 잘 설계하자. 이 후 추가적으로 양방향 매핑이 필요한 곳에만 추가해주자
> 
> 양방햐 매핑일 경우 연관관계 편의 메서드를 통해 한 번에 연관관계를 주입해주자.

---

## 섹션 6

연관관계 매핑 시 고려사항

- 다중성 (1:1, 1:N, N:1, N:M)
  - `@OneToOne`
  - `@OneToMany`
  - `@ManyToOne`
  - `@ManyToMany`
- 단방향, 양방향
  - 테이블은 외래키 하나로 양쪽 조인을 통해 조회 가능(방향이라는 개념이 없음)
  - 객체는 참조용 필드가 있는 쪽에서만 참조 가능
- 연관관계의 주인
  - 테이블과 객체가 연관관계를 맺는 방법의 차이가 있기 때문에 외래키를 관리할 주인을 정해야 한다.
  - 외래키를 가진 테이블의 객체가 주인이되고, 주인의 반대편은 단순 조회만 가능하다.

다대일

- 단방향
  - 가장 많은 관계
- 양방향
  - "다"쪽에 외래키가 존재하므로 연관관계의 주인이다.
  - 다대일 양방향은 역방향에서 주인을 참조하는 필드가 추가된 것 뿐!

일대다

- 단방향
  - "일"에서 외래키를 관리하는 주인이 된다. (테이블의 외래키는 항상 "다"쪽에 존제)
  - 반대편 테이블의 외래키를 관리하는 특이한 구조가 된다.
  - @JoinColumn을 꼭 사용해야 한다. 그렇지 않으면 새로운 조인 테이블을 생성함
  - 엔티티가 관리하는 외래키가 다른테이블에 존재하므로 연관관계를 관리하기 위해 추가적인 update가 실행될 수 있다.
    - "일"쪽에서 참조하는 "다"에 대한 데이터 등록을 할 경우 관련 외래키에 대한 수정을 해줘야하는데 외래키는 반대편 테이블에 존재하기 때문에
  - 일대다 단방향 매핑이 필요할 경우 차라리 다대일 양방향 매핑을 사용하자
- 양방향
  - 공식적으로 존재하지 않는다.
  - "다"쪽에 `@JoinColumn(insertable=false, updatable=false)`로 선언해 읽기 전용 필드로 만들어 양방향처럼 사용할 수 있다.
  - 다시 한 번! 다대일 양방향을 사용하자!

일대일 관계

- 일대일 관계는 반대도 일대일 관계이다.
- 주 테이블이나 대상 테이블 중에 외래 키 선택이 가능하다.
- 외래 키에 데이터베이스 유니크 제약 조건이 추가되어야 일대일관계가 된다.
- 주 테이블에 외래 키 단방향 (Member(주), Locker: Member로 Locker를 조회하는 경우가 많으므로)
  - 단방향
    - 다대일 단방향 매핑과 유사
  - 양방향
    - 역방향에 참조를 추가해주면 된다.
    - 외래키가 있는 곳이 연관관계 주인이 된다. (반대편에 mappedBy 선언)
  - 장점: 주 테이블만 조회해도 대상 테이블에 데이터가 있는지 확인 가능 (외래키값으로 확인)
  - 단점: 값이 없으면 외래 키에 null 허용
- 대상 테이블에 외래 키 단방향
  - 단방향
    - JPA가 지원하지 않는다.
  - 양방향
    - 일대일 주 테이블에 외래 키 양방향과 매핑 방법이 같다
  - 장점: 주 테이블과 대상 테이블을 일대일에서 일대다 관계로 변경할 때 테이블 구조 유지 (Member가 여러 개의 Locker를 가질 수 있도록 허용할 경우 Locker에 외래키가 있다면 unique 제약조건만 없애주면 끝!)
  - 단점: 프록시 기능의 한계로 지연 로딩으로 설정해도 항상 즉시 로딩된다. (필드에 값이 있는지 없는지 알아야 하는데, 해당 값을 알려고 하면 지연로딩이라하더라도 그 값을 알아야하므로 즉시 로딩되게 된다..: 프록시 기능)

다대다
- 관계형 데이터베이스는 테이블 2개로 다대다 관계를 표현할 수 없다. 연결 테이블을 추가해 일대다 다대일 구조로 풀어내야한다.
- 객체는 컬랙션을 활용해 객체 2개로 다대다 관계가 가능하다.
- `@ManyToMany`, `@JoinTable`을 통해 연결 테이블을 지정할 수 있다.
  - 편리해보이지만 실무에서는 사용하면 안된다.
  - 추가적인 필드값을 넣을 수 없다.
  - 쿼리문이 원하느대로 동작되지 않을 수 있다.(중간에 매핑 테이블이 존재하기 때문에)

-> 연결 테이블용 엔티티를 추가한다. `@ManyToOne`과 `@OneToMany`를 통해 풀어낸다.

> 다대일 관계는 외래키가 존재한 테이블이 주인이 된다. (양방향 관계일 경우)
> 
> 일대다 관계 보다는 다대일 관계를 사용하자.
> 
> 일대일 관계는 주, 대상 테이블 혹은 외래 키의 위치를 확인하고 설계하자
> 
> 다대다 관계는 연결 테이블용 엔티티를 추가해서 사용하자.

---

## 섹션 7

상속 관계 매핑

- 관계형 데이터베이스에는 상속관계라는 개념이 없다.
- 슈퍼타입 서브타입 관계가 객체 상속과 유사하다.
- 상속관계 매핑은 객체의 상속 구조와 DB의 슈퍼타입 서브타입 관계를 매핑하는 것이다.
- 슈퍼타입 서브타입 논리 모델 -> 실제 물리 모델로 구현
  - 부모 자식 클래스 각각을 테이블로 변환 -> 조인 전략
  - 부모 자식을 통합 테이블로 변환 -> 단일 테이블 전략
  - 서브타입 테이블로 변환(부모에 공통 필드를 자식 필드에 모두 통합) -> 구현 클래스마다 테이블 전략

관련 어노테이션

- `@Inheritance(strategy=InheritanceType.XXX)`
  - `JOINED`: 조인 전략
  - `SINGLE_TABLE`: 단일 테이블 전략
  - `TABLE_PER_CLASS`: 구현 클래스마다 테이블 전략
- `@DiscriminatorColumn(name="DTYPE")`
  - 해당 컬럼이 어떤 서브타입인지를 구분해주는 컬럼 생성
- `@DiscrimnatorValue("xxx")`
  - 해당 컬럼이 어떤 서브타입인지 명시: 실제 컬럼에 넣을 값

조인 전략

- 장점
  - 테이블을 정규화된 형태로 매핑할 수 있다.
  - 외래 키 참조 무결성 제약조건 활용 가능 (외래키 값은 `null`이거나 관계가 있는 릴레이션의 기본키 값이어야 한다)
  - 다른 테이블에서 해당 테이블의 슈퍼클래스만 조회하면 해당 값을 알 수 있다.
  - 정규화된 테이블로 저장공간의 효율화
- 단점
  - 조회 시 조인으로 인해 성능 저하
  - 조회 쿼리가 복잡하다
  - 데이터 저장 시 INSERT가 2번 호출된다.

단일 테이블 전략

- 장점
  - 조인이 필요 없으므로 일반적으로 조회 성능이 빠르다
  - 하나의 테이블에서 모두 조회가 가능하므로 조회 쿼리가 단순하다.
- 단점
  - 자식 엔티티가 매핑한 컬럼은 모두 `null`이 허용된다.
  - 단일 테이블에 모든 것을 저장하므로 테이블이 커질 수 있다. 상황에 따라 조회 성능이 오히려 느려질 수 있다.

구현 클래스마다 테이블 전략

- 데이터베이스 설계자와 ORM 전문가 둘 다 추천하지 않는 방법이다.
- 장점
  - 서브 타입을 명확하게 구분해서 처리할 때 효과적이다.
  - `not null` 제약 조건 사용 가능
- 단점
  - 여러 자식 테이블을 함께 조회할 때 모든 서브타입 테이블을 UNION해서 값을 찾아야 한다.
  - 자식 테이블을 통합해서 쿼리하기 힘들다. (아이템 가격의 총합을 구할 때 등등)

`@MappedSuperclass`

- 공통 매핑 정보가 필요할 때 사용합니다. (createTime, updateTime 등등)
- 상속관계로 매핑되는 것이 아니고 필드들이 모두 반영되는 것입니다.
- 엔티티로 존재하지 않고 테이블로도 매핑되지 않습니다.
- 부모 클래스를 상속받은 자식 클래스에 매핑 정보만 제공합니다.
- 조회, 검색 불가(`em.find(BaseEntity)` 불가)
- 직접 생성해서 사용할 일이 없으므로 추상 클래스를 권장

-> 테이블 관계 없이 단순히 엔티티가 공통으로 사용하는 매핑 정보를 모아주는 역할

참고. `@Entity` 클래스는 엔티티나 `@MappedSuperclass`로 지정한 클래스만 상속 가능하다.

> 실전에서는 훨씬 복잡한 테이블 관계가 나타난다. 이러한 관계에서는 단순하게 설계하는 것이 최선이기 때문에 단일 테이블에 모두 넣는 방법을 사용할 수 있다.
> 
> 테이블을 단순하게 유지하기 위해서 자식 클래스들을 json으로 넣는 방식도 고려해보면 좋다.
> 
> 정답은 없다. 우선은 객체 지향적인 설계를 따르고 이후에 다른 설계를 고려해보는 것이 좋다.
> 
> 등록일, 수정일 등 모든 클래스에는 존재하지만 따로 엔티티로 관리할 필요 없는 것들은 @MappedSuperclass를 사용하자

---

## 섹션 8

프록시

- `em.find()`는 데이터베이스를 통해 실제 엔티티 객체 조회.
- `em.getReference()`는 데이터베이스 조회를 미루는 가짜(프록시) 엔티티 객체를 조회한다.
- 특징
  - 실제 클래스를 상속 받아서 만들어진다.
  - 사용자 입장에서는 진짜 객체인지 프록시 객체인지 구분하지 않고 사용하면 된다.
  - 실제 객체의 참조(target)를 보관
  - 프록시 객체의 메서드를 호출하면 프록시 객체는 실제 객체의 메서드를 호출한다.

프록시 객체 초기화

- `em.getReference(<클래스명>, Id)`를 통해 프록시 객체를 조회한다.
- 프록시 객체는 실제 클래스를 상속받아 만들어진다.
- 프록시 객체를 통해 필드값을 조회하는 메서드를 실행하면 영속성 컨텍스트에 실제 값의 초기화 요청을 그 때 한다.
- 영속성 컨텍스트는 실제 값을 DB에 조회하고 해당 엔티티를 생성해준다.
- 프록시 객체가 생성된 엔티티를 타겟으로 참조할 수 있도록함으로써 실제 필드값을 조회할 수 있게 된다.

프록시 특징

- 프록시 객체는 처음 사용할 때 한 번만 초기화된다.
- 프록시 객체를 초기화할 경우 프록시 객체가 실제 엔티티로 바뀌는 것이 아니라, 프록시 객체를 통해 실제 엔티티에 접근이 가능하게 되는 것이다.
- 프록시 객체는 원본 엔티티를 상속 받는다. 따라서 타입 체크시 주의해야 한다.
  - `<Proxy_Class>.getClass() == <Target_class>.getClass()` = `false`이다.
  - `instanceof`를 사용해야 한다.
- 영속성 컨텍스트에 찾는 엔티티가 이미 있다면 `em.getReference()`를 호출해도 실제 엔티티가 반환된다.
- `em.getReferenc()`를 호출해 프록시 객체가 생성된 뒤, em.find()로 실제 엔티티를 받기 위해 시도하면 프록시 객체가 반환된다.
- 영속성 컨텍스트의 도움을 받을 수 없는 준영속 상태일 경우, 프록시를 초기화하면 오류가 발생한다. (`org.hibernate.LazyInitializationException`)
  - 영속성 컨텍스트는 한 트랜잭션 내부에서 깉은 라이프사이클을 갖는 경우가 많은데 트랜잭션이 끝난 뒤 프록시를 초기화 하는 경우 많이 발생한다.

프록시 관련 유틸리티

- `PersistenceUnitUtil.isLoaded(Object entity)` : 프록시 인스턴스의 초기화 여부 확인
- `entity.getClass().getName()` 출력 : 프록시 클래스 확인 방법
- `org.hibernate.Hibernate.initialize(entity)` : 프록시 강제 초기화
  - JPA 표준은 강제 초기화가 없으므로, 객체 필드값 조회 시 강제 초기화 된다

즉시, 지연 로딩

- 객체를 조회할 경우 참조하고 있는 객체도 한 번에 조회하면 즉시 로딩 (`FetchType.EAGER`)
  - 참조하고 있는 객체가 존재할 경우 모두 join을 통해 한 번에 조회한다.
- 객체를 조회할 경우 참조하고 있는 객체를 조회하지 않으면 지연 로딩 (`FetchType.LAZY`)
  - 지연 로딩 시 참조하고 있는 객체는 프록시 객체로 조회한다.
  - 해당 객체를 실제 조회하는 시점에 조회 쿼리가 실행되며 초기화 된다.

즉시 로딩 주의점

- 즉시 로딩 사용 시 예상하지 못한 SQL이 발생한다.
  - A가 B와 C를 모두 참조하고 있을 때, A와 B만 조회하고 싶지만 A와B A와C join 쿼리가 함께 발생할 수 있다.
- 즉시 로딩은 JPQL에서 N+1 문제를 일으킨다.
  - 클래스 A와 B가 연관관계를 맺고 있고 JPQL 쿼리로 모든 A를 조회하면, 실제 조회 쿼리 한 개가 먼저 발생한 뒤, 즉시 로딩이라는 것을 깨닫고 각 A객체들이 연관된 B 객체를 조회하기 위해 다시 조인 쿼리를 발생시키게 된다.

> 가급적 지연 로딩만 사용해야 한다. 
> 
> `@ManyToOne`, `@OneToOne`은 기본이 즉시 로딩이다. -> 지연 로딩으로 수정해주어야 한다.
> 
> 두 테이블을 동시에 조회하고 싶다면 JPQL fetch 조인이나, 엔티티 그래프 기능을 사용하자

CASCADE

- 영속성 전이 속성이다.
- 특정 엔티티를 영속 상태로 만들 때 연관된 엔티티도 함께 영속 상태로 만들고 싶을 때 사용한다.
- 영속성 전이는 연관관계를 매핑하는 것과 아무 관련이 없다.
- 엔티티를 영속화할 때 연관된 엔티티도 함께 영속화하는 편리한 기능을 제공할 뿐이다.
- 종류 
  - `ALL`: 모두 적용
  - `PERSIST`: 영속
  - `REMOVE`: 삭제
  - `MERGE`: 엔티티 상태를 병합(merge) 시 연관 엔티티도 병합
  - `REFRESH`: 상위 엔티티 새로고침 시 연관 엔티티도 새로고침
  - `DETACH`: 준영속상태가 되면 연관된 엔티티도 `detach()`

고아 객체
- 고아 객체 제거: 부모 엔티티와 연관관계가 끊어진 자식 엔티티를 자동으로 삭제
- `orphanRemoval = true`
- 참조하는 곳이 하나일 때 사용해야 한다. (여러 곳에 연관관계를 맺고 있는 객체라면 어떤 상황이 벌어질지 모른다.)
- 특정 엔티티가 개인 소유할 경우 사용하면 좋다. (라이프 사이클이 동일한 두 연관 객체에 사용해야 한다.)

영속성 전이 + 고아 객체

- `CascadeType.ALL` + `orphanRemoval=true`
- 두 옵션을 모두 활성화하면 부모 엔티티를 통해 자식의 생명 주기를 관리할 수 있다.
- 도메인 주도 설계(DDD)의 Aggregate Root 개념을 구현할 때 유용하다.

> 프록시 객체는 실제 객체가 DB로부터 조회되기 전에 생성되는 객체이다.
> 
> 지연 로딩 시 조회된 객체가 참조하고 있는 객체는 프록시 객체로 생성이 된다.
> 
> 즉시 로딩은 절대 사용하지 말자.
> 
> Cascade, orphanRemoval은 비즈니스적인 로직이나 영속성 관리의 편의를 위해 필요하다면 설정하자

---

## 섹션 9

JPA 데이터 타입 분류

- 엔티티 타입
  - `@Entity`로 정의하는 객체
  - 데이터가 변해도 식별자로 지속해서 추적 가능
- 값 타입
  - `int`, `Integer`, `String`처럼 단순히 값으로 사용하는 자바 기본 타입, 객체
  - 식별자가 없고 값만 있으므로 변경 추적 불가
  - 분류
    - 기본값 타입
      - 자바 기본 타입 : `int`, `double`
      - 래퍼 클래스 : `Integer`, `Long`
      - `String`
    - 임베디드 타입 : Embedded type, 복합 값 타입
    - 컬렉션 값 타입 : collection value type

기본값 타입
- `String`, `int` 등등
- 생명주기를 엔티티에 의존
- 값 타입은 다른 엔티티와 공유하면 안된다.
  - 값 타입 변경 시 다른 엔티티에도 영향을 주면 안된다.
- 기본 타입(`int`, `double`)은 공유가 불가능하다.
- `Integer`나 `String` 같은 클래스는 공유 가능하지만 변경은 불가능하다.

-> 기본 값 타입을 사용했을 경우 공유가 불가능하거나 값에 대한 변경이 불가능하므로 안전하게 사용할 수 있었다.

임베디드 타입(복합 값 타입)

- 새로운 값 타입을 직접 정의해서 사용할 수 있다.
- JPA는 임베디드 타입이라고 한다.
- 주로 기본 값 타입을 모아서 만들어서 복합 값 타입이라고 한다.
- `int`, `String`과 같이 값 타입이다.
- `@Embeddable` : 값 타입을 정의하는 곳에 표시
- `@Embedded` : 값 타입을 사용하는 곳에 표시
- 기본 생성자를 필수로 정의해두어야 한다.
- 임베디드 타입의 값이 null이라면 매핑한 컬럼 값은 모두 null이다.
- 한 엔티티에 같은 값 타입을 사용할 경우 컬럼명을 재정의할 때 `@AttributeOverride`를 사용한다.

임베디드 타입 장점

- 재사용 가능하다
- 높은 응집도를 가질 수 있다. (임베디드 타입 클래스 내부에 메서드를 정의함으로써 해당 값에 필요한 기능들을 응집해서 사용할 수 있다.)
- 임베디드 타입 또한 값 타입과 마찮가지로 소유한 엔티티에 생명주기를 의존함

임베디드 타입, 테이블 매핑

- 임베디드 타입은 엔티티의 값일 뿐이다.
- 임베디드 타입을 사용하기 전과 후에 매핑하는 테이블은 같다
- 잘 설계된 ORM 어플리케이션은 매핑한 테이블 수보다 클래스 수가 더 많다.

값 타입과 불변 객체

- 임베디드 타입 같은 값 타입을 여러 엔티티에서 공유할 경우 부작용이 발생할 수 있다.
- 값 타입의 실제 인스턴스인 값을 공유하는 것은 위험하다.

```java
Address address = new Address("city1", "14111")
Member member1 = new Member();
Member member2 = new Member();
  
member1.setAddress(address);
member2.setAddress(address);
  
member1.getAddress().setCity("new city");
// 의도: member1의 city를 new city로 변경
// 실제: member1뿐만 아니라 member2에도 변경이 일어남
```

-> 대신 값(instance)을 복사해서 사용해주면 된다. 

`Address newAddress = new Address(address.getCity(),address.getZipcode())`

객체 타입의 한계

- 항상 값을 복사해서 사용해야 공유 참조로 인한 부작용을 피할 수 있다.
- 임베디드 타입처럼 직접 정의한 값 타입은 자바의 기본 타입이 아니라 객체 타입으로 이런 값 복사는 따라오게 된다..
- 객체 타입은 참조 값을 직접 대입하는 것을 막을 수 없다. 객체의 공유 참조는 피할 수 없다.

불변 객체

- 객체 타입을 수정할 수 없게 만들면 부작용을 없앨 수 있다.
- 값 타입은 불변 객체로 설계해야 한다.
- 불변 객체 : 생성 시점 이후 절대 값을 변경할 수 없는 객체
- 생성자로만 값을 설정하고 수정자를 만들지 않으면 된다.
- `Integer`, `String`은 자바가 제공하는 대표적인 불변 객체이다.

값 타입의 비교

- 값 타입은 인스턴스가 달라도 안에 있는 값이 같다면 같다고 봐야 한다.
- 동일성 비교: 인스턴스의 참조 값을 비교 (`==` 사용)
- 동등성 비교: 인스턴스의 값을 비교 (`equals()` 사용)
- 값 타입은 `equals()`를 통해 동등성 비교를 해야 한다. (`equals` 메서드를 재정의 해주어야 한다.)

값 타입 컬렉션

- 값 타입을 하나 이상 저장할 경우 사용
- 데이터 베이스는 컬렉션을 저장할 수 있는 수단이 없다.
- 컬렉션을 저장하기 위한 별도의 테이블이 필요하다.
- `@ElementCollection`, `@CollectionTable`을 통해 값타입의 테이블을 생성할 수 있다.
- 값 타입 컬렉션은 지연 로딩 전략을 사용한다.
- 값 타입 컬렉션은 영속성 전이(Cascade) + 고아 객체(orphanRemoval) 기능을 필수로 가진다.

값 타입 컬렉션 제약 사항

- 값 타입은 엔티티와 다르게 식별자 개념이 없다. (변경의 추적이 어렵다.)
- 값 타입 컬렉션에 변경 사항이 발생하면, 주인 엔티티와 연관된 모든 데이터를 삭제하고, 값 타입 컬렉션에 있는 현재 값을 모두 다시 저장한다.
- 값 타입 컬렉션을 매핑하는 테이블은 모든 컬럼을 묶어서 기본 키를 구성해야 한다. (NotNull, 중복 저장 X)

값 타입 컬렉션 대안

- 실무에서 상황에 따라 값 타입 컬렉션 대신에 일대다 관계를 고려한다.
- 일대다 관계를 위한 엔티티를 만들고, 여기에서 값 타입을 사용한다.
- `CascadeType.ALL`과 `orphanRemoval=True`를 통해 값 타입 컬렉션처럼 사용한다.

> 엔티티 타입은 식별자로 추적이 가능하고 생명주기를 스스로 관리한다. 공유가 가능하다.
> 
> 값 타입은 식별자가 없어 추적이 불가능하고 생명주기를 엔티티에 의존한다. 공유하지 않고 불변 객체로 만들어 사용하는 것이 안전하다.
> 
> 식별자가 필요하고 지속적으로 값을 추적, 변경해야 한다면 그것은 값 타입이 아닌 엔티티이다.

---

## 섹션 10

JPA 쿼리 방법

- **JPQL**
- JPA Criteria
- **QueryDSL**
- 네이티브 SQL
- JDBC API 직접 사용, MyBatis, SpringJdbcTemplate 함께 사용

JPQL

- JPA가 제공하는 SQL을 추상화한 객체 지향 쿼리 언어
- SQL과 문법 유사하다. (`SELECT`, `FROM`, `WHERE`, `GROUP BY`, `HAVING`, `JOIN`)
- JPQL은 엔티티 객체를 대상으로 쿼리한다.

```java
String sql = "select m from Member m where m.name like '%hello%'";
List<Member> result = em.createQuery(jpql, Member.class)
        .getResultList();
```

- 테이블이 아닌 객체를 대상으로 검색하는 객체 지향 쿼리
- SQL을 추상화해서 특정 데이터베이스 SQL에 의존하지 않는다.

> JPQL은 객체 지향 SQL

Criteria

- 문자가 아닌 자바 코드로 JPQL을 작성할 수 있다.
- JPQL 빌더 역할
- JPA 공식 기능
- 단점: 너무 복잡하고 실용성이 없다.

-> Criteria 대신 QueryDSL 사용을 권장한다.

QueryDSL

- 문자가 아닌 자바 코드로 JPQL을 작성할 수 있다.
- JPQL 빌더 역할
- 컴파일 시점에 문법 오류를 찾을 수 있다.
- 동적 쿼리 작성이 편리하다.
- 단순하고 쉽다.
- **실무 사용을 권장한다.**

네이티브 SQL

- JPA가 제공하는 SQL을 직접 사용하는 기능
- JPQL로 해결할 수 없는 특정 데이터베이스에 의존하는 기능

기타 조회 방법

- JDBC 커넥션을 직접 사용, 스프링 JdbcTemplate, MyBatis 등
- 영속성 컨텍스트를 적절한 시점에 강제로 플러시 해주어야 한다.

JPQL 소개

- JPQL은 객체 지향 쿼리 언어이다. 따라서 테이블 대상이 아닌 엔티티 객체를 대상으로 쿼리한다.
- JPQL은 SQL을 추상화해서 특정 데이터베이스 SQL에 의존하지 않는다.
- JPQL은 SQL로 변환된다.

JPQL 문법

- 엔티티와 속성은 대소문자를 구분한다.
- JPQL 키워드는 대소문자를 구분하지 않는다.
- 엔티티 이름을 통해 객체를 조회한다. (테이블 이름이 아니다.)
- 별칭은 필수이다. (`as` 생략 가능)
- `select m from Member m where m.age > 18`

집합

- `COUNT()`: 갯수
- `SUM()`: 총합
- `AVG()`: 평균
- `MAX()`: 최대값
- `MIN()`: 최소값

정렬

- `GROUP BY`, `HAVING`
- `ORDER BY`

반환 타입 관련

- `TypedQuery` = 반환 타입이 명확할 때 사용
  ```java
  TypedQuery<Member> query = em.createQuery("SELECT m FROM Member m", Member.class);
  ```
- `Query` = 반환 타입이 명확하지 않을 때 사용
  ```java
  Query query = em.createQuery("SELECt m.username, m.age from Member m");
  ```
  
결과 조회 방법

- `query.getResultList()`
  - 결과가 하나 이상일 때, 리스트 반환
  - 결과가 없으면 빈 리스트 반환
- `query.getSingleResult()`
  - 결과가 없으면: `javax.persistence.NoResultException`
  - 결과가 2개 이상이면: `javax.persistence.NonUniqueResultException`

파라미터 바인딩

- **이름 기준**:
  ```java
  String query = "select m from Member m where m.username = :username"
  em.createQuery(query,Member.class)
    .setParameter("username", usernameParam)
    .getResultList(); 
  ```
- 위치 기준
  ```java
  String query = "select m from Member m where m.username = ?1"
  em.createQuery(query,Member.class)
    .setParameter(1, usernameParam)
    .getResultList(); 
  ```
  
프로젝션

- `SELECT` 절에 조회할 대상을 지정하는 것을 말한다.
- 프로젝션 대상: 엔티티, 임베디드 타입, 스칼라 타입(숫자, 문자 등 기본 데이터 타입)
  - 엔티티 프로젝션: `select m from Member m`
  - 엔티티 프로젝션: `select m.team from Member m` <- 별 좋은 방법은 아니다. 조인이 사용되면 명시적으로 표현해주는 게 좋다!
  - 임베디드 타입 프로젝션: `select m.address from Member m`
  - 스칼라 타입 프로젝션: `select m.username from Member m`
  - `DISTINCT` 사용 가능

값 조회 방법

- `SELECT m.username, m.age FROM Member m` 에서 조회한 것들을 반환받는 방법
- `Query` 타입으로 조회
- `Object[]` 타입으로 조회
- new 명령어로 조회
  - 위와 같은 경우 조회할 값을 필드로 갖는 DTO 클래스를 생성해 반환 받는다.
  - `select new {패키지명}.MemberDTO(m.username,m.age) from Member m`
  - 패키지 명을 포함한 전체 클래스 명 사용
  - DTO 클래스에 순서와 타입이 일치하는 생성자가 필요하다.

페이징

- JPA는 페이징을 두 API로 추상화
- `setFirstResult(int startPosition)`: 조회 시작 위치
- `setMaxResults(int maxResult)` : 조회할 데이터 수
- 위 두 api를 통해 대부분의 DB 언어에 대한 페이징 기능 제공

조인

- 내부 조인
  - `select m from Member m [inner] join m.team t`
- 외부 조인
  - `select m from Member m left [outer] join m.team t`
- 세타 조인
  - `select count(m) from Member m, Team t where m.username = t.name`

ON 절
- 조인 대상 필터링: join할 테이블에 조건을 더 해 필터링한 경우
- 연관 관계 없는 엔티티 외부 조인

서브 쿼리

- `[NOT] EXISTS (subquery)`: 서브쿼리에 결과가 존재하면 참
- `{ALL | ANY | SOME} (subquery)`
  - `ALL` 모두 만족하면 참
  - `ANY`, `SOME`: 같은 의미, 조건을 하나라도 만족하면 참
- `[NOT] IN (subquery)` : 서브 쿼리 결과 중 하나라도 같은 것이 있으면 참
- 한계
  - JPA는 `WHERE`, `HAVING` 절에서만 서브 쿼리가 가능하다.
  - `SELECT` 절도 가능(하이버네이트에서 지원)
  - (예전 강의이기 때문에 실제 구현해보면서 느껴봐야 될 듯..)

JPQL 타입 표현
- 문자: 'hello'
- 숫자: 1L, 1D, 1F
- boolean: TRUE, FALSE
- ENUM: 패키지명 포함 클래스명
- 엔티티 타입: `TYPE(m) = Member` 상속 관계에서 사용

JPQL 기타

- SQL과 문법이 같은 식
- `EXISTS`, `IN`
- `AND`, `OR`, `NOT`
- `=,` `>`, `>=`, `<`, `<=`, `<>`
- `BETWEEN`, `LIKE`, `IS NULL`
- 기본 CASE 식
- 조건식
  - `COALESCE`: 하나씩 조회해서 null이 아니면 반환, null이면 두번째 값 반환
    - `select coalesce(m.username, '이름 없는 회원') from Member m`
  - `NULLIF`: 두 값이 같으면 null 반환, 다르면 첫번째 값 반환
    - `select nullif(m.username, '관리자') from Member m`
- 기본 함수
  - `CONCAT`
  - `SUBSTRING`
  - `TRIM`
  - `LOWER`, `UPPER`
  - `LENGTH`
  - `LOCATE`
  - `ABS`, `SQRT`, `MOD`
  - `SIZE`, `INDEX`(JPA 용도)
- 사용자 정의 함수
  - 하이버네이트는 사용전 방언에 추가해야 한다.
  - 사용하는 DB 방언을 상속받고, 사용자 정의 함수를 등록한다.

> JPQL은 객체 지향적인 쿼리를 만들어 사용할 수 있도록 해준다.
> 
> 다른 쿼리 방식들 보다는 JPQL을 사용하고 동적 쿼리는 QueryDSL을 활용하자
> 
> 서브쿼리나 여러 기본 SQL 문법 지원 여부는 실제 코딩을 할 때 더 알아갈 수 있을 것 같다.

---

## 섹션 11

경로 표현식

```roomsql
select m.username 
from Member m
join m.team t
join m.orders o
where t.name = '팀A'
```
- `.`을 통해 객체 그래프를 탐색하는 것
- 상태 필드
  - 단순히 값을 저장하기 위한 필드 (`m.username`, `m.age`)
  - 경로 탐색의 끝, 더 이상 탐색하지 않는다.
- 연관 필드
  - 연관 관계를 위한 필드
  - 단일 값 연관 필드
    - `@ManyToOne`, `@OneToOne` 대상이 엔티티인 경우
    - 묵시적으로 내부 조인(inner join)을 발생시킨다. 이 후 더 탐색이 가능합니다.
  - 컬렉션 값 연관 필드
    - `@OneToMany`, `@ManyToMany` 대상이 컬렉션인 경우
    - 묵시적으로 내부 조인을 발생시킨다. 이 후로의 탐색을 지원하지 않는다.
    - `FROM` 절에서 명시적 조인을 통해 별칭을 얻으면 별칭을 통해 탐색 가능

조인

- 명시적 조인: join 키워드를 직접 사용
  - `select m from Member m join m.team t`
- 묵시적 조인: 경로 표현식에 의해 묵시적으로 SQL 조인 발생 (내부 조인만 가능)
  - `select m.team from Member m`

예제

- `select o.member team from Order o` (O)
- `select t.members from Team t` (O)
- `select t.members.username from Team t` (X, 컬렉션 값은 탐색 불가능)
- `select m.username from Team t join t.members m` (O)

경로 탐색 시 주의 사항

- 연관 필드 조회 시 항상 내부 조인 한다.
- 컬렉션은 경로 탐색의 끝으로 명시적 조인을 통해 별칭을 얻어 이 후의 탐색을 해야 한다.
- 경로 탐색은 주로 `SELECT`, `WHERE`절에 나타나지만 SQL문에서 `FROM` 절에 영향을 준다.

> 가급적 묵시적 조인 대신 명시적 조인을 사용하자
> 
> 조인은 SQL 튜닝의 중요 포인트이다.
> 
> 묵시적 조인은 조인이 일어나는 상황을 한눈에 파악하기 어렵다.

fetch join

- SQL 조인의 종류가 아니다.
- JPQL에서 성능 최적화를 위해 제공하는 기능이다.
- 연관된 엔티티나 컬렉션을 SQL **한 번에 함께 조회**하는 기능이다.
- `[LEFT [OUTER] | INNER] JOIN FETCH {조인 경로}`

엔티티 페치 조인

- 작성된 JPQL
  ```roomsql
  select m from Member m join fetch m.team
  ```
- 만들어진 SQL
  ```roomsql
  SELECT M.*, T.* FROM MEMBER M INNER JOIN TEAM T ON M.TEAM_ID=T.ID
  ```
  
컬렉션 패치 조인

- 작성된 JPQL
  ```roomsql
  select t from Team t join fetch t.members
  ```
- 만들어진 SQL
  ```roomsql
  SELECT T.*, M.* FROM Team t INNER JOIN Member M ON T.ID=M.TEAM_ID
  ```


DISTINCT

- SQL의 `DISTINCT`는 중복된 결과를 제거
- JPQL의 `DISTINCT`
  - SQL에 `DISTINCT` 추가
  - 어플리케이션 레벨에서 엔티티 중복을 제거

페치 조인과 `DISTINCT`

- SQL에 `DISTINCT`를 추가하지만 데이터가 다르므로 SQL 결과에서 중복 제거 실패한다.
- `DISTINCT`가 추가로 어플리케이션에서 중복 제거를 시도한다.
- 같은 식별자를 가진 TEAM 엔티티 제거
- 하이버네이트6부터는 `DISTINCT` 명령어를 사용하지 않아도 어플리케이션에서 중복이 자동 제거됩니다.

페치 조인과 일반 조인의 차이

- 일반 조인 실행 시 연관된 엔티티를 함께 조회하지 않는다.
  - `select t from Team t join  t.members m`를 통해 Team 객체를 가져올 경우 Team 객체만 가져올 뿐이다. Member 필드를 조회하면 다시 조회 쿼리가 발생한다.
- JPQL은 결과를 반환할 때 연관관계를 고려하지 않는다.
- 단지 `SELECT` 절에 지정한 엔티티만 조회할 뿐이다.
- 페치 조인을 사용할 때만 연관된 엔티티도 함께 조회된다.(즉시 로딩)
- 페치 조인은 객체 그래프를 SQL 한 번에 조회하는 개념이다.

페치 조인의 특징과 한계

- 페치 조인 대상에는 별칭을 주면 안된다.
  - 하이버네이트에서 가능하지만 가급적 사용하지 말자.
  - 패치 조인은 모든 연관된 데이터를 가져오기 위해 사용하는 것이다. 이 중 몇 개만 가져와서 수정하거나 한다면 `CASCADE` 등과 같은 옵션들로 인해 어떤 오류가 발생할지 모른다.
- 둘 이상의 컬렉션은 페치 조인할 수 없다.
- 컬렉션을 페치 조인하면 페이징 API를 사용할 수 없다.
  - 일대일, 다대일 같은 단일 값 연관 필드들은 페이징 가능하다.
  - 하이버네이트는 경고 로그를 남기고 메모리에서 페이징한다.
  - 위에서 SQL `DISTINCT`를 사용해도 엔티티의 중복을 피할 수 없다고 말했다. 그러므로 하이버네이트는 모든 데이터들을 들고와서 메모리에서 페이징하는 수밖에 없다...
- 연관된 엔티티들을 SQL로 한 번에 조회할 수 있다. 성능 최적화
- 엔티티에 직접 적용하는 글로벌 로딩 전략보다 우선한다. (`@ManyToOne`에 `FetchType.LAZY`여도 즉시 조회됨)
- 여러 테이블을 조인해서 엔티티가 가진 모양이 아닌 전혀 다른 결과를 내야된다면, 페치 조인 보다는 일반 조인을 사용하고 필요한 데이터들만 조회해서 DTO로 반환하는 것이 효과적이다.

JPQL 다형성

- TYPE: 조회 대상을 특정 자식으로 한정
  ```roomsql
  select i from Item i where type(i) IN (Book, Movie)
  ```
  ->
  ```roomsql
  select i from i where i.DTYPE in ('B', 'M')
  ```
- `TREAT`
  - 자바 타입 캐스팅과 유사
  - 상속 구조에서 부모 타입을 특정 자식 타입으로 다룰 때 사용
  - `FROM`, `WHERE`, `SELECT`에서 사용
  
  ```roomsql
  select i from Item i where treat(i as Book).author = ‘kim’
  ```
  ->
  ```roomsql
  select i.* from Item i where i.DTYPE = ‘B’ and i.author = ‘kim’
  ```
  
엔티티 직접 사용

- JPQL에서 엔티티를 직접 사용하면 SQL에서는 해당 엔티티의 기본 키 값을 사용한다.
  ```java
  String jpql = “select m from Member m where m = :member”;
  List resultList = em.createQuery(jpql)
                      .setParameter("member", member)
                      .getResultList();
  ```
- 실행된 SQL: `SELECT m.* from Member m where m.id=?`

Named 쿼리

- 미리 정의해서 이름을 부여하고 사용하는 JPQL
- 정적 쿼리
- 어노테이션, XML을 통해 정의
- 어플리케이션 로딩 시점에 초기화 후 재사용
- 어플리케이션 로딩 시점에 쿼리를 검증할 수 있다.
- XML 설정이 항상 우선권을 가진다. (어플리케이션 운영 환경에 따라 다른 XML을 배포할 수 있다.)
- Spring Data Jpa 사용 시 알아서 해준다!..

벌크 연산

- JPA의 변경 감지 기능을 실행하여 update하는 것이 아니라 한 번에 update한다.
- 쿼리 한 번으로 여러 테이블 로우를 변경
- `executeUpdate()`는 영향 받는 엔티티 수 반환
- `UPDATE`와 `DELETE`를 지원한다.
- 하이버네이트 구현체는 `INSERT INTO .. SELECT` 문도 지원한다.
- 벌크 연산은 영속성 컨텍스트를 무시하고 데이터베이스에 직접 쿼리하는 것이다.
  - 벌크 연산 실행 전에 영속성 컨텍스트의 내용을 모두 flush 하고 진행한다.
  - 벌크 연산 수행 후 영속성 컨텍스트를 초기화한다.

> 묵시적 `join` 보다는 명시적으로 `join`을 설정해주자.
> 
> 연관 엔티티, 컬렉션 조회 시 fetch `join`으로 성능 최적화하자
> 
> 벌크 연산을 통해 한 번의 쿼리로 여러 개 데이터를 변경하자