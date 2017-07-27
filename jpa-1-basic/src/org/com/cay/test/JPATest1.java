package org.com.cay.test;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.com.cay.entity.Customer;
import org.junit.Test;

public class JPATest1 {

	@Test
	public void test() {
		//1、创建EntityManagerFactory
		String persistenceUnitName = "jpa-1";
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(persistenceUnitName);
		
		//2、创建EntityManager
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		
		//3、开启事务
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		
		//4、进行持久化操作
		Customer customer = new Customer();
		customer.setLastName("Cay");
		customer.setAge(22);
		customer.setEmail("Cay@qq.com");
		customer.setCreateTime(new Date());
		customer.setBirth(new Date());
		entityManager.persist(customer);
		
		//5、提交事务
		transaction.commit();
		
		//6、关闭EntityManager
		entityManager.close();
		
		//7、关闭EntityManagerFactory
		entityManagerFactory.close();
	}

}
