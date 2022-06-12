package com.dietdiary.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SampleController {
	@RequestMapping("/test")
	public String test() {
		System.out.println("testtesttest!!");
		return "test";
	}
}
