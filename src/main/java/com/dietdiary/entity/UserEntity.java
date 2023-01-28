package com.dietdiary.entity;

import java.math.BigDecimal;
import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Data;

@Data //getter、setterメソッド等を生成→うまく動作しなかったためEclipseで生成
@Entity //JPAのエンティティであることを示す
@Table(name="users")  //クラス名とテーブル名が異なる場合に宣言
public class UserEntity {

	//バリデーション参考：https://www.tsuchiya.blog/spring-boot-step4/#toc3

	//ユーザーID  PostgreSQL側ではserial型
	//テーブルのプライマリキーに当たるプロパティに@Idのアノテーションを付与
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "users_USERID_seq")
    @SequenceGenerator(name = "users_USERID_seq", sequenceName = "users_USERID_seq", allocationSize = 1)
	private int userId;

    //ユーザー名（入力チェックあり・最大20文字）
	@NotEmpty(message="ユーザー名を入力してください")
	@Size(max = 20, message="ユーザー名は20文字以内で入力してください")
    private String userName;

	//パスワード
	@NotBlank(message="パスワードを入力してください")
    @Pattern(regexp="^[0-9a-zA-Z]+$", message="パスワードは半角英数字で入力してください")
    @Size(min=8, max=20, message="パスワードは半角英数字8～20字で入力してください")
    private String password;

    //目標体重
	@NotBlank(message="目標体重を入力してください")
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