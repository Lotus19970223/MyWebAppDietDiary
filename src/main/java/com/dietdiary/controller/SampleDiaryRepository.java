package com.dietdiary.controller;

import java.sql.Date;

import org.springframework.data.repository.CrudRepository;

import com.dietdiary.entity.Sample_diaries;

//
public interface SampleDiaryRepository extends CrudRepository<Sample_diaries, Date> {
}
