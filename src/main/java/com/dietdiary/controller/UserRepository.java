package com.dietdiary.controller;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.dietdiary.entity.UserEntity;

//日記用のSpring Data リポジトリインターフェイス（サンプル）
//https://spring.pleiades.io/spring-data/jpa/docs/current/reference/html/

//JPAにはRepositoryインターフェースに、命名規則に従ったメソッド名を書くとSQLを自動生成する機能がある
public interface UserRepository extends CrudRepository<UserEntity, Integer> {
//CrudRepositoryから継承した抽象メソッドに加え、下記の抽象メソッドを持たせる

	//ユーザーのレコードを取得する
	/*PostgreSQLではカラム名であることを明示する場合はダブルクオーテーション
	  文字列であることを明示する場合はシングルクオーテーション
	 */
	//ダブルクオーテーションをエスケープする カラム名は大文字小文字区別あり
	@Query(value = "select * from USERS where \\\"USER_ID\\\" = ?1",
            nativeQuery = true)
	//下記メソッドを呼び出す際にUserIDを指定して使用する
	UserEntity findUserRecordByUserID(int userID);

	//レコード登録用メソッド
	@Query(value = "insert into USERS(\\\"USER_NAME\\\", \\\"PASSWORD\\\", \\\"WEIGHT_GOAL\\\", \\\"USER_CREATED_WHEN\\\") "
			+ "values (?1, ?2, ?3, CURRENT_DATE);",
            nativeQuery = true)
	//ユーザー名、パスワード、目標体重を入力した状態のエンティティを引数にして登録 作成日は上記のCURRENT_DATE USER_IDは自動採番
	void saveExceptUserID(UserEntity user);

}
