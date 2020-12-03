//package com.example.practice_batch;
//
//import com.example.practice_batch.entity.Pay2;
//import org.springframework.data.jdbc.repository.query.Query;
//import org.springframework.data.jpa.repository.JpaRepository;
//
//import java.util.List;
//
///**
// * Created by jojoldu@gmail.com on 2018. 9. 15.
// * Blog : http://jojoldu.tistory.com
// * Github : https://github.com/jojoldu
// */
//
//public interface PayRepository extends JpaRepository<Pay2, Long> {
//
//    @Query("SELECT p FROM Pay2 p WHERE p.successStatus = 0")
//    List<Pay2> findAllSuccess();
//}