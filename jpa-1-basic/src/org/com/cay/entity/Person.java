package org.com.cay.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table
public class Person {

	private int id;
	private String name;
	private Gender gender = Gender.MALE;
	private String info;
	private byte[] file;
	private String path;

	@Transient//�������ݿ��ֶν���ӳ��
	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	@Lob // �ֽڻ��߶���������
	@Basic(fetch=FetchType.LAZY)//��ʱ����
	public byte[] getFile() {
		return file;
	}

	public void setFile(byte[] file) {
		this.file = file;
	}

	@Lob // ���ı�
	public String getInfo() {
		return info;
	}

	public void setInfo(String info) {
		this.info = info;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	// @Enumerated(EnumType.ORDINAL) // Ĭ�Ͻ�ö�����͵���ֵд�����ݿ�,0,1,...
	@Enumerated(EnumType.STRING) // ��ö������������ַ���д�����ݿ�,����Ϊ��MALE,FEMALE
	@Column(length = 6, nullable = false)
	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender gender) {
		this.gender = gender;
	}

	public Person(String name, Gender gender) {
		super();
		this.name = name;
		this.gender = gender;
	}

	public Person() {
		super();
	}

}
