package com.example.demo.Repository;

import com.example.demo.Model.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductsRepository extends JpaRepository<Products, Integer> {

    Optional<Products> findByName(String name);

}
