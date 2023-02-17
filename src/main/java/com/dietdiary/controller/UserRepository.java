package com.dietdiary.controller;

import java.math.BigDecimal;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.dietdiary.entity.UserEntity;

//日記用のSpring Data リポジトリインターフェイス（サンプル）
//https://spring.pleiades.io/spring-data/jpa/docs/current/reference/html/

//JPAにはRepositoryインターフェースに、命名規則に従ったメソッド名を書くとSQLを自動生成する機能がある
@Repository
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

	//上記メソッドのユーザー名版
	@Query(value = "select * from USERS where \\\"USER_NAME\\\" = ?1",
            nativeQuery = true)
	//下記メソッドを呼び出す際にUserIDを指定して使用する
	UserEntity findUserRecordByUserName(String userName);

	//ユーザーのレコード一覧を取得する（）
	@Query(value = "select * from USERS order by \\\"USER_ID\\\"",
            nativeQuery = true)
	Iterable<UserEntity> findAllByOrderByUserId();

	//レコード登録用メソッド(エラーになる　引数のUserEntityを?1にあてはめて?2以降が空白になるため？？)
	@Query(value = "insert into USERS(\\\"USER_NAME\\\", \\\"PASSWORD\\\", \\\"WEIGHT_GOAL\\\", \\\"USER_CREATED_WHEN\\\") "
			+ "values (?1, ?2, ?3, CURRENT_DATE);",
            nativeQuery = true)
	//ユーザー名、パスワード、目標体重を入力した状態のエンティティを引数にして登録 作成日は上記のCURRENT_DATE USER_IDは自動採番
	void saveExceptUserID(UserEntity user);

	//レコード登録用メソッド @Transactionalと@Modifyingをつけないとselect文以外の場合エラーになるのでつける
	@Transactional
	@Modifying
	@Query(value = "insert into USERS(\\\"USER_NAME\\\", \\\"PASSWORD\\\", \\\"WEIGHT_GOAL\\\", \\\"USER_CREATED_WHEN\\\") "
			+ "values (?1, ?2, ?3, CURRENT_DATE)",
            nativeQuery = true)
	//ユーザー名、パスワード、目標体重を引数にして登録 作成日は上記のCURRENT_DATE  USER_IDは自動採番
	void saveByParameters(String userName, String password, BigDecimal weightGoal);

	//レコード登録用メソッド仮
	@Transactional
	@Modifying
	@Query(value = "insert into USERS(\\\"USER_ID\\\", \\\"USER_NAME\\\", \\\"PASSWORD\\\", \\\"WEIGHT_GOAL\\\", \\\"USER_CREATED_WHEN\\\") "
			+ "values (7, ?1, ?2, ?3, CURRENT_DATE)",
            nativeQuery = true)
	//ユーザー名、パスワード、目標体重を引数にして登録 作成日は上記のCURRENT_DATE  USER_IDは自動採番
	void saveByParametersTest(String userName, String password, BigDecimal weightGoal);



}

