package com.dietdiary.controller;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.dietdiary.entity.Customer;
import com.dietdiary.entity.SampleDiaryEntity;
import com.dietdiary.service.SampleService;



@Controller
public class CustomerController {

	// https://spring.pleiades.io/guides/gs/accessing-data-mysql/
	// This means to get the bean called CustomerRepository
	// Which is auto-generated by Spring, we will use it to handle the data
	@Autowired CustomerRepository cr;
	//Repositoryに@Autowiredを付けることでインスタンスが DI コンテナから渡され、new することなくメソッドを呼び出せるようになる
	//https://qiita.com/kuro227/items/a16e22ac12afe7442a3d
	@Autowired SampleDiaryRepository sampleDiaryRepository;
	@Autowired SampleService sampleService;

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

		Iterable<SampleDiaryEntity> sampleDiaryList = sampleDiaryRepository.findAll();
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

		//0～999のどれかで乱数を生成 RandomクラスのnextInt() を使用
		Random rand = new Random();
	    int randomNumber = rand.nextInt(1000);
	    //日記欄に書き込む用に乱数をString型に変換
	    String randomNumberStr = Integer.toString(randomNumber);

	    //DBのデータをエンティティのリストとして取得
	    Iterable<SampleDiaryEntity> sampleDiaryList = sampleDiaryRepository.findAll();
	    //DB更新用（乱数）のサンプルのメソッドを使用してDBへの書き込みを行う
	    for(SampleDiaryEntity sde : sampleDiaryList){
	    	//日記のエンティティとString型に変換した乱数を引数にする
	    	updateSampleDiary(sde,randomNumberStr);
        }
	    //View側へ乱数を文字列にしたオブジェクトを渡す
	    mav.addObject("randomNumberStr", randomNumberStr);
	    // /myWebAppDietDiary/src/main/resources/templates内の
	 	// sampleDBUpdate.htmlファイルを表示する
		mav.setViewName("sampleDBUpdate");
		return mav;
	}

	//DB更新用（乱数）のサンプルのメソッド
	SampleDiaryEntity updateSampleDiary(SampleDiaryEntity sde, String randomNumberStr) {
		//日記エンティティの持つ日付の値が2022-10-01の場合
		if(sde.getDate().toString() == "2022-10-01") {
			//test
			System.out.println(sde.getDate());
			//引数で受け取った乱数（文字列化済）を日記欄にセット
			sde.setDiaryText(randomNumberStr);
			//日記欄を変更したエンティティをセット（戻り値はセット後のエンティティ）
			//戻り値であるセット後のエンティティをそのままメソッドの戻り値とする
			System.out.println(sampleDiaryRepository.save(sde) + "kore is omeate");
			return sampleDiaryRepository.save(sde);
		}
		//test
		System.out.println(sde.getDate());
		//日付の値が2022-10-01でない場合そのまま日記エンティティを返す
		return sde;
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