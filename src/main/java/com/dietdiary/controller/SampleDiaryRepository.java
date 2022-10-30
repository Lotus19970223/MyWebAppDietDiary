package com.dietdiary.controller;

import java.sql.Date;

import org.springframework.data.repository.CrudRepository;

import com.dietdiary.entity.SampleDiaryEntity;

//日記用のSpring Data リポジトリインターフェイス（サンプル）
//https://spring.pleiades.io/spring-data/jpa/docs/current/reference/html/
public interface SampleDiaryRepository extends CrudRepository<SampleDiaryEntity, Date> {
//CrudRepositoryから継承した抽象メソッドに加え、下記の抽象メソッドを持たせる
//リポジトリの
}
