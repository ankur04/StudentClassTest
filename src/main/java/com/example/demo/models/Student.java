package com.example.demo.models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Cascade;
import org.hibernate.annotations.ManyToAny;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerator;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import lombok.Data;

@Entity
//@JsonIgnoreProperties({"hibernateLazyInitializer","handler","classes"})
//@JsonIdentityInfo(generator = ObjectIdGenerators.StringIdGenerator.class,
					//property="studentid")
public class Student {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer studentid;
	private String name;
	private Integer age;
	@ManyToMany(fetch = FetchType.LAZY,
            cascade = CascadeType.ALL)
	@JoinTable(
			name="register",
			joinColumns={@JoinColumn(name="studentid")},
			inverseJoinColumns = {@JoinColumn(name="classid")}
			)
//	@JsonManagedReference
	private List<Class> classes;
	
	public Student(Integer studentid, String name, Integer age) {
		super();
		this.studentid = studentid;
		this.name = name;
		this.age = age;
	}

	public Student() {
		super();
	}

	public Integer getStudentid() {
		return studentid;
	}

	public void setStudentid(Integer studentid) {
		this.studentid = studentid;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}
	
	

	public List<Class> getClasses() {
		return classes;
	}

	public void setClasses(List<Class> classes) {
		this.classes = classes;
	}

	@Override
	public String toString() {
		return "Student [studentid=" + studentid + ", name=" + name + ", age=" + age + "]";
	}
	
	

}
