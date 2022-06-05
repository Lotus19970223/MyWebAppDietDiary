//https://qiita.com/t-shin0hara/items/687085ec34ae78ca2260

/*@Data・・・Lombokの@Dataアノテーションです。
GetterやSetter、toStringなどのコードを自動生成してくれます。
自動生成されている事は、[アウトライン]ビューで確認できます（下記イメージ参照）。
モデルには、GetterやSetterが必要ですが、この定型コードを補ってくれるのがLombokです。
下のイメージのとおり、Userクラス（※SampleUserModel）にはフィールド定義のみしかしていませが、アウトラインにはGetterやSetterが表示されている事が分かります。
当然ですが、Lombokを使用しない場合は、GetterやSetterの記述が必要となります。
 */

package com.dietdiary.model;

import lombok.Data;

@Data
public class SampleUserModel {
    private String name;
    private String email;
    private Integer age;
}
