package org.com.cay.test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.com.cay.entity.Department;
import org.com.cay.entity.Manager;
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
	//注意：若在1的一端的@OneToMany中使用mapperBy属性，则@OneToMany端不能再使用@JoinColumn属性
	@Test
	public void testOneToOnePersist(){
		Manager mgr = new Manager();
		mgr.setMgrName("M-AA");
		
		Department dept = new Department();
		dept.setDeptName("D-AA");
		
		//设置关联关系
		mgr.setDept(dept);
		dept.setMgr(mgr);
		
		//执行保存操作
		//推荐：先保存不维护关联关系的一方，即没有外键的一方
		entityManager.persist(mgr);
		entityManager.persist(dept);
	}
	
	//默认情况下，
	//1、若获取维护关联关系的一方，则会通过左外连接的方式获取与其关联的对象
	// 通过修改@OneToOne的fetch属性来修改默认的加载策略
	@Test
	public void testOneToOneFind(){
		Department dept = entityManager.find(Department.class, 1);
		System.out.println(dept.getDeptName());
		
		System.out.println(dept.getMgr().getClass().getName());
	}
	
	//默认情况下，
	//1、若获取不维护关联关系的一方，则也会通过左外连接的方式获取与其关联的对象
	//即使修改不维护关联关系的一方的fetch属性，也无法减少sql语句，所以不建议在不维护关联关系的一方设置fetch属性
	@Test
	public void testOneToOneFind2(){
		Manager mgr = entityManager.find(Manager.class, 1);
		
		System.out.println(mgr.getMgrName());
		
		System.out.println(mgr.getDept().getClass().getName());
	}
	
//	//默认情况下，若删除1 的一端，则会先把关联的n的一端的外键置为空，然后再删除1的一端  
//	@Test
//	public void testOneToManyRemove(){
////		Order order = entityManager.find(Order.class, 1);
////		entityManager.remove(order);
//		
//		// 通过修改@OneToMany的cascade来修改级联操作
//		Customer customer = entityManager.find(Customer.class, 9);
//		entityManager.remove(customer);
//	}
//	
//	@Test
//	public void testManyToOneUpdate(){
//		Customer customer = entityManager.find(Customer.class, 10);
//		customer.getOrders().iterator().next().setOrderName("OOO");
//	}
}
