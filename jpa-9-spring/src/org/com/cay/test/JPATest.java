package org.com.cay.test;

import javax.sql.DataSource;

import org.com.cay.entity.Person;
import org.com.cay.service.PersonService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext.xml"})
public class JPATest {

//	private ApplicationContext ctx = null;
//	{
//		ctx = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
//	}
	
	@Autowired
	private DataSource dataSource;
	
	@Autowired
	private PersonService personService;
	
	@Test
	public void testJPA(){
	//	dataSource = (DataSource) ctx.getBean("dataSource");
		System.out.println(dataSource);
	}
	
	@Test
	public void testPersonService(){
		Person p1 = new Person();
		p1.setAge(10);
		p1.setName("AA");
		p1.setEmail("aa@qq.com");
		
		Person p2 = new Person();
		p2.setAge(29);
		p2.setName("BB");
		p2.setEmail("bb@qq.com");
		
		System.out.println(personService.getClass().getName());
		personService.saves(p1, p2);
	}
}
