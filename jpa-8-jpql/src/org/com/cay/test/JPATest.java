package org.com.cay.test;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.criteria.Order;

import org.com.cay.entity.Customer;
import org.hibernate.jpa.QueryHints;
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
	
	@Test
	public void testJPQL(){
		String jpql = "from Customer c where c.age > ?";
		Query query = entityManager.createQuery(jpql);
		query.setParameter(1, 20);
		@SuppressWarnings("unchecked")
		List<Customer> customers = query.getResultList();
		System.out.println(customers.size());
	}
	
	//Ĭ������£���ѯ�������ԣ��򽫷���Object[]���͵Ľ��������Object[]���͵�List
	@Test
	public void testPartlyProperties1(){
		String jpql = "select c.lastName, c.age from Customer c where c.id > ?";
		List<Object[]> result = entityManager.createQuery(jpql).setParameter(1, 2).getResultList();
		for (Object[] objects : result) {
			System.out.println(objects[0] + "," + objects[1]);
		}
	}

	//Ҳ������ʵ�����д�����Ӧ�Ĺ�������Ȼ����JPQL��������ö�Ӧ�Ĺ���������ʵ����Ķ���
	@Test
	public void testPartlyProperties2(){
		String jpql = "select new Customer(c.lastName, c.age) from Customer c where c.id > ?";
		List<Customer> result = entityManager.createQuery(jpql).setParameter(1, 2).getResultList();
		for (Customer customer : result) {
			System.out.println(customer);
		}
	}
	
	//��������ʵ����Ǯʹ��@NamedQuery��ǵĲ�ѯ���
	@Test
	//ִ��������
	public void testNamedQuery(){
		Query query = entityManager.createNamedQuery("testNamedQuery");//.setParameter(1, 2);
		Customer customer = (Customer) query.getSingleResult();
		System.out.println(customer);
	}
	
	@Test
	public void testNativeQuery(){
		Query query = entityManager.createNativeQuery("select age from jpa_customer where id = ?");
		query.setParameter(1, 3);
		Object result = query.getSingleResult();
		System.out.println(result);
	}
	
	//ʹ��hibernate�Ĳ�ѯ���棬ǰ������jpa�����ļ������ò�ѯ����
	@Test
	public void testQueryCache(){
		String jpql = "select new Customer(c.lastName, c.age) from Customer c where c.id > ?";
		Query query = entityManager.createQuery(jpql).setParameter(1, 2);
		query.setHint(QueryHints.HINT_CACHEABLE, true);
		List<Customer> result = query.getResultList();
		
		System.out.println(result.size());
		
		System.out.println("------------------");
		query = entityManager.createQuery(jpql).setParameter(1, 2);
		query.setHint(QueryHints.HINT_CACHEABLE, true);
		result = query.getResultList();
		
		System.out.println(result.size());
	}
	
	@Test
	public void testOrderBy(){
		String jpql = "from Customer c where c.age > ? order by c.age";
		Query query = entityManager.createQuery(jpql).setParameter(1, 19);
		query.setHint(QueryHints.HINT_CACHEABLE, true);
		List<Customer> result = query.getResultList();
		System.out.println(result.size());
		for (Customer customer : result) {
			System.out.println(customer.getAge());
		}
	}
	
	@Test
	public void testGroupBy(){
		String jpql = "select o.customer from Order o group by o.customer having count(o.id) >= 2";
		List<Customer> customers = entityManager.createQuery(jpql).getResultList();
		System.out.println(customers);
	}
	
	//ʹ��fetch����ͬʱ��Ҫ��ѯ�Ķ���Ĺ���������в�ѯ����ʼ��
	@Test
	public void testLeftOuterJoinFetch(){
		String jpql = "from Customer c left outer join fetch c.orders where c.id = ?";
		Customer customer = (Customer) entityManager.createQuery(jpql).setParameter(1, 2).getSingleResult();
		System.out.println(customer.getLastName());
		System.out.println(customer.getOrders().size());
	}
	
	@Test
	public void testSubQuery(){
		String jpql = "select o from Order o where o.customer = (select c from Customer c where c.lastName = ?)";
		Query query = entityManager.createQuery(jpql).setParameter(1, "FF");
		List<Order> orders = query.getResultList();
		System.out.println(orders.size());
	}
	
	//ʹ��jpql���ڽ�����
	@Test
	public void testJpqlFunction(){
		String jpql = "select upper(c.email) from Customer c";
		List<String> emails = entityManager.createQuery(jpql).getResultList();
		System.out.println(emails);
	}
	
	@Test
	public void testExecuteUpdate(){
		String jpql = "update Customer c set c.lastName = ? where c.id = ?";
		Query query = entityManager.createQuery(jpql).setParameter(1, "CC").setParameter(2, 3);
		query.executeUpdate();
	}
}
