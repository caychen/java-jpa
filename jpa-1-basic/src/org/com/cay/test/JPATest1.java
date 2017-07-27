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
		//1������EntityManagerFactory
		String persistenceUnitName = "jpa-1";
		EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory(persistenceUnitName);
		
		//2������EntityManager
		EntityManager entityManager = entityManagerFactory.createEntityManager();
		
		//3����������
		EntityTransaction transaction = entityManager.getTransaction();
		transaction.begin();
		
		//4�����г־û�����
		Customer customer = new Customer();
		customer.setLastName("Cay");
		customer.setAge(22);
		customer.setEmail("Cay@qq.com");
		customer.setCreateTime(new Date());
		customer.setBirth(new Date());
		entityManager.persist(customer);
		
		//5���ύ����
		transaction.commit();
		
		//6���ر�EntityManager
		entityManager.close();
		
		//7���ر�EntityManagerFactory
		entityManagerFactory.close();
	}

}
