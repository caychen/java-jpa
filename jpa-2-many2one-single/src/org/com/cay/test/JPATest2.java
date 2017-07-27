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

	// EntityManager��find��������Hibernate��Session��get����
	@Test
	public void testFind() {
		Customer customer = entityManager.find(Customer.class, 1);
		System.out.println("--------------");
		System.out.println(customer);
	}

	// EntityManager��getReference��������Hibernate��Session��load����
	@Test
	public void testGetReference() {
		Customer customer = entityManager.getReference(Customer.class, 1);
		System.out.println("--------------");
		System.out.println(customer);
	}

	// ������Hibernate��Session��save����,ʹ����������״̬��Ϊ�־�״̬
	// ע�⣬��hibernate��save�����Ĳ�֮ͬ������������id������ִ��insert�����������׳��쳣
	@Test
	public void testPersist() {
		Customer customer = new Customer();
		customer.setLastName("BB");
		customer.setEmail("bb@qq.com");
		customer.setAge(22);
		customer.setCreateTime(new Date());
		customer.setBirth(new Date());

		// ������������id�Ĳ����������쳣
		// customer.setId(222);

		entityManager.persist(customer);
		System.out.println(customer.getId());
	}

	// EntityManager��remove��������Hibernate��Session��delete����,�Ѷ����Ӧ�ļ�¼�����ݿ����Ƴ�
	// ��ע�⣺�÷���ֻ���Ƴ��־û����󣬶�hibernate��delete����ʵ���ϻ������Ƴ��������
	@Test
	public void testRemove() {
		// ���»��׳��쳣����Ϊjpa��remove�����Ƴ�����״̬�Ķ���
		// Customer customer = new Customer();
		// customer.setId(4);
		// entityManager.remove(customer);

		Customer customer = entityManager.find(Customer.class, 4);
		entityManager.remove(customer);
	}

	// JPA��EntityManager��merge����������Hibernate��Session��saveOrUpdate����
	// 1�����������һ����ʱ���󣬻ᴴ��һ���µĶ��󣬰���ʱ��������Ը��Ƶ��µĶ����У�Ȼ����µĶ���ִ��insert�־û������������µĶ�������id����֮ǰ����ʱ������û��id
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

	// 2������״̬�Ķ��󣬼�����Ķ�����OID��
	// 2.1�� ����EntityManager������û�иö���
	// 2.2���������ݿ���Ҳû�ж�Ӧ�ļ�¼��
	// 2.3��JPA�ᴴ��һ���µĶ���Ȼ��ѵ�ǰ������״̬�Ķ�������Ը��Ƶ��´����Ķ�����
	// 2.4�����´����Ķ���ִ��insert����
	@Test
	public void testMerge2() {
		Customer customer = new Customer();
		customer.setAge(18);
		customer.setBirth(new Date());
		customer.setCreateTime(new Date());
		customer.setEmail("dd@163.com");
		customer.setLastName("DD");

		// ����״̬�Ķ���
		customer.setId(10);

		Customer customer2 = entityManager.merge(customer);
		System.out.println("customer's:" + customer.getId());
		System.out.println("customer2's:" + customer2.getId());
	}

	// 3������״̬�Ķ��󣬼�����Ķ�����OID��
	// 3.1�� ����EntityManager������û�иö���
	// 3.2���������ݿ����ж�Ӧ�ļ�¼��
	// 3.3��JPA���ѯ��Ӧ�ļ�¼��Ȼ�󷵻ظü�¼��Ӧ�Ķ�����Ȼ���������������Ը��Ƶ���ѯ���Ķ����У�
	// 3.4���Ը��º��ѯ���ĵĶ���ִ��update����
	@Test
	public void testMerge3() {
		Customer customer = new Customer();
		customer.setAge(20);
		customer.setBirth(new Date());
		customer.setCreateTime(new Date());
		customer.setEmail("bb@163.com");
		customer.setLastName("BB");

		// ����״̬�Ķ���
		customer.setId(5);

		Customer customer2 = entityManager.merge(customer);

		System.out.println(customer == customer2);// false
	}

	// 4������״̬�Ķ��󣬼�����Ķ�����OID��
	// 4.1�� ����EntityManager�������иö���
	// 4.2��JPA��������������Ը��Ƶ���ѯ��EntityManager�����еĶ�����
	// 4.3����EntityManager�����еĶ���ִ��update����
	@Test
	public void testMerge4() {
		Customer customer = new Customer();
		customer.setAge(20);
		customer.setBirth(new Date());
		customer.setCreateTime(new Date());
		customer.setEmail("bb@163.com");
		customer.setLastName("BB");

		// ����״̬�Ķ���
		customer.setId(5);

		Customer customer2 = entityManager.find(Customer.class, 5);
		entityManager.merge(customer);

		System.out.println(customer == customer2);// false
	}
	
	//ͬHibernate��Session��flush������
	@Test
	public void testFlush(){
		Customer customer = entityManager.find(Customer.class, 1);
		
		System.out.println(customer);
		
		customer.setLastName("Amy");
		
		//ǿ�Ʒ��͸��²���
		entityManager.flush();
	}
	
	@Test
	public void testQuery(){
		Query query = entityManager.createQuery("select lastName from Customer where id=?");
		query.setParameter(1, 1);
		System.out.println(query.getSingleResult());
	}

}
