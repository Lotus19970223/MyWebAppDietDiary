package com.dietdiary.entity;

import java.math.BigDecimal;
import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.Data;

@Data //getter、setterメソッド等を生成→うまく動作しなかったためEclipseで生成
@Entity //JPAのエンティティであることを示す
@Table(name="users")  //クラス名とテーブル名が異なる場合に宣言
public class UserEntity {
	//ユーザーID  PostgreSQL側ではserial型
	//テーブルのプライマリキーに当たるプロパティに@Idのアノテーションを付与
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_USERID_seq")
    @SequenceGenerator(name = "users_USERID_seq", sequenceName = "users_USERID_seq", allocationSize = 1)
	private int userId;
    //ユーザー名
    private String userName;
    //パスワード
    private String password;
    //目標体重
    private BigDecimal weightGoal;
    //ユーザー作成日
    private Date userCreatedWhen;

    //getterとsetter
	public int getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public BigDecimal getWeightGoal() {
		return weightGoal;
	}
	public void setWeightGoal(BigDecimal weightGoal) {
		this.weightGoal = weightGoal;
	}
	public Date getUserCreatedWhen() {
		return userCreatedWhen;
	}
	public void setUserCreatedWhen(Date userCreatedWhen) {
		this.userCreatedWhen = userCreatedWhen;
	}
}