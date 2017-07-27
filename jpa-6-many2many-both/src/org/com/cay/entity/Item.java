package org.com.cay.entity;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

@Entity
@Table(name = "JPA_ITEM")
public class Item {

	private Integer id;
	private String itemName;
	private Set<Category> categories = new HashSet<>();

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "ITEM_NAME")
	public String getItemName() {
		return itemName;
	}

	public void setItemName(String itemName) {
		this.itemName = itemName;
	}

	//使用@ManyToMany注解来映射多对多关联关系
	//使用@JoinTable来映射中间表
	//	1. name为中间表的名字
	//	2. joinColumns表示当前表在中间表中的字段集合，
	//		2.1. name属性为该表在中间表的外键名，
	//		2.2. referencedColumnName指定外键列关联当前表的列名
	//	3. inverseJoinColumns表示双向关联关系的另一方在中间表的信息
	@JoinTable(name = "ITEM_CATEGORY", 
			joinColumns = {@JoinColumn(name = "ITEM_ID", referencedColumnName = "ID") }, 
			inverseJoinColumns = {@JoinColumn(name = "CATEGORY_ID", referencedColumnName = "ID") })
	@ManyToMany
	public Set<Category> getCategories() {
		return categories;
	}

	public void setCategories(Set<Category> categories) {
		this.categories = categories;
	}

}
