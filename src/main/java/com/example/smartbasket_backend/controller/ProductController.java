package com.example.smartbasket_backend.controller;

import com.example.smartbasket_backend.model.Product;
import com.example.smartbasket_backend.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;


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

            // Путь, по которому будет храниться изображение
            String fileName = file.getOriginalFilename();
            String uploadDir = "uploads/";
            String filePath = uploadDir + fileName;

            // Здесь сохраняем файл в папку uploads
            file.transferTo(new java.io.File(filePath));

            // Находим товар по ID
            Product product = productService.getProduct(id);
            if (product == null) {
                return ResponseEntity.notFound().build();
            }

            // Сохраняем путь к изображению в товаре
            product.setImageUrl(filePath);
            productService.updateProduct(product);  // Этот метод должен обновить товар в базе данных

            return ResponseEntity.ok("File uploaded and product updated with image path!");
        } catch (Exception e) {
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
