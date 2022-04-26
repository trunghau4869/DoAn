package com.example.demo.repository.AccountRepo;

import com.example.demo.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AccountRepo extends JpaRepository<Account, String> {


    Account findByUserName(String account);
    @Query(value = "select pass_word from account where user_name =:userName", nativeQuery = true)
    String  findByPass(@Param("userName") String userName);

//    @Query(value="select *  from  `user` join `account` where `account`.user_name=`user`.user_name and  `user`.user_name=:userName", nativeQuery = true)
    @Query(value="select *  from  `user` join`account`  where  `account`.user_name=`user`.user_name and  gmail=:gmail", nativeQuery = true)

    Account findAccountByGmail(@Param("gmail")String gmail);


    Account findAccountByResetPasswordToken(String token);

}
