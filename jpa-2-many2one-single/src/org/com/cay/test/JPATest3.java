package org.com.cay.test;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.com.cay.entity.Customer;
import org.com.cay.entity.Order;
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
	public void testManyToOnePersist(){
		Customer customer = new Customer();
		customer.setLastName("FF");
		customer.setEmail("ff@qq.com");
		customer.setAge(22);
		customer.setCreateTime(new Date());
		customer.setBirth(new Date());
		
		Order order1 = new Order();
		order1.setOrderName("order-1");
		order1.setCustomer(customer);
		
		Order order2 = new Order();
		order2.setOrderName("order-2");
		order2.setCustomer(customer);
		
		//ִ�б������
		//�Ƽ����ȱ���һ��һ�ˣ��ٱ�����һ�ˣ������Ͳ����������update���
		entityManager.persist(customer);
		entityManager.persist(order1);
		entityManager.persist(order2);
	}
	
	//Ĭ��ʹ���������ӵķ�ʽ����ȡ�ö��һ�˵Ķ�����������һ��һ�˵Ķ��󣬿���ͨ��ʹ�ö��һ�˵�@ManyToOne��fetch�������޸ļ��ز���
	@Test
	public void testManyToOneFind(){
		Order order = entityManager.find(Order.class, 2);
		System.out.println(order.getOrderName());
		
		System.out.println(order.getCustomer().getLastName());
	}
	
	@Test
	public void testManyToOneRemove(){
//		Order order = entityManager.find(Order.class, 1);
//		entityManager.remove(order);
		
		//�������Ҫɾ����һ��һ���ڶ��һ�������ݹ���������ֱ��ɾ��
		Customer customer = entityManager.find(Customer.class, 7);
		entityManager.remove(customer);
	}
	
	@Test
	public void testManyToOneUpdate(){
		Order order = entityManager.find(Order.class, 2);
		order.getCustomer().setLastName("GG");
	}
}
