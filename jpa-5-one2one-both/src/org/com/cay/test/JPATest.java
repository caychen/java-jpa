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
	
	//�ڽ���˫��1-n������ϵʱ������ʹ��n��һ����ά��������ϵ����1��һ�˲�ά��������ϵ����������Ч�ļ���sql���
	//ע�⣺����1��һ�˵�@OneToMany��ʹ��mapperBy���ԣ���@OneToMany�˲�����ʹ��@JoinColumn����
	@Test
	public void testOneToOnePersist(){
		Manager mgr = new Manager();
		mgr.setMgrName("M-AA");
		
		Department dept = new Department();
		dept.setDeptName("D-AA");
		
		//���ù�����ϵ
		mgr.setDept(dept);
		dept.setMgr(mgr);
		
		//ִ�б������
		//�Ƽ����ȱ��治ά��������ϵ��һ������û�������һ��
		entityManager.persist(mgr);
		entityManager.persist(dept);
	}
	
	//Ĭ������£�
	//1������ȡά��������ϵ��һ�������ͨ���������ӵķ�ʽ��ȡ��������Ķ���
	// ͨ���޸�@OneToOne��fetch�������޸�Ĭ�ϵļ��ز���
	@Test
	public void testOneToOneFind(){
		Department dept = entityManager.find(Department.class, 1);
		System.out.println(dept.getDeptName());
		
		System.out.println(dept.getMgr().getClass().getName());
	}
	
	//Ĭ������£�
	//1������ȡ��ά��������ϵ��һ������Ҳ��ͨ���������ӵķ�ʽ��ȡ��������Ķ���
	//��ʹ�޸Ĳ�ά��������ϵ��һ����fetch���ԣ�Ҳ�޷�����sql��䣬���Բ������ڲ�ά��������ϵ��һ������fetch����
	@Test
	public void testOneToOneFind2(){
		Manager mgr = entityManager.find(Manager.class, 1);
		
		System.out.println(mgr.getMgrName());
		
		System.out.println(mgr.getDept().getClass().getName());
	}
	
//	//Ĭ������£���ɾ��1 ��һ�ˣ�����Ȱѹ�����n��һ�˵������Ϊ�գ�Ȼ����ɾ��1��һ��  
//	@Test
//	public void testOneToManyRemove(){
////		Order order = entityManager.find(Order.class, 1);
////		entityManager.remove(order);
//		
//		// ͨ���޸�@OneToMany��cascade���޸ļ�������
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
