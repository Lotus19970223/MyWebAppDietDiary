package com.dietdiary.controller;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.dietdiary.entity.Customer;
import com.dietdiary.entity.SampleDiaryEntity;



@Controller
public class CustomerController {

	// https://spring.pleiades.io/guides/gs/accessing-data-mysql/
	// This means to get the bean called CustomerRepository
	// Which is auto-generated by Spring, we will use it to handle the data
	@Autowired CustomerRepository cr;
	@Autowired SampleDiaryRepository sdr;

	@RequestMapping("/hello")
	//ModelAndViewオブジェクトを返す
	public ModelAndView index() {
		ModelAndView mav = new ModelAndView();

		Iterable<Customer> customerList = cr.findAll();
		mav.addObject("customerList", customerList);
		mav.setViewName("customerList");
        return mav;
	}
	@RequestMapping("/sampleDBRead")
	//ModelAndViewオブジェクトを返す
	public ModelAndView index2() {
		ModelAndView mav = new ModelAndView();

		//日本（東京）の現在日時を取得
		ZonedDateTime nowDateTimeJP = ZonedDateTime.now(ZoneId.of("Asia/Tokyo"));
		//現在日時から日付・時刻それぞれのオブジェクトを作成
		LocalDate localDateJP = nowDateTimeJP.toLocalDate();
		LocalTime localTimeJP = nowDateTimeJP.toLocalTime();
		//上記で取得した日付・時刻を、変数localDateJP、localTimeJPのオブジェクトとして登録
		mav.addObject("localDateJP", localDateJP);
		mav.addObject("localTimeJP", localTimeJP);

		Iterable<SampleDiaryEntity> sampleDiaryList = sdr.findAll();
//		https://pointsandlines.jp/server-side/java/model-and-view
//		addObject()メソッドではView側へ渡すオブジェクトのデータを
//		第一引数にテンプレートから参照する変数名、
//		第二引数にオブジェクト名として格納している
		mav.addObject("sampleDiaryList", sampleDiaryList);
		// 「setViewNameの引数のファイル名」に対応した
		// /myWebAppDietDiary/src/main/resources/templates内の
		// ファイルを表示する
		mav.setViewName("sampleDiaryList");
        return mav;
	}

	@RequestMapping("/sampleDBUpdate")
	//ModelAndViewオブジェクトを返す
	public ModelAndView index3() {
		ModelAndView mav = new ModelAndView();
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