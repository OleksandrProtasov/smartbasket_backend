package com.example.smartbasket_backend.service;

import com.example.smartbasket_backend.model.Product;
import com.example.smartbasket_backend.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    // Добавление нового товара
    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    // Получение товара по ID
    public Product getProduct(Long id) {
        return productRepository.findById(id).orElse(null); // Возвращаем товар или null, если не найден
    }

    // Получение товара по ID (для обновления и удаления)
    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id); // Возвращаем Optional, потому что товар может не существовать
    }

    // Обновление товара
    public Product updateProduct(Product product) {
        return productRepository.save(product); // Сохраняем обновленный товар
    }

    // Удаление товара
    public void deleteProduct(Long id) {
        productRepository.deleteById(id); // Удаляем товар по ID
    }
}
