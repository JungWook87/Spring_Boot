package com.study.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;

@Configuration // 스프링은 @Configuration이 선언된 클래스를 자바(JAVA) 기반의 설정 파일로 인식한다.
               // 스프링 레거시의 XML 설정 방식을 Java 클래스로 대체한 것으로 생각하면 된다.
@PropertySource("classpath:/application.properties") // 해당 클래스에서 참조할 properties의 경로를 선언한다.
public class DatabaseConfig {

    @Autowired // 빈(Bean)으로 등록된 인스턴스(객체)를 클래스에 주입하는데 사용한다.
               // @Autowired 외에도 @Resource, @Inject 등이 존재한다
    private ApplicationContext context;
    // 스프링 컨테이너 중 하나이다
    // 스프링 컨테이너는 빈(Bean)의 생성과 사용, 관계, 생명 주기 등을 관리한다.
    // 빈(Bean)은 쉽게 말해 Java 객체이다. 예를 들어, 프로젝트에 100개의 클래스가 있다고 가정해보면,
    // 이 클래스들이 서로에 대한 의존성이 높다고 했을 때, "결합도가 높다"라고 표현하는데,
    // 이러한 문제를 컨테이너에서 빈(Bean)을 주입 받는 방법으로 해결할 수 있다.
    // 즉, 클래스간의 의존성을 낮출 수 있다.

    @Bean
    // Configuration 클래스의 메서드 레벨에만 선언이 가능하며, @Bean이 선언된 객체는 스프링 컨테이너에 의해 관리되는 빈(Bean)으로 등록된다
    // 해당 어노테이션은 인자로 몇 가지 속성(옵션)을 지정할 수 있다.
    @ConfigurationProperties(prefix = "spring.datasource.hikari")
    // 해당 어노테이션은 인자에 prefix 속성을 선언(지정)할 수 있다. prefix는 접두사 즉, 머리를 의미한다
    // 우리는 prefix에 spring.datasource.hikari를 선언했다. 쉽게 말해 @PropertySource에 선언된 파일(application.properties)에서
    // prefix에 해당하는 spring.datasource.hikari로 시작하는 설정을 모두 읽어 들여 해당 메서드에 맵핑하는 개념이다
    // 추가적으로 해당 어노테이션은 메서드 뿐만 아니라 클래스 레벨에도 선언할 수 있다.
    public HikariConfig hikariConfig(){
        return new HikariConfig();
        // hikariConfig는 히카리 CP 객체를 생성한다. 히카리 CP는 커넥션 풀 라이버리 중 하나이다.
    }

    @Bean
    public DataSource dataSource(){
        return new HikariDataSource(hikariConfig());
        // dataSource는 데이터 소스 객체를 생성한다. 순수 JDBC는 SQL을 실행할 때마다 커넥션을 맺고 끊는 I/O 작업을 하는데,
        // 이 작업은 상당한 양의 리소스를 잡아먹는다. 그리고 이 문제의 해결책으로 커넥션 풀이 등장했다.
        // 커넥션 풀은 커넥션 객체를 생성해두고, DB에 접근하는 사용자에게 미리 생성해둔 커넥션을 제공했다가 다시 돌려받는 방법이다.
        // 데이터 소스는 커넥션 풀을 지원하기 위한 인터페이스이다.
    }

    @Bean
    public SqlSessionFactory sqlSessionFactory() throws Exception {
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        factoryBean.setDataSource(dataSource());
        // factoryBean.setMapperLocations(context.getResouces("classpath:/mappers/**/*Mapper.xml"));
        return factoryBean.getObject();
        // sqlSessionFactory는 SqlSessionFactory 객체를 생성한다. SqlSessionFactory는 DB 커넥션과 SQL 실행에 대한 모든 것을 갖는 객체이다
        // SqlSessionFactoryBean은 FactoryBean 인터페이스의 구현 클래스로, 마이바티스(MyBatis)와 스프링의 연동 모듈로 사용된다
        // 쉽게 말해 factoryBean 객체는 데이터 소스를 참조하며, XML Mapper(SQL 쿼리 작성 파일)의 경로와 설정 파일 경로등의 정보를 갖는 객체이다
    }

    @Bean
    public SqlSessionTemplate sqlSession() throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory());
        // sqlSession은 sqlSession 객체를 생성한다. 마이바티스 공식 문서에는 다음과 같이 저장되어 있다.
        // 1. SqlSessionTemplate 는 마이바티스 스프링 연동 모듈의 핵심이다
        // 2. SqlSessionTemplate 는 SqlSession을 구현하고, 코드에서 SqlSession을 대체한다.
        // 3. SqlSessionTemplate 는 쓰레드에 안전하고, 여러 개의 DAO나 Mapper에서 공유할 수 있다.
        // 4. 필요한 시점에 세션을 닫고, 커밋 또는 롤백하는 것을 포함한 세션의 생명주기를 관리한다.

        // SqlSessionTemplate 는 SqlSessionFactory를 통해 생성되고, 공식 문서의 내용과 같이
        // DB의 커밋, 롤백 등 SQL의 실행에 필요한 모든 메서드를 갖는 객체로 생각할 수 있다.
    }
}
