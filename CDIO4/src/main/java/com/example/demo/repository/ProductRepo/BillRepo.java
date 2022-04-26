package com.example.demo.repository.ProductRepo;

import com.example.demo.model.Bill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BillRepo extends JpaRepository<Bill,Integer> {
    @Query(value = "select * from bill where id_user = :id ;", nativeQuery = true)
    List<Bill> findBills(@Param("id") int id);
}
