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
		
		//执行保存操作
		//推荐：先保存一的一端，再保存多的一端，这样就不会多出额外的update语句
		entityManager.persist(customer);
		entityManager.persist(order1);
		entityManager.persist(order2);
	}
	
	//默认使用左外连接的方式来获取该多的一端的对象和其关联的一的一端的对象，可以通过使用多的一端的@ManyToOne的fetch属性来修改加载策略
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
		
		//如果将需要删除的一的一端在多的一端有数据关联，则不能直接删除
		Customer customer = entityManager.find(Customer.class, 7);
		entityManager.remove(customer);
	}
	
	@Test
	public void testManyToOneUpdate(){
		Order order = entityManager.find(Order.class, 2);
		order.getCustomer().setLastName("GG");
	}
}
