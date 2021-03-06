package org.com.cay.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.com.cay.entity.Person;
import org.springframework.stereotype.Repository;

@Repository
public class PersonDao {

	//如何获取到和当前事务关联的EntityManager对象？
	//通过@PersistenceContext注解来标记成员变量
	@PersistenceContext
	private EntityManager entityManager;
	
	public void save(Person person){
		entityManager.persist(person);
	}
}
