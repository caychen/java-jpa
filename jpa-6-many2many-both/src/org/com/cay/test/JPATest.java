package org.com.cay.test;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.com.cay.entity.Category;
import org.com.cay.entity.Item;
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
	public void testManyToManyPersist(){
		Item i1 = new Item();
		i1.setItemName("I-1");
		
		Item i2 = new Item();
		i2.setItemName("I-2");
		
		Category c1 = new Category();
		c1.setCategoryName("C-1");
		
		Category c2 = new Category();
		c2.setCategoryName("C-2");
		
		i1.getCategories().add(c1);
		i1.getCategories().add(c2);
		
		i2.getCategories().add(c1);
		i2.getCategories().add(c2);
		
		c1.getItems().add(i1);
		c1.getItems().add(i2);
		
		c2.getItems().add(i1);
		c2.getItems().add(i2);
		
		entityManager.persist(i1);
		entityManager.persist(i2);
		entityManager.persist(c1);
		entityManager.persist(c2);
	}
	
	//对于关联的集合对象，默认使用懒加载的策略
	//通过维护关联关系的一方来获取不维护的一方，或者通过不维护关联关系的一方来获取维护的一方，sql语句相同
	@Test
	public void testManyToManyFind(){
		Item item = entityManager.find(Item.class, 1);
		
		System.out.println(item.getItemName());
		
		System.out.println(item.getCategories().size());
	}
	
	@Test
	public void testManyToManyFind2(){
		Category category = entityManager.find(Category.class, 1);
		
		System.out.println(category.getCategoryName());
		
		System.out.println(category.getItems().size());
	}

}
