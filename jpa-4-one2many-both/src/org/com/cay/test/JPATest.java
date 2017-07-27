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
	
	//�ڽ���˫��1-n������ϵʱ������ʹ��n��һ����ά��������ϵ����1��һ�˲�ά��������ϵ����������Ч�ļ���sql���
	//ע�⣺����1��һ�˵�@OneToMany��ʹ��mappedBy���ԣ���@OneToMany�˲�����ʹ��@JoinColumn����
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
		
		//����������ϵ
		//order1.setCustomer(customer);
		//order2.setCustomer(customer);
		customer.getOrders().add(order1);
		customer.getOrders().add(order2);
		
		order1.setCustomer(customer);
		order2.setCustomer(customer);
		
		//ִ�б������
		//�Ƽ����ȱ���һ��һ�ˣ��ٱ�����һ�ˣ������Ͳ����������update���
		entityManager.persist(customer);
		entityManager.persist(order1);
		entityManager.persist(order2);
	}
	
	//Ĭ��ʹ�������ز�������ȡ��������Ķ��󣬿���ͨ��ʹ�ö��һ�˵�@ManyToOne��fetch�������޸ļ��ز���
	// ͨ���޸�@OneToMany��fetch���޸�Ĭ�ϵļ��ز���
	@Test
	public void testOneToManyFind(){
		Customer customer = entityManager.find(Customer.class, 8);
		System.out.println(customer.getLastName());
		
		System.out.println(customer.getOrders().size());
	}
	
	//Ĭ������£���ɾ��1 ��һ�ˣ�����Ȱѹ�����n��һ�˵������Ϊ�գ�Ȼ����ɾ��1��һ��  
	@Test
	public void testOneToManyRemove(){
//		Order order = entityManager.find(Order.class, 1);
//		entityManager.remove(order);
		
		// ͨ���޸�@OneToMany��cascade���޸ļ�������
		Customer customer = entityManager.find(Customer.class, 9);
		entityManager.remove(customer);
	}
	
	@Test
	public void testManyToOneUpdate(){
		Customer customer = entityManager.find(Customer.class, 10);
		customer.getOrders().iterator().next().setOrderName("OOO");
	}
}
