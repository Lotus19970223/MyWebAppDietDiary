package com.dietdiary.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
        	/*interface UserRepository extends CrudRepository<UserEntity, Integer>からコピー箇所
        	@Query(value = "select * from USERS where \\\"USER_NAME\\\" = ?1",
                    nativeQuery = true)
        	//下記メソッドを呼び出す際にUserIDを指定して使用する
        	UserEntity findUserRecordByUserName(String userName);
        	コピー箇所終わり*/

            //元のSQL
        	//String sql = "SELECT * FROM user WHERE name = ?";
            //変更後SQL
        	String sql = "select * from USERS where \"USER_NAME\" = ?";
        	//String sql = "select * from USERS where USER_NAME = ?1";

            Map<String, Object> map = jdbcTemplate.queryForMap(sql, username);
            String password = (String)map.get("password");
            Collection<GrantedAuthority> authorities = new ArrayList<>();
            authorities.add(new SimpleGrantedAuthority((String)map.get("authority")));
            //コンパイルエラーのためキャスト
            //元はreturn new UserDetailsImpl(username, password, authorities);
            //test
            System.out.println("map:" + map); System.out.println("password:" + password);
            System.out.println("authorities:" + authorities);
            return (UserDetails) new UserDetailsImpl(username, password, authorities);
        } catch (Exception e) {
        	System.out.println("Exception:" + e);
            throw new UsernameNotFoundException("user not found.", e);
        }
    }
}
