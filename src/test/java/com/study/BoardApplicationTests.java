package com.study;
// 스프링은 단위 테스트를 위한 환경과 다양한 기능들을 제공한다.
// 일반적으로 단위 테스트는 비즈니스 로직 또는 SQL 쿼리에 문제가 있는지 확인하는 용도로 사용된다
// WAS(톰캣)을 구동하지 않은 상태에서도 테스트가 가능하기 때문에 시간적인 측면에서 상당히 유리하다

import org.apache.ibatis.session.SqlSessionFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest
class BoardApplicationTests {

	@Autowired
	private ApplicationContext context;

	@Autowired
	private SqlSessionFactory sessionFactory;

	@Test
	void contextLoads() {
	}

	@Test
	public void testByApplicationContext(){
		try{
			System.out.println("====================");
			System.out.println(context.getBean("sqlSessionFactory"));
			System.out.println("====================");
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	/* 테스트 결과
	* org.apache.ibatis.session.defaults.DefaultSqlSessionFactory@1d7f2f0a
	* 실행한 테스트 코드에서는 context 객체의 getBean() 메서드에 "sqlSessionFactory"를 전달했다.
	* 여기서 context는 스프링 컨테이너 중 하나인 ApplicationContext의 객체이며, 스프링 컨테이너는 빈(Bean)의 생명 주기를 관리한다.
	* 즉, 스프링 컨테이너에 담겨있는 SqlSessionFatory 빈(Bean)을 꺼내오는 개념으로 이해하면 된다.
	* getBean() 메서드의 인자로 전달한 문자열이 SqlSessionFactory 빈의 메서드명이라는 것이 핵심이다
	* */


	@Test
	public void testBySqlSessionFactory(){
		try{
			System.out.println("====================");
			System.out.println(sessionFactory.toString());
			System.out.println("====================");
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	/* 테스트 결과
	* org.apache.ibatis.session.defaults.DefaultSqlSessionFactory@6248cfab
	* 마찬가지로 객체의 주소 값이 콘솔에 출력되었다.
	* */

	/* 앞의 두 테스트 코드 모두 SqlSessionFactory 타입의 객체이다.
	* "컨테이너에서 빈(Bean)을 이런식으로 주입해 주는구나" 정도만 기억하자
	* */

	@Test
	public void testByApplicationContext2(){
		try{
			System.out.println("====================");
			System.out.println(context.getBean("abc"));
			System.out.println("====================");
		} catch (Exception e){
			e.printStackTrace();
		}
	}
	/* sqlSessionFactory의 @Bean 어노테이션에 name 속성을 이용하여 객체를 주입받는 방법
	* SqlSessionFactory 빈(Bean)의 이름을 "abc"로 변경해서 테스트한 결과
	* org.apache.ibatis.session.defaults.DefaultSqlSessionFactory@531a716c
	* 주의점!! name 속성을 선언하면 메서드명으로 빈을 주입받을 수 없게 되는데,
	* 만약 name이 지정된 상태에서 메서드명으로 getBean() 메서드를 호출하면 예외가 발생한다
	* "sqlSessionFactory라는 이름의 빈(Bean)이 없다" 라는 메시지가 출력된다
	* */


	/* 테스트 코드 실행하기
	* 클래스에서 메서드 이름을 더블 클릭하면 블록이 잡히는데 이 상태에서 마우스를 우클릭한 후
	* "Run... Test" 또는 "Debug... Test"를 실행해주면 된다.
	* 메서드의 실행결과는 콘솔창에서 확인할 수 있다.
	* */
}
