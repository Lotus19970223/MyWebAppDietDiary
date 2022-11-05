package com.dietdiary.controller;

import java.sql.Date;

import org.springframework.data.repository.CrudRepository;

import com.dietdiary.entity.SampleDiaryEntity;

//日記用のSpring Data リポジトリインターフェイス（サンプル）
//https://spring.pleiades.io/spring-data/jpa/docs/current/reference/html/

//JPAにはRepositoryインターフェースに、命名規則に従ったメソッド名を書くとSQLを自動生成する機能がある
public interface SampleDiaryRepository extends CrudRepository<SampleDiaryEntity, Date> {
//CrudRepositoryから継承した抽象メソッドに加え、下記の抽象メソッドを持たせる
	//日記レコード一覧を取得する際にDate順にソートする
	Iterable<SampleDiaryEntity> findAllByOrderByDateAsc();
}
