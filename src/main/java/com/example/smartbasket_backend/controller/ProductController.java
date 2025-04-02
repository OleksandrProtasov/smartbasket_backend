package com.example.smartbasket_backend.controller;

import com.example.smartbasket_backend.model.Product;
import com.example.smartbasket_backend.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;

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

    // Загрузка изображения для продукта
    @PostMapping("/{id}/upload-image")
    public ResponseEntity<String> uploadImage(@PathVariable Long id, @RequestParam("file") MultipartFile file) {
        try {
            if (file.isEmpty()) {
                return ResponseEntity.badRequest().body("No file selected");
            }

            // Генерируем уникальное имя файла
            String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
            Path filePath = Paths.get("uploads", fileName);

            // Сохраняем файл на диск
            Files.createDirectories(filePath.getParent());
            Files.write(filePath, file.getBytes());

            // Сохраняем путь к файлу в базе данных
            Product product = productService.getProduct(id);
            product.setImageUrl(fileName);
            productService.updateProduct(product);

            return ResponseEntity.ok("File uploaded successfully!");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Server error while uploading the file");
        }
    }

    // Получение всех товаров
    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }
}
