package com.dietdiary.controller;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.dietdiary.entity.Customer;
import com.dietdiary.entity.SampleDiaryEntity;
import com.dietdiary.entity.UserEntity;
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
	@Autowired UserRepository userRepository;
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

	@RequestMapping("/testLogout")
	//ModelAndViewオブジェクトを返す
	public ModelAndView indexLogout() {
		ModelAndView mav = new ModelAndView();
		//ログアウトボタン付きインデックスページ
		mav.setViewName("index");
        return mav;
	}

	//ユーザー作成ページ
	@RequestMapping("/createUser")
	//ModelAndViewオブジェクトを返す
	public ModelAndView createUser() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("createUser");
        return mav;
	}

	//ユーザー作成ページで作成ボタン押下時に呼ばれるメソッド
	@RequestMapping("/saveCreatedUser")
	//参考：https://www.tsuchiya.blog/spring-boot-step4/#toc3
    // @Validアノテーションを付けた引数は入力チェックが行われる
    // 結果は直後の引数BindingResultに格納される。
    // BindingResultは@Validのすぐあとになくてはならない
	public String saveCreatedUser(Model mode, @ModelAttribute @Valid UserEntity sdeFromForm, BindingResult result) {
			System.out.println(sdeFromForm + "←が表示されていればsdeはnullではない");
			//getDate()含め、getDiaryText()以外は引数のsdeFromFormではnull
			//引数のsdeFromFormでは、保存ボタン押下時に送信した値がgetDiaryText()で取得可
			System.out.println(sdeFromForm.getUserName() + "←が表示されていればsde.getUserName()はnullではない");
			System.out.println(sdeFromForm.getPassword() + "←が表示されていればsde.getPassword()はnullではない");
			System.out.println(sdeFromForm.getWeightGoal() + "←が表示されていればsde.getWeightGoalはnullではない");

			//DBにinsertする用のエンティティを用意
			UserEntity userEntity = new UserEntity();

			//作成ページで入力された値をエンティティにセット
			userEntity.setUserName(sdeFromForm.getUserName());
			userEntity.setPassword(sdeFromForm.getPassword());
			userEntity.setWeightGoal(sdeFromForm.getWeightGoal());

			/*日本（東京）の現在日時を取得
			ZonedDateTime nowDateTimeJP = ZonedDateTime.now(ZoneId.of("Asia/Tokyo"));
			//現在日時から日付のオブジェクトを作成
			LocalDate localDateJP = nowDateTimeJP.toLocalDate();
			java.sql.Date sqlDate = java.sql.Date.valueOf(localDateJP.toString());

			//ユーザー作成日をエンティティにセット
			userEntity.setUserCreatedWhen(sqlDate);*/

			//エンティティをDBに登録
    		userRepository.saveByParameters(userEntity.getUserName(), userEntity.getPassword(), userEntity.getWeightGoal());

		    //ユーザー一覧ページに再遷移（登録ボタン押下後に表示）
		    return "redirect:/userList";
		}

	@RequestMapping("/userList")
	public ModelAndView displayUserList() {
		//月を指定せずに全データを取得する
		ModelAndView mav = new ModelAndView();

		//参考：https://qiita.com/parapore/items/4acffd670fc913e05d85
		//JPAにはRepositoryインターフェースに、命名規則に従ったメソッド名を書くとSQLを自動生成する機能がある
		//日記レコード一覧を取得する際にDate順にソートする
		Iterable<UserEntity> userList = userRepository.findAllByOrderByUserId();
//		https://pointsandlines.jp/server-side/java/model-and-view
//		addObject()メソッドではView側へ渡すオブジェクトのデータを
//		第一引数にテンプレートから参照する変数名、
//		第二引数にオブジェクト名として格納している
		mav.addObject("userList", userList);
		// 「setViewNameの引数のファイル名」に対応した
		// /myWebAppDietDiary/src/main/resources/templates内の
		// ファイルを表示する
		mav.setViewName("userList");
        return mav;
	}


	@RequestMapping("/MyPage")
	public ModelAndView showMyPage() {
		ModelAndView mav = new ModelAndView();
		//日本（東京）の現在日時を取得
				ZonedDateTime nowDateTimeJP = ZonedDateTime.now(ZoneId.of("Asia/Tokyo"));
				//現在日時から日付・時刻それぞれのオブジェクトを作成
				LocalDate localDateJP = nowDateTimeJP.toLocalDate();
				LocalTime localTimeJP = nowDateTimeJP.toLocalTime();
				//上記で取得した日付・時刻を、変数localDateJP、localTimeJPのオブジェクトとして登録
				mav.addObject("localDateJP", localDateJP);
				mav.addObject("localTimeJP", localTimeJP);
				//現在年月のみを文字列で表すオブジェクトを作成
				DateTimeFormatter yearMonthFormat = DateTimeFormatter.ofPattern("yyyy-MM");
				String yearMonthLocalDateJPStr = localDateJP.format(yearMonthFormat);

				//View側にyearMonthLocalDateJPStrを渡す
				mav.addObject("yearMonthLocalDateJPStr", yearMonthLocalDateJPStr);

				// usersレコードを取得する 主キーのIDで検索するため1件（サンプル）のみ取得
				UserEntity userEntity = userRepository.findUserRecordByUserID(1);
				//View側にuserEntityを渡す
				mav.addObject("userEntity", userEntity);

				//ユーザーの作成年月を文字列で表すオブジェクトを作成
				//Localdate型でないとformatメソッドが使えないのでtoLocalDate()を使用
				String userCreatedWhenStr = userEntity.getUserCreatedWhen()
														.toLocalDate().format(yearMonthFormat);

				//View側にuserCreatedWhenStrを渡す
				mav.addObject("userCreatedWhenStr", userCreatedWhenStr);

				//参考：https://qiita.com/parapore/items/4acffd670fc913e05d85
				//JPAにはRepositoryインターフェースに、命名規則に従ったメソッド名を書くとSQLを自動生成する機能がある
				//現在の年月のレコードを取得する 現在の年月とフォーマット（いずれも文字列）を渡して使用
				Iterable<SampleDiaryEntity> sampleDiaryList = sampleDiaryRepository.findDiaryRecordsByYearMonth(yearMonthLocalDateJPStr, "yyyy-MM");
//				https://pointsandlines.jp/server-side/java/model-and-view
//				addObject()メソッドではView側へ渡すオブジェクトのデータを
//				第一引数にテンプレートから参照する変数名、
//				第二引数にオブジェクト名として格納している
				mav.addObject("sampleDiaryList", sampleDiaryList);
				// 「setViewNameの引数のファイル名」に対応した
				// /myWebAppDietDiary/src/main/resources/templates内の
				// ファイルを表示する
				mav.setViewName("MyPage");
		        return mav;
	}

	@RequestMapping("/sampleMyPageThisMonth")
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
		//現在年月のみを文字列で表すオブジェクトを作成
		DateTimeFormatter yearMonthFormat = DateTimeFormatter.ofPattern("yyyy-MM");
		String yearMonthLocalDateJPStr = localDateJP.format(yearMonthFormat);

		// usersレコードを取得する 主キーのIDで検索するため1件（サンプル）のみ取得
		UserEntity userEntity = userRepository.findUserRecordByUserID(1);
		//View側にuserEntityを渡す
		mav.addObject("userEntity", userEntity);
		//参考：https://qiita.com/parapore/items/4acffd670fc913e05d85
		//JPAにはRepositoryインターフェースに、命名規則に従ったメソッド名を書くとSQLを自動生成する機能がある
		//現在の年月のレコードを取得する 現在の年月とフォーマット（いずれも文字列）を渡して使用
		Iterable<SampleDiaryEntity> sampleDiaryList = sampleDiaryRepository.findDiaryRecordsByYearMonth(yearMonthLocalDateJPStr, "yyyy-MM");
//		https://pointsandlines.jp/server-side/java/model-and-view
//		addObject()メソッドではView側へ渡すオブジェクトのデータを
//		第一引数にテンプレートから参照する変数名、
//		第二引数にオブジェクト名として格納している
		mav.addObject("sampleDiaryList", sampleDiaryList);
		// 「setViewNameの引数のファイル名」に対応した
		// /myWebAppDietDiary/src/main/resources/templates内の
		// ファイルを表示する
		mav.setViewName("sampleMyPageThisMonth");
        return mav;
	}
	@RequestMapping("/sampleDBRead")
	//ModelAndViewオブジェクトを返す
	public ModelAndView index2_2() {
		ModelAndView mav = new ModelAndView();

		//日本（東京）の現在日時を取得
		ZonedDateTime nowDateTimeJP = ZonedDateTime.now(ZoneId.of("Asia/Tokyo"));
		//現在日時から日付・時刻それぞれのオブジェクトを作成
		LocalDate localDateJP = nowDateTimeJP.toLocalDate();
		LocalTime localTimeJP = nowDateTimeJP.toLocalTime();
		//上記で取得した日付・時刻を、変数localDateJP、localTimeJPのオブジェクトとして登録
		mav.addObject("localDateJP", localDateJP);
		mav.addObject("localTimeJP", localTimeJP);
		//現在年月のみを文字列で表すオブジェクトを作成
		DateTimeFormatter yearMonthFormat = DateTimeFormatter.ofPattern("yyyy-MM");
		String yearMonthLocalDateJPStr = localDateJP.format(yearMonthFormat);
		// usersレコードを取得する 主キーのIDで検索するため1件（サンプル）のみ取得
		UserEntity userEntity = userRepository.findUserRecordByUserID(1);
		//View側にuserEntityを渡す
		mav.addObject("userEntity", userEntity);

		//参考：https://qiita.com/parapore/items/4acffd670fc913e05d85
		//JPAにはRepositoryインターフェースに、命名規則に従ったメソッド名を書くとSQLを自動生成する機能がある
		//現在の年月のレコードを取得する 現在の年月とフォーマット（いずれも文字列）を渡して使用
		Iterable<SampleDiaryEntity> sampleDiaryList = sampleDiaryRepository.findDiaryRecordsByYearMonth(yearMonthLocalDateJPStr, "yyyy-MM");
//		https://pointsandlines.jp/server-side/java/model-and-view
//		addObject()メソッドではView側へ渡すオブジェクトのデータを
//		第一引数にテンプレートから参照する変数名、
//		第二引数にオブジェクト名として格納している
		mav.addObject("sampleDiaryList", sampleDiaryList);
		// 「setViewNameの引数のファイル名」に対応した
		// /myWebAppDietDiary/src/main/resources/templates内の
		// ファイルを表示する
		mav.setViewName("sampleMyPageThisMonth");
        return mav;
	}
	@RequestMapping("/sampleMyPageNovember")
	//ModelAndViewオブジェクトを返す
	public ModelAndView indexNovember() {
		ModelAndView mav = new ModelAndView();

		//2022年11月を表すための日付文字列
		String yearMonthStr = "2022-11";

		//参考：https://qiita.com/parapore/items/4acffd670fc913e05d85
		//JPAにはRepositoryインターフェースに、命名規則に従ったメソッド名を書くとSQLを自動生成する機能がある
		//2022年11月のレコードを取得する 2022年11月の年月とフォーマット（いずれも文字列）を渡して使用
		Iterable<SampleDiaryEntity> sampleDiaryList = sampleDiaryRepository.findDiaryRecordsByYearMonth(yearMonthStr, "yyyy-MM");
//		https://pointsandlines.jp/server-side/java/model-and-view
		// usersレコードを取得する 主キーのIDで検索するため1件（サンプル）のみ取得
		UserEntity userEntity = userRepository.findUserRecordByUserID(1);
		//View側にuserEntityを渡す
		mav.addObject("userEntity", userEntity);
//		addObject()メソッドではView側へ渡すオブジェクトのデータを
//		第一引数にテンプレートから参照する変数名、
//		第二引数にオブジェクト名として格納している
		mav.addObject("sampleDiaryList", sampleDiaryList);
		// 「setViewNameの引数のファイル名」に対応した
		// /myWebAppDietDiary/src/main/resources/templates内の
		// ファイルを表示する
		mav.setViewName("sampleMyPageNovember");
        return mav;
	}
	@RequestMapping("/sampleMyPageDecember")
	//ModelAndViewオブジェクトを返す
	public ModelAndView indexDecember() {
		ModelAndView mav = new ModelAndView();

		//2022年12月を表すための日付文字列
		String yearMonthStr = "2022-12";

		//参考：https://qiita.com/parapore/items/4acffd670fc913e05d85
		//JPAにはRepositoryインターフェースに、命名規則に従ったメソッド名を書くとSQLを自動生成する機能がある
		//2022年12月のレコードを取得する 2022年12月の年月とフォーマット（いずれも文字列）を渡して使用
		Iterable<SampleDiaryEntity> sampleDiaryList = sampleDiaryRepository.findDiaryRecordsByYearMonth(yearMonthStr, "yyyy-MM");
//		https://pointsandlines.jp/server-side/java/model-and-view
		// usersレコードを取得する 主キーのIDで検索するため1件（サンプル）のみ取得
		UserEntity userEntity = userRepository.findUserRecordByUserID(1);
		//View側にuserEntityを渡す
		mav.addObject("userEntity", userEntity);
//		addObject()メソッドではView側へ渡すオブジェクトのデータを
//		第一引数にテンプレートから参照する変数名、
//		第二引数にオブジェクト名として格納している
		mav.addObject("sampleDiaryList", sampleDiaryList);
		// 「setViewNameの引数のファイル名」に対応した
		// /myWebAppDietDiary/src/main/resources/templates内の
		// ファイルを表示する
		mav.setViewName("sampleMyPageDecember");
        return mav;
	}
	@RequestMapping("/sampleDBReadArchive")
	//ModelAndViewオブジェクトを返す
	public ModelAndView index4() {
		//月を指定せずに全データを取得する
		ModelAndView mav = new ModelAndView();

		//日本（東京）の現在日時を取得
		ZonedDateTime nowDateTimeJP = ZonedDateTime.now(ZoneId.of("Asia/Tokyo"));
		//現在日時から日付・時刻それぞれのオブジェクトを作成
		LocalDate localDateJP = nowDateTimeJP.toLocalDate();
		LocalTime localTimeJP = nowDateTimeJP.toLocalTime();
		//上記で取得した日付・時刻を、変数localDateJP、localTimeJPのオブジェクトとして登録
		mav.addObject("localDateJP", localDateJP);
		mav.addObject("localTimeJP", localTimeJP);

		//参考：https://qiita.com/parapore/items/4acffd670fc913e05d85
		//JPAにはRepositoryインターフェースに、命名規則に従ったメソッド名を書くとSQLを自動生成する機能がある
		//日記レコード一覧を取得する際にDate順にソートする
		Iterable<SampleDiaryEntity> sampleDiaryList = sampleDiaryRepository.findAllByOrderByDateAsc();
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

	//参考：https://qiita.com/kuro227/items/a16e22ac12afe7442a3d
	//DB更新用（乱数）のサンプルのメソッド
	SampleDiaryEntity updateSampleDiary(SampleDiaryEntity sde, String randomNumberStr) {
		//日記エンティティの持つ日付の値が2022-12-01の場合
		// ==だと参照型なので等しい判定にならない equals()を使用
		if(sde.getDate().toString().equals("2022-12-01")) {
			//引数で受け取った乱数（文字列化済）を日記欄にセット
			sde.setDiaryText(randomNumberStr);
			//日記欄を変更したエンティティをセット（戻り値はセット後のエンティティ）
			//戻り値であるセット後のエンティティをそのままメソッドの戻り値とする
			return sampleDiaryRepository.save(sde);
		}
		//日付の値が2022-12-01でない場合そのまま日記エンティティを返す
		return sde;
	}

	//保存ボタン押下時のDB更新用（入力フォームに入力した値）のメソッド
	//post送信時に呼び出される
	//引数のSampleDiaryEntityは保存ボタン押下時に送信されたもの
	@RequestMapping("/formSave")
	public String formSave(SampleDiaryEntity sdeFromForm) {
		//ModelAndView mav = new ModelAndView();
		//日本（東京）の現在日時を取得
		ZonedDateTime nowDateTimeJP = ZonedDateTime.now(ZoneId.of("Asia/Tokyo"));
		//現在日時から日付のオブジェクトを作成
		LocalDate localDateJP = nowDateTimeJP.toLocalDate();
			System.out.println(sdeFromForm + "←が表示されていればsdeはnullではない");
			//getDate()含め、getDiaryText()以外は引数のsdeFromFormではnull
			//引数のsdeFromFormでは、保存ボタン押下時に送信した値がgetDiaryText()で取得可
			System.out.println(sdeFromForm.getDate() + "←が表示されていればsde.getDate()はnullではない");
			System.out.println(sdeFromForm.getWeight() + "←が表示されていればsde.getWeight()はnullではない");
			System.out.println(sdeFromForm.getDiaryText() + "←が表示されていればsde.getDiaryText()はnullではない");

			//DBのデータをエンティティのリストとして取得
		    Iterable<SampleDiaryEntity> sampleDiaryList = sampleDiaryRepository.findAll();

			//DBから取得した日記のエンティティのうち本日の日付のもののみ処理対象にする
		    for(SampleDiaryEntity sde : sampleDiaryList){
		    	//日付判定の際、NullPointerExceptionを防止
		    	if(sde.getDate() != null && sde.getDate().toString().equals(localDateJP.toString())){
		    	//本日の日付の日記のエンティティに対して、引数のSampleDiaryEntity
		    	//に保存された体重欄・日記欄（入力フォームのもの）で値を更新する
		    		sde.setWeight(sdeFromForm.getWeight());
		    		sde.setDiaryText(sdeFromForm.getDiaryText());
		    		//日記欄を更新したエンティティでDBを更新
		    		sampleDiaryRepository.save(sde);
		    	}
		    	//本日の日付でない場合は処理は無し
	        }
		    //DB内容表示ページに遷移（保存ボタン押下後に再表示）
		    return "redirect:/sampleMyPageThisMonth";

		    //下記の処理では、テーブル内の値がすべてnullになってしまう
		    //（@RequestMapping("/sampleDBRead")のメソッド内の処理が行われてDBから値が入る前にページが開かれるため）

		    //mav.setViewName("sampleDBRead");
			//return mav;
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
}