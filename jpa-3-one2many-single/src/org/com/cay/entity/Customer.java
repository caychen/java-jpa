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
@Table(name = "JPA_CUSTOMER") // 如果name属性与实体类名一致，可以不写name属性，忽略大小写
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
	@Column(name = "ID") // 如果column的name属性跟数据库的属性名一样，可以不需要写，忽略大小写
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

	// @Temporal来表示Date类型的精度，DATE或TIME或DATESTAMP(默认)
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

	// 使用@OneToMany来映射1-n的关联关系
	// 使用@JoinColumn来映射外键列的名称
	// 通过修改@OneToMany的fetch来修改默认的加载策略
	// 通过修改@OneToMany的cascade来修改级联操作
	@OneToMany(fetch=FetchType.EAGER,cascade=CascadeType.REMOVE)
	@JoinColumn(name = "CUSTOMER_ID")
	public Set<Order> getOrders() {
		return orders;
	}

	public void setOrders(Set<Order> orders) {
		this.orders = orders;
	}

	// 工具方法，不需要映射为数据表的一列
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
