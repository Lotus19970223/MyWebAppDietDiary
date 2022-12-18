package com.dietdiary.controller;

import java.sql.Date;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.dietdiary.entity.SampleDiaryEntity;

//日記用のSpring Data リポジトリインターフェイス（サンプル）
//https://spring.pleiades.io/spring-data/jpa/docs/current/reference/html/

//JPAにはRepositoryインターフェースに、命名規則に従ったメソッド名を書くとSQLを自動生成する機能がある
public interface SampleDiaryRepository extends CrudRepository<SampleDiaryEntity, Date> {
//CrudRepositoryから継承した抽象メソッドに加え、下記の抽象メソッドを持たせる
	//日記レコード一覧を取得する際にDate順にソートする テーブル内の全レコードを取得する
	Iterable<SampleDiaryEntity> findAllByOrderByDateAsc();

	//参考：https://oracle.programmer-reference.com/oracle-date-compare-ymd/
	//		https://www.shift-the-oracle.com/sql/functions/to_date.html
	//TO_DATE関数：TO_DATE(str, fmt)→文字列とフォーマットを引数にする
	//作成したいSQL：date列（DATE型）の日付が現在の年・月であるもの
	//SELECT * FROM sample_diaries WHERE TRUNC(date) = TO_DATE('2022/12', 'YYYY/MM');
	//→SELECT * FROM sample_diaries WHERE TRUNC(date) = TO_DATE(?1, ?2);
	//プレースホルダに現在の年月とフォーマットを入れて使う

	//日記レコード一覧を取得する際にDate順にソートする 現在の年月のレコードを取得する
	//参考：https://postgresweb.com/postgresql-date-trunc  date列の値を日単位まで切り捨ててから判定する
	//      https://workmemo.techblog.jp/archives/9550585.html
	@Query(value = "select * from SAMPLE_DIARIES where date_trunc('day', date) = TO_DATE(?1, ?2) ORDER BY date",
            nativeQuery = true)
	//下記メソッドを呼び出す際に現在の年月とフォーマットを指定して使用する
	Iterable<SampleDiaryEntity> findDiaryRecordsByNowYearMonth(String nowYearMonth, String format);
}
