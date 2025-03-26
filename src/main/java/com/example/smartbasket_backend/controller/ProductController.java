package com.example.smartbasket_backend.controller;

import com.example.smartbasket_backend.model.Product;
import com.example.smartbasket_backend.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    // Добавление нового товара
    @PostMapping("/add")
    public Product addProduct(@RequestBody Product product) {
        return productService.addProduct(product);
    }

    // Получение товара по ID
    @GetMapping("/{id}")
    public Product getProduct(@PathVariable Long id) {
        return productService.getProduct(id);
    }

    // Обновление товара
    @PutMapping("/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable Long id, @RequestBody Product product) {
        Optional<Product> existingProduct = productService.getProductById(id); // Проверяем, существует ли товар с данным ID
        if (existingProduct.isPresent()) {
            product.setId(id); // Убедимся, что ID из пути запроса не заменит ID из тела запроса
            Product updatedProduct = productService.updateProduct(product);
            return ResponseEntity.ok(updatedProduct); // Возвращаем обновленный товар
        }
        return ResponseEntity.notFound().build(); // Если товар не найден, возвращаем 404
    }

    // Удаление товара
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        Optional<Product> existingProduct = productService.getProductById(id);
        if (existingProduct.isPresent()) {
            productService.deleteProduct(id);
            return ResponseEntity.noContent().build(); // Удаляем товар, возвращаем статус 204 (No Content)
        }
        return ResponseEntity.notFound().build(); // Если товар не найден, возвращаем 404
    }
}
