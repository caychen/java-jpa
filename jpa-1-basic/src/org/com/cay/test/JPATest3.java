package org.com.cay.test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.com.cay.entity.Gender;
import org.com.cay.entity.Person;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class JPATest3 {

	private EntityManagerFactory entityManagerFactory;
	private EntityManager entityManager;
	private EntityTransaction entityTransaction;

	@Before
	public void init() {
		entityManagerFactory = Persistence.createEntityManagerFactory("jpa-1");
		entityManager = entityManagerFactory.createEntityManager();

		entityTransaction = entityManager.getTransaction();
		entityTransaction.begin();
	}

	@After
	public void destory() {
		entityTransaction.commit();
		entityManager.close();
		entityManagerFactory.close();
	}
	
	@Test
	public void testEnum(){
		Person person = new Person("Cay",Gender.MALE);
		entityManager.persist(person);
		
	}
}
