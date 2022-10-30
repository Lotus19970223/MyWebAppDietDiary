package com.dietdiary.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.dietdiary.controller.SampleDiaryRepository;

@Service //サービスクラス
@Transactional
public class SampleService {
	@Autowired
	SampleDiaryRepository sdrRepository;
//	public SampleDiaryEntity updateDiary(SampleDiaryEntity sampleDiaryEntity) {
//        return sampleDiaryEntity.save(sampleDiaryEntity);
//    }
}
