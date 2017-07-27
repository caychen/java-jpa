package org.com.cay.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.com.cay.entity.Person;
import org.springframework.stereotype.Repository;

@Repository
public class PersonDao {

	//��λ�ȡ���͵�ǰ���������EntityManager����
	//ͨ��@PersistenceContextע������ǳ�Ա����
	@PersistenceContext
	private EntityManager entityManager;
	
	public void save(Person person){
		entityManager.persist(person);
	}
}
