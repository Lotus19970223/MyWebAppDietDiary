//Entityクラス
package com.dietdiary.controller;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data //getter、setterメソッド等を生成→うまく動作しなかったためEclipseで生成
@Entity //JPAのエンティティであることを示す
@Getter
@Setter
public class Customer {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private String firstName;
    private String lastName;

    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	protected Customer() {}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	//getter, setterはEclipseで自動生成
}