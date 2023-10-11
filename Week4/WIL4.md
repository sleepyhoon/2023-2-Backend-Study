# 과제

1. DI에 대해서 정리하자.

   ## 의존성 주입?

    ```java
    @Component
    public class ClassB {
        private ClassA classA;
    
        public ClassB(ClassA classA) {
            this.classA = classA;
        }
    }
    ```

   3주차 과제의 ClassB 이다.
   모든 코드를 살펴봐도 ClassB 에 ClassA 를 주입해주는 코드가 없는데,
   ClassB 가 빈으로 등록되어있고, 사용 또한 가능하다.
   이는 스프링이 내부적으로 의존성을 주입해줬기 때문이다.
   우리는 이를 스프링이 DI, 의존성 주입을 해줬다 라고 한다.
   DI란 외부에서 두 객체 간의 관계를 결정해주는 디자인 패턴으로,
   인터페이스를 사이에 둬서 클래스 레벨에서는 의존 관계가 고정되지 않도록 하고
   런타임 시에 관계를 동적으로 주입하여 유연성을 확보하고 결합도를 낮출 수 있게 해준다.
   의존 관계 주입은 크게 4가지 방법이 있다.

    - 생성자 주입
    - 수정자 주입(setter 주입)
    - 필드 주입
    - 일반 메소드 주입

   ### **@Autowired**

   스프링에는 여러가지 의존성 주입이 있지만 가장 간편하게 쓰이는 방법으로는 @Autowired 어노테이션을 활용하는 방법이 있다.

   “여기에 의존성을 주입해줘”라는 뜻입니다.

   @Autowired를 붙이게 되면 Spring이 자동으로 해당 클래스의 객체를 찾아서 필요한 의존성을 주입해줍니다.

    ```java
    @Component
    public class Pizza {
        @Autowired
        private Cheese cheese;
    }
    ```

   다만 여기서 주입받을 Cheese 도 스프링 Bean이어야 한다.

   ### 생성자 주입

   생성자를 통해 의존 관계를 주입하는 방법이다.
   생성자 주입을 사용하면 객체의 최초 생성 시점에 스프링이 의존성을 주입해준다.
   그렇기 때문에 주입받은 객체가 변하지 않거나, 반드시 객체의 주입이 필요한 경우에 강제하기 위해 사용할 수 있다.
   또한 Spring 프레임워크에서는 생성자 주입을 적극 지원하고 있기 때문에,
   생성자가 1개만 있을 경우에 @Autowired 를 생략해도 주입이 가능하도록 편의성을 제공하고 있다.
   만약 @Autowired 가 붙은 생성자가 여러 개 있을 경우 가장 많은 의존성을 주입할 수 있는 생성자를 사용해서 의존성 주입한다.

    ```java
    @Component
    public class Pizza {
        private Cheese cheese;
        private Bread bread;
    
        // @Autowired
        public Pizza(Cheese cheese, Bread bread) {
            this.cheese = cheese;
            this.bread = bread;
        }
    }
    ```

   ### setter 주입

   [data:image/gif;base64,R0lGODlhAQABAIAAAP///wAAACH5BAEAAAAALAAAAAABAAEAAAICRAEAOw==](data:image/gif;base64,R0lGODlhAQABAIAAAP///wAAACH5BAEAAAAALAAAAAABAAEAAAICRAEAOw==)

   **setter 주입**

   setter라 불리는 필드의 값을 변경하는 수정자 메서드를 통해서 의존관계를 주입하는 방법이다.

   선택, 변경 가능성이 있는 의존관계에 사용할 수 있다.

   setter 메서드에 @Autowired 어노테이션을 붙이면 스프링이 setter를 사용해서 자동으로 의존성을 주입해준다.

   이때 빈 객체를 만들고 setter로 의존성을 주입해주기 때문에 빈 생성자가 필요하다.

   때문에 파이널 필드를 만들 수 없고 의존성의 불변을 보장할 수 없다는 특징이 있다.

    - 파이널 필드 : 초기값이 저장되면 이것이 최종값이 되어 프로그램 실행 도중 변경 불가능

    ```java
    @Component
    public class Pizza {
        private Cheese cheese;
        private Bread bread;
    
        public void setCheese(Cheese cheese) {
            this.cheese = cheese;
        }
    
        public void setBread(Bread bread) {
            this.bread = bread;
        }
    }
    ```

   ### 필드 주입

   말 그대로 필드에 바로 주입하는 방법이다.

    ```java
    @Component
    public class Pizza {
        @Autowired
        private Cheese cheese;
        @Autowired
        private Bread bread;
    }
    ```

   하지만 필드 주입은 더 이상 추천되는 방법이 아니며 심지어 인텔리제이가 경고도 해준다.

   필드 주입을 사용하게 되면 테스트 등의 이유로 자동이 아닌 수동 의존성을 주입하고 싶어도,

   생성자, setter가 없으므로 우리가 직접 의존성을 넣어줄 수가 없다.

   때문에 필드 주입을 사용하게 되면 의존성이 프레임워크에 강하게 종속된다는 문제점이 있다.

   애플리케이션의 실제 코드와 관계 없는 테스트 코드, 스프링 설정을 목적으로 하는 @Configuration 같은 곳에서만 특별한 용도로 사용해야한다.

   ### 일반 메소드 주입

   [data:image/gif;base64,R0lGODlhAQABAIAAAP///wAAACH5BAEAAAAALAAAAAABAAEAAAICRAEAOw==](data:image/gif;base64,R0lGODlhAQABAIAAAP///wAAACH5BAEAAAAALAAAAAABAAEAAAICRAEAOw==)

   한번에 여러 필드를 주입 받을 수 있다.

   하지만 일반적으로 잘 안쓴다

    ```java
    @Component
    public class Pizza {
        private Cheese cheese;
        private Bread bread;
    
        @Autowired
        public void init(Cheese cheese, Bread bread) {
            this.cheese = cheese;
            this.bread = bread;
        }
    }
    ```

   ### DI 주의점

    - **NullPointerException 방지**

   필드 주입이나 setter 주입의 경우 스프링의 빈 관리 기능을 빌리지 않고 new 키워드로 객체를 생성해 줄 경우, NullPointerException이 발생할 수 있다.

   왜냐면 이들은 빈 생성자를 사용해 기본적으로 의존성이 없는 상태이기 때문이다.

   하지만 생성자 주입은 (완전한 생성자라는 가정 하에) 객체 생성 시점에 모든 의존성을 주입해주므로

   Null을 의도적으로 넣어주지 않는 한 NullPointerException이 발생할 수 없다.

    - **순환 참조 문제 방지**

   필드 주입이나 setter 주입을 통해 의존성을 주입하게 되면,

   A 객체가 B 객체를 의존하는데 B 객체 또한 A 객체를 의존할 때 생기는 순환참조가 발생할 수 있다.

   그러나 생성자 주입을 사용하는 객체들끼리 의존성이 순환되면 스프링은 에러 메시지와 함께 프로그램을 종료한다.

    ```java
    @Component
    public class CircuitA {
        @Autowired
        private CircuitB b;
    
        public void doB() {
            b.doA();
        }
    }
    ```

   code A

    ```java
    @Component
    public class CircuitB {
        @Autowired
        private CircuitA a;
    
        public void doA() {
            a.doB();
        }
    }
    ```

   code B

   code A와 code B 같은 코드가 있다면 A가 B의 doSomething을 호출하면 B가 다시 A의 doSomething을 호출하고 순환 호출이 반복되다가 결국 stackoverflow 에러가 발생해서 프로그램이 멈추게 될 것이다.

    - stackoverflow: 지정한 스택 메모리 사이즈보다 더 많은 스택 메모리를 사용하게 되어 에러가 발생하는 상황

   https://oopy.lazyrockets.com/api/v2/notion/image?src=https%3A%2F%2Fprod-files-secure.s3.us-west-2.amazonaws.com%2F81276f83-e80d-4730-b0ae-1b519fac7648%2F77717ca2-a353-4b46-a3bf-407e9c38327c%2F%25E1%2584%2589%25E1%2585%25B3%25E1%2584%258F%25E1%2585%25B3%25E1%2584%2585%25E1%2585%25B5%25E1%2586%25AB%25E1%2584%2589%25E1%2585%25A3%25E1%2586%25BA_2023-09-24_%25E1%2584%258B%25E1%2585%25A9%25E1%2584%2592%25E1%2585%25AE_4.40.59.png&blockId=9c5d3b47-6c3e-4d68-b86f-f4a3bc87fc0c

   만약 생성자 주입을 이용한다면 애플리케이션 시작시에 위의 에러를 통해서 방지해줄 수 있다.

   하지만 스프링 2.6 버전부터 setter 주입과 필드 주입도 위의 에러로 알려줌!

   ### 주입 대상이 여러 가지일 때

    ```java
    @Component
    public class Pizza {
        private Cheese cheese;
        private Bread bread;
    		@ 생성자 주입
        public Pizza(Cheese cheese, Bread bread) {
            this.cheese = cheese;
            this.bread = bread;
        }
    }
    ```

    ```java
    public interface Cheese {
    }
    ```

    ```java
    @Component
    public class CheddarCheese implements Cheese{
    }
    ```

    ```java
    @Component
    public class MozzarellaCheese implements Cheese{
    }
    ```

   만약 Pizza 에서 Cheese 를 의존성 주입하고 싶을 때,
   Bean에 MozzarellaCheese 와 CheddarCheese 가 둘 다 등록되어 있을 때 어떤 것을 선택해야하는지 모른다.

   이때는 아래 순서대로 기준을 정한다
    1. 타입
    2. 이름
       타입이 1순위이다. 우선 정의되어있는 타입을 기준으로 찾는다.
       CheddarCheese 와 MozzarellaCheese 서비스 모두 Cheese 의 구현체임으로 Cheese 라는 타입으로 검색된다.
       이렇게 타입을 기준으로 여러 Bean이 검색되었다면 스프링은 그 다음으로 Bean의 이름을 기준으로 의존성을 주입한다.
       이때 주입하는 데 사용하는 메서드의 매개변수명과 등록된 Bean의 이름이 일치하는지 체크한다.

    ```java
    @Component
    public class Pizza {
        private Cheese cheese;
        private Bread bread;
    
        public Pizza(Cheese cheddarCheese, Bread bread) {
            this.cheese = cheddarCheese;
            this.bread = bread;
        }
    }
    ```

   그래서 생성자 매개변수명을 cheddarCheese 로 바꿔주면 자동으로 CheddarCheese Bean이 주입되고 생성에 성공하게 된다.

   하지만 이런 식으로 매개변수명을 바꿔버리면 수동으로 CheddarCheese 를 넣어줘야 하는 경우에는 헷갈린다. 자동으로 주입해주려는 Bean을 바꿀 때도 귀찮아진다. 그렇다면 어떻게 해야할까?

   @Qualifier 또는 @Primary 를 이용하는 방법이 있다.

   ### @Qualifier

    ```java
    @Component
    @Qualifier("defaultCheese")
    public class MozzarellaCheese implements Cheese {
    }
    ```

    ```java
    @Component
    public class Pizza {
        private Cheese cheese;
        private Bread bread;
    
        public Pizza(@Qualifier("defaultCheese") Cheese cheese, Bread bread) {
            this.cheese = cheese;
            this.bread = bread;
        }
    }
    ```

   위 코드처럼 @Qualifier 어노테이션 안에 해당 Bean의 구분자를 지정해줄 수 있다.

   MozzarellaCheese 에 @Qualifier 어노테이션을 붙여서 defaultCheese 라고 수정해주고 의존성을 주입받을 부분에 같은 어노테이션을 작성하면   MozzarellaCheese 가 주입된다.

   ### @Primary

    ```java
    @Component
    @Primary
    public class MozzarellaCheese implements Cheese {
    }
    ```

    ```java
    @Component
    public class Pizza {
        private Cheese cheese;
        private Bread bread;
    
        public Pizza(Cheese cheese, Bread bread) {
            this.cheese = cheese;
            this.bread = bread;
        }
    }
    ```

   @Primary 어노테이션이 붙은 Bean은 해당 타입으로 의존성 검색을 할 때 우선적으로 주입된다.

   일종의 기본 Bean이 되는 것이다.

   ### 의존성 주입 기준

   위 의존성 주입에 대한 우선순위는 다음과 같다.
   타입 → @Qualifier → @Primary → 변수명

   ### Lombok?

   사실 위에 소개한 방법도 쓰이긴 하는데 잘 안쓴다.

   아래 소개할 내용이 더 편하기 때문이다.

   Lombok 이라는 Java 라이브러리가 있다.

   반복적인 코드를 줄이는 데 도움을 주는 어노테이션 기반의 도구를 제공해준다.

   getter, setter, equals, hashCode 및 toString 메서드 등을 Lombok의 어노테이션을 사용하여 해당 코드를 자동으로 생성할 수 있다.

   위에서 말했던 생성자 또한 그러하다. 아래는 Lombok이 제공해주는 생성자이다.

   DI 방법 중, 생성자 주입을 자동으로 설정해준다.

    - @NoArgsConstructor : 파라미터가 없는 기본 생성자를 생성
    - @RequiredArgsConstructor : final 또는 @NonNull로 표시된 필드만을 파라미터로 하는 생성자를 생성
    - @AllArgsConstructor : 모든 필드 값을 파라미터로 받는 생성자를 생성

   이는 아래와 같이 쓰일 수 있다.

    ```java
    @Component
    public class Pizza {
        private Cheese cheese;
        private Bread bread;
    
        public Pizza(Cheese cheese, Bread bread) {
            this.cheese = cheese;
            this.bread = bread;
        }
    }
    ```

   원래 이런 방식으로 @Autowired 를 이용하여 DI를 진행하던 코드였지만,

   Lombok을 이용한다면 아래와 같이 바꿀 수 있다.

    - @RequiredArgsConstructor

    ```java
    @Component
    @RequiredArgsConstructor
    public class Pizza {
        private final Cheese cheese;
        private final Bread bread;
    }
    ```

   이 생성자는 final 필드만 주입해줘서, 코드를 약간 변경했습니다.

    - @AllArgsConstructor(Class 모든 필드 값을 파라미터로 받는 생성자를 추가)

    ```java
    @Component
    @AllArgsConstructor
    public class Pizza {
        private Cheese cheese;
        private Bread bread;
    }
    ```

2. Spring boot에 대해서 정리하자.

### 왜 Spring boot가 만들어졌는가?

스프링은 JEE나 J2EE로 알려진 자바 엔터프라이즈 에디션을 경량화하려는 대안으로 시작했다.
컴포넌트 코드 작성은 가벼웠으나 개발 구성은 무거웠다.
즉, 프로젝트 시작시 설정해야할 내용이 많아진다.
이 모든 구성 작업은 개발 저항으로 나타난다.
이에 대응하기 위해 스프링 부트가 만들어졌다.

### ****Spring Boot란?****

Spring Boot는 스프링 기반의 어플리케이션를 빠르게 개발하고 실행하기 위한 프레임워크이다.

위 개발 저항을 해결하기 위해 Spring Boot가 만들어졌다.

개발에 필요한 복잡한 설정을 Spring Boot가 대신 해줌으로써,

개발자의 편리한 Spring 사용에 도움을 준다.

### ****Spring Boot의 특징****

1. WAS(Web Application Server)

Tomcat 같은 웹 서버를 내장해서 별도의 웹 서버를 설치하지 않아도 됨

1. 라이브러리 관리

손쉬운 빌드 구성을 위한 스타터 종속성 제공 및 라이브러리 버전 관리

1. 자동 구성
    - 프로젝트 시작에 필요한 스프링과 외부 라이브러리의 빈을 자동 등록
    - 스프링 애플리케이션에 공통으로 필요한 애플리케이션 기능을 자동으로 구성
2. 외부 설정

환경에 따라 달라져야 하는 외부 설정 공통화

1. 프로덕션 준비

모니터링을 위한 메트릭, 상태 확인 기능 제공

- 스프링 애플리케이션 컨텍스트에 구성된 빈
- 스프링 부트의 자동 구성으로 구성된 것
- 애플리케이션에서 사용할 수 있는 환경 변수, 시스템 프로퍼티, 구성 프로퍼티, 명령줄 인자
- 최근에 처리된 HTTP 요청 정보
- 메모리 사용량, 가비지 컬렉션, 웹 요청, 데이터 소스 사용량 등 다양한 메트릭

### ****왜 Spring Boot를 사용할까?****

간단히 말해서 Spring Boot가 Spring 보다 더 편하다.

위 Spring Boot의 특징에서 볼 수 있듯,

Spring에서 개발했다면 설정해줬어야 할 것들을 미리 설정해준다.

이에 많은 개발자가 Spring 어플리케이션 개발을 위해 Spring Boot를 이용한다.

1. DI를 완료한 후 스프링을 실행할 경우, 아래와 같은 로그를 볼 수 있다. 로그를 캡쳐하여 WIL에 명시하라.
   <img width="908" alt="스크린샷 2023-10-11 150208" src="https://github.com/sleepyhoon/2023-1-OC-Web-Study/assets/101882530/49c6f458-b061-43ce-afc1-d78ab47ff208">
