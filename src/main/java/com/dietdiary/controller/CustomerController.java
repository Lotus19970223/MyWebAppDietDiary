package com.dietdiary.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.dietdiary.entity.Customer;



@Controller
public class CustomerController {

	// https://spring.pleiades.io/guides/gs/accessing-data-mysql/
	// This means to get the bean called CustomerRepository
	// Which is auto-generated by Spring, we will use it to handle the data
	@Autowired CustomerRepository cr;

	@RequestMapping("/hello")
	//ModelAndViewオブジェクトを返す
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
		System.out.println("test2!");
		System.out.println("${JDBC_DATABASE_URL}");
		// returnで返した「Stringの文字列のファイル名」に対応した
		// /myWebAppDietDiary/src/main/resources/templates内の
		// ファイルを表示する
		return "index";
	}
	@RequestMapping("/sample")
	public String sample3() {
		//コンソールに表示テスト
		System.out.println("test3!");
		// returnで返した「Stringの文字列のファイル名」に対応した
		// /myWebAppDietDiary/src/main/resources/templates内の
		// ファイルを表示する
		return "BootStrapSample";
	}
	@RequestMapping("/sampleDataList")
	public String sample4() {
		//コンソールに表示テスト
		System.out.println("test4!");
		// returnで返した「Stringの文字列のファイル名」に対応した
		// /myWebAppDietDiary/src/main/resources/templates内の
		// ファイルを表示する
		return "SampleDiariesDataList";
	}
/*	  @PostMapping(path="/add") // Map ONLY POST Requests
	  public @ResponseBody String addNewUser (@RequestParam String name
	      , @RequestParam String email) {
	    // @ResponseBody means the returned String is the response, not a view name
	    // @RequestParam means it is a parameter from the GET or POST request

	    Customer c = new Customer();
	    c.setId(name);
	    c.setEmail(email);
	    CustomerRepository.save(c);
	    return "Saved";
	  }
*/
}