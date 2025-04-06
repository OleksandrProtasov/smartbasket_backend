package com.example.smartbasket_backend.repository;

import com.example.smartbasket_backend.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    // Дополнительные методы для работы с продуктами
    void deleteById(Long id);
}
