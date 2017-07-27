package org.com.cay.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "JPA_ORDER")
public class Order {

	private Integer id;
	private String orderName;
	//private Customer customer;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "ORDER_NAME")
	public String getOrderName() {
		return orderName;
	}

	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}

	@Override
	public String toString() {
		return "Order [id=" + id + ", orderName=" + orderName + "]";
	}

	// 映射单向n-1的关联关系
//	@JoinColumn(name = "CUSTOMER_ID")//来映射外键
//	@ManyToOne(fetch=FetchType.LAZY)//来映射多对一关联关系，可以使用fetch属性来修改默认的关联属性的加载策略
//	public Customer getCustomer() {
//		return customer;
//	}
//
//	public void setCustomer(Customer customer) {
//		this.customer = customer;
//	}
//
//	@Override
//	public String toString() {
//		return "Order [id=" + id + ", orderName=" + orderName + ", customer=" + customer + "]";
//	}

	
}
