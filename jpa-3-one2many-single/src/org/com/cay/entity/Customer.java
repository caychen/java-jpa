package org.com.cay.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
@Table(name = "JPA_CUSTOMER") // ���name������ʵ������һ�£����Բ�дname���ԣ����Դ�Сд
public class Customer {

	private Integer id;
	private String lastName;
	private String email;
	private int age;
	private Date createTime;
	private Date birth;

	private Set<Order> orders = new HashSet<>();

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID") // ���column��name���Ը����ݿ��������һ�������Բ���Ҫд�����Դ�Сд
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "LAST_NAME")
	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	// @Temporal����ʾDate���͵ľ��ȣ�DATE��TIME��DATESTAMP(Ĭ��)
	@Temporal(TemporalType.TIMESTAMP)
	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	@Temporal(TemporalType.DATE)
	public Date getBirth() {
		return birth;
	}

	public void setBirth(Date birth) {
		this.birth = birth;
	}

	// ʹ��@OneToMany��ӳ��1-n�Ĺ�����ϵ
	// ʹ��@JoinColumn��ӳ������е�����
	// ͨ���޸�@OneToMany��fetch���޸�Ĭ�ϵļ��ز���
	// ͨ���޸�@OneToMany��cascade���޸ļ�������
	@OneToMany(fetch=FetchType.EAGER,cascade=CascadeType.REMOVE)
	@JoinColumn(name = "CUSTOMER_ID")
	public Set<Order> getOrders() {
		return orders;
	}

	public void setOrders(Set<Order> orders) {
		this.orders = orders;
	}

	// ���߷���������Ҫӳ��Ϊ���ݱ��һ��
	@Transient
	public String getInfo() {
		return "lastName: " + lastName + ", email: " + email;
	}

	@Override
	public String toString() {
		return "Customer [id=" + id + ", lastName=" + lastName + ", email=" + email + ", age=" + age + ", createTime="
				+ createTime + ", birth=" + birth + ", orders=" + orders + "]";
	}

}
