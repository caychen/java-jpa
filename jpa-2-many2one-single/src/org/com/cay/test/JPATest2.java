package org.com.cay.test;

import java.util.Date;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;

import org.com.cay.entity.Customer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class JPATest2 {

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

	// EntityManager的find方法类似Hibernate中Session的get方法
	@Test
	public void testFind() {
		Customer customer = entityManager.find(Customer.class, 1);
		System.out.println("--------------");
		System.out.println(customer);
	}

	// EntityManager的getReference方法类似Hibernate中Session的load方法
	@Test
	public void testGetReference() {
		Customer customer = entityManager.getReference(Customer.class, 1);
		System.out.println("--------------");
		System.out.println(customer);
	}

	// 类似与Hibernate中Session的save方法,使对象由游离状态变为持久状态
	// 注意，和hibernate的save方法的不同之处：若对象有id，则不能执行insert操作，而是抛出异常
	@Test
	public void testPersist() {
		Customer customer = new Customer();
		customer.setLastName("BB");
		customer.setEmail("bb@qq.com");
		customer.setAge(22);
		customer.setCreateTime(new Date());
		customer.setBirth(new Date());

		// 加入以下设置id的操作会引发异常
		// customer.setId(222);

		entityManager.persist(customer);
		System.out.println(customer.getId());
	}

	// EntityManager的remove方法类似Hibernate中Session的delete方法,把对象对应的记录从数据库中移除
	// 但注意：该方法只能移除持久化对象，而hibernate的delete方法实际上还可以移除游离对象。
	@Test
	public void testRemove() {
		// 以下会抛出异常，因为jpa的remove不能移除游离状态的对象
		// Customer customer = new Customer();
		// customer.setId(4);
		// entityManager.remove(customer);

		Customer customer = entityManager.find(Customer.class, 4);
		entityManager.remove(customer);
	}

	// JPA的EntityManager的merge方法类似于Hibernate的Session的saveOrUpdate方法
	// 1、若传入的是一个临时对象，会创建一个新的对象，把临时对象的属性复制到新的对象中，然后对新的对象执行insert持久化操作，所以新的对象中有id，但之前的临时对象中没有id
	@Test
	public void testMerge1() {
		Customer customer = new Customer();
		customer.setAge(18);
		customer.setBirth(new Date());
		customer.setCreateTime(new Date());
		customer.setEmail("cc@163.com");
		customer.setLastName("CC");

		Customer customer2 = entityManager.merge(customer);
		System.out.println("customer's:" + customer.getId());
		System.out.println("customer2's:" + customer2.getId());
	}

	// 2、游离状态的对象，即传入的对象有OID，
	// 2.1、 若在EntityManager缓存中没有该对象，
	// 2.2、若在数据库中也没有对应的记录，
	// 2.3、JPA会创建一个新的对象，然后把当前的游离状态的对象的属性复制到新创建的对象中
	// 2.4、对新创建的对象执行insert操作
	@Test
	public void testMerge2() {
		Customer customer = new Customer();
		customer.setAge(18);
		customer.setBirth(new Date());
		customer.setCreateTime(new Date());
		customer.setEmail("dd@163.com");
		customer.setLastName("DD");

		// 游离状态的对象
		customer.setId(10);

		Customer customer2 = entityManager.merge(customer);
		System.out.println("customer's:" + customer.getId());
		System.out.println("customer2's:" + customer2.getId());
	}

	// 3、游离状态的对象，即传入的对象有OID，
	// 3.1、 若在EntityManager缓存中没有该对象，
	// 3.2、若在数据库中有对应的记录，
	// 3.3、JPA会查询对应的记录，然后返回该记录对应的对象，再然后会把游离对象的属性复制到查询到的对象中，
	// 3.4、对更新后查询到的的对象执行update操作
	@Test
	public void testMerge3() {
		Customer customer = new Customer();
		customer.setAge(20);
		customer.setBirth(new Date());
		customer.setCreateTime(new Date());
		customer.setEmail("bb@163.com");
		customer.setLastName("BB");

		// 游离状态的对象
		customer.setId(5);

		Customer customer2 = entityManager.merge(customer);

		System.out.println(customer == customer2);// false
	}

	// 4、游离状态的对象，即传入的对象有OID，
	// 4.1、 若在EntityManager缓存中有该对象，
	// 4.2、JPA会把游离对象的属性复制到查询到EntityManager缓存中的对象中
	// 4.3、对EntityManager缓存中的对象执行update操作
	@Test
	public void testMerge4() {
		Customer customer = new Customer();
		customer.setAge(20);
		customer.setBirth(new Date());
		customer.setCreateTime(new Date());
		customer.setEmail("bb@163.com");
		customer.setLastName("BB");

		// 游离状态的对象
		customer.setId(5);

		Customer customer2 = entityManager.find(Customer.class, 5);
		entityManager.merge(customer);

		System.out.println(customer == customer2);// false
	}
	
	//同Hibernate的Session的flush方法。
	@Test
	public void testFlush(){
		Customer customer = entityManager.find(Customer.class, 1);
		
		System.out.println(customer);
		
		customer.setLastName("Amy");
		
		//强制发送更新操作
		entityManager.flush();
	}
	
	@Test
	public void testQuery(){
		Query query = entityManager.createQuery("select lastName from Customer where id=?");
		query.setParameter(1, 1);
		System.out.println(query.getSingleResult());
	}

}
