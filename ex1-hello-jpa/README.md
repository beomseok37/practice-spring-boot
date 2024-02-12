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
