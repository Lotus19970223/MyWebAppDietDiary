package com.dietdiary.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class CustomerController {

	@Autowired CustomerRepository cr;

	@RequestMapping("/hello")
	public ModelAndView index() {
		ModelAndView mav = new ModelAndView();

		Iterable<Customer> customerList = cr.findAll();
		mav.addObject("customerList", customerList);
		mav.setViewName("customerList");
        return mav;
	}

	@RequestMapping("/")
	public String sample2() {
		//コンソールに表示テスト
		System.out.println("test!");
		// returnで返した「Stringの文字列のファイル名」に対応した
		// /myWebAppDietDiary/src/main/resources/templates内の
		// ファイルを表示する
		return "index";
	}
	/*
	@RequestMapping("/myWebAppDietDiary/")
	public String sample3() {
		return "SampleRequestMappingContextRoot1";
	}
	@RequestMapping("/myWebAppDietDiary")
	public String sample4() {
		return "SampleRequestMappingContextRoot2";
	}*/
}