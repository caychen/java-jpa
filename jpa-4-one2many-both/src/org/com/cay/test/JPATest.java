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

public class JPATest {

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
	
	//在进行双向1-n关联关系时，建议使用n的一端来维护关联关系，而1的一端不维护关联关系，这样会有效的减少sql语句
	//注意：若在1的一端的@OneToMany中使用mappedBy属性，则@OneToMany端不能再使用@JoinColumn属性
	@Test
	public void testOneToManyPersist(){
		Customer customer = new Customer();
		customer.setLastName("FF");
		customer.setEmail("ff@qq.com");
		customer.setAge(22);
		customer.setCreateTime(new Date());
		customer.setBirth(new Date());
		
		Order order1 = new Order();
		order1.setOrderName("order-1");
		
		Order order2 = new Order();
		order2.setOrderName("order-2");
		
		//建立关联关系
		//order1.setCustomer(customer);
		//order2.setCustomer(customer);
		customer.getOrders().add(order1);
		customer.getOrders().add(order2);
		
		order1.setCustomer(customer);
		order2.setCustomer(customer);
		
		//执行保存操作
		//推荐：先保存一的一端，再保存多的一端，这样就不会多出额外的update语句
		entityManager.persist(customer);
		entityManager.persist(order1);
		entityManager.persist(order2);
	}
	
	//默认使用懒加载策略来获取与其关联的对象，可以通过使用多的一端的@ManyToOne的fetch属性来修改加载策略
	// 通过修改@OneToMany的fetch来修改默认的加载策略
	@Test
	public void testOneToManyFind(){
		Customer customer = entityManager.find(Customer.class, 8);
		System.out.println(customer.getLastName());
		
		System.out.println(customer.getOrders().size());
	}
	
	//默认情况下，若删除1 的一端，则会先把关联的n的一端的外键置为空，然后再删除1的一端  
	@Test
	public void testOneToManyRemove(){
//		Order order = entityManager.find(Order.class, 1);
//		entityManager.remove(order);
		
		// 通过修改@OneToMany的cascade来修改级联操作
		Customer customer = entityManager.find(Customer.class, 9);
		entityManager.remove(customer);
	}
	
	@Test
	public void testManyToOneUpdate(){
		Customer customer = entityManager.find(Customer.class, 10);
		customer.getOrders().iterator().next().setOrderName("OOO");
	}
}
