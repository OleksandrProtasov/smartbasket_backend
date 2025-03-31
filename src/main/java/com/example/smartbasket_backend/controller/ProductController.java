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
    @PostMapping("/{id}/upload-images")
    public ResponseEntity<String> uploadImages(@PathVariable Long id, @RequestParam("files") MultipartFile[] files) {
        try {
            if (files.length == 0) {
                return ResponseEntity.badRequest().body("No files selected");
            }

            Product product = productService.getProduct(id);
            if (product == null) {
                return ResponseEntity.notFound().build();
            }

            List<String> imageUrls = product.getImageUrls();  // Получаем текущие URL-ы изображений

            for (MultipartFile file : files) {
                if (!file.isEmpty()) {
                    // Генерация уникального имени для файла (чтобы избежать перезаписывания)
                    String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
                    String uploadDir = "uploads/";

                    // Путь для сохранения изображения
                    java.nio.file.Path path = java.nio.file.Paths.get(uploadDir + fileName);
                    java.nio.file.Files.createDirectories(path.getParent());
                    file.transferTo(path.toFile());

                    // Добавляем новый URL изображения в список
                    imageUrls.add(uploadDir + fileName);
                }
            }

            // Обновляем список изображений в продукте
            product.setImageUrls(imageUrls);
            productService.updateProduct(product);

            return ResponseEntity.ok("Files uploaded and product updated with image paths!");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Server error while uploading the files");
        }
    }

    // Получение всех товаров
    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }
}
