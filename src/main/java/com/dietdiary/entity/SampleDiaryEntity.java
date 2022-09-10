package com.dietdiary.entity;

import java.math.BigDecimal;
import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data //getter、setterメソッド等を生成→うまく動作しなかったためEclipseで生成
@Entity //JPAのエンティティであることを示す
@Table(name="sample_diaries")  //クラス名とテーブル名が異なる場合に宣言
public class SampleDiaryEntity {
	//日付
	//テーブルのプライマリキーに当たるプロパティに@Idのアノテーションを付与
	@Id
	private Date date;
    //体重
    private BigDecimal weight;
    //目標体重
    private BigDecimal weightForGoal;
    //日記欄
    private String diaryText;

    //以下getterとsetter
    public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public BigDecimal getWeight() {
		return weight;
	}
	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}
	public BigDecimal getWeightForGoal() {
		return weightForGoal;
	}
	public void setWeightForGoal(BigDecimal weightForGoal) {
		this.weightForGoal = weightForGoal;
	}
	public String getDiaryText() {
		return diaryText;
	}
	public void setDiaryText(String diaryText) {
		this.diaryText = diaryText;
	}

}
