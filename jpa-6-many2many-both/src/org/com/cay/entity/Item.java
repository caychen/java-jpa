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

	//ʹ��@ManyToManyע����ӳ���Զ������ϵ
	//ʹ��@JoinTable��ӳ���м��
	//	1. nameΪ�м�������
	//	2. joinColumns��ʾ��ǰ�����м���е��ֶμ��ϣ�
	//		2.1. name����Ϊ�ñ����м����������
	//		2.2. referencedColumnNameָ������й�����ǰ�������
	//	3. inverseJoinColumns��ʾ˫�������ϵ����һ�����м�����Ϣ
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
