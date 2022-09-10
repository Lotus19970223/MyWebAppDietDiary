package com.dietdiary.controller;

import java.sql.Date;

import org.springframework.data.repository.CrudRepository;

import com.dietdiary.entity.SampleDiaryEntity;

//
public interface SampleDiaryRepository extends CrudRepository<SampleDiaryEntity, Date> {
}
