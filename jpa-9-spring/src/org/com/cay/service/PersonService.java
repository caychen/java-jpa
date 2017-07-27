package org.com.cay.service;

import org.com.cay.dao.PersonDao;
import org.com.cay.entity.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PersonService {

	@Autowired
	private PersonDao personDao;
	
	@Transactional
	public void saves(Person person1, Person person2){
		personDao.save(person1);
		
		personDao.save(person2);
	}
}
