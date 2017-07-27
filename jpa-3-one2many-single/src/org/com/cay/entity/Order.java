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

	// ӳ�䵥��n-1�Ĺ�����ϵ
//	@JoinColumn(name = "CUSTOMER_ID")//��ӳ�����
//	@ManyToOne(fetch=FetchType.LAZY)//��ӳ����һ������ϵ������ʹ��fetch�������޸�Ĭ�ϵĹ������Եļ��ز���
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
