package org.com.cay.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "JPA_DEPARTMENT")
public class Department {

	private Integer id;
	private String deptName;
	private Manager mgr;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name="DEPT_NAME")
	public String getDeptName() {
		return deptName;
	}

	public void setDeptName(String deptName) {
		this.deptName = deptName;
	}

	//ʹ��@OneToOne��ӳ��1-1������ϵ
	//����Ҫ�ڵ�ǰ���ݱ��������������Ҫʹ��
	@OneToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="MGR_ID",unique=true)
	public Manager getMgr() {
		return mgr;
	}

	public void setMgr(Manager mgr) {
		this.mgr = mgr;
	}

	@Override
	public String toString() {
		return "Department [id=" + id + ", deptName=" + deptName + ", mgr=" + mgr + "]";
	}

}
