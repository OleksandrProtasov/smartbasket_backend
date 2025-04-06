package com.example.smartbasket_backend.controller;

import com.example.smartbasket_backend.model.Product;
import com.example.smartbasket_backend.model.User;
import com.example.smartbasket_backend.service.ProductService;
import com.example.smartbasket_backend.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private UserService userService; // Добавляем зависимость от UserService

    // Добавление нового товара
    @PostMapping("/add")
    public ResponseEntity<String> addProduct(@RequestBody Product product, @RequestParam Long userId) {
        User user = userService.getUserById(userId);
        if (user != null && user.getRole().equals("ADMIN")) {
            productService.addProduct(product);
            return ResponseEntity.ok("Product added successfully");
        } else {
            return ResponseEntity.status(403).body("You do not have permission to add products.");
        }
    }

    // Получение товара по ID
    @GetMapping("/{id}")
    public Product getProduct(@PathVariable Long id) {
        return productService.getProduct(id);
    }

    // Загрузка изображений для продукта
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

            // Путь для сохранения изображений
            String uploadDir = "uploads/";

            // Убедимся, что директория существует
            Path path = Paths.get(uploadDir);
            if (!Files.exists(path)) {
                Files.createDirectories(path);
            }

            for (MultipartFile file : files) {
                if (!file.isEmpty()) {
                    // Генерация уникального имени для файла
                    String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
                    Path filePath = path.resolve(fileName);

                    // Сохраняем файл на диск
                    file.transferTo(filePath.toFile());

                    // Добавляем новый URL изображения в список
                    imageUrls.add(uploadDir + fileName);
                }
            }

            // Обновляем список изображений в продукте
            product.setImageUrls(imageUrls);
            productService.updateProduct(product);

            return ResponseEntity.ok("Files uploaded and product updated with image paths!");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Server error while uploading the files");
        }
    }

    @GetMapping("/{id}/images")
    public ResponseEntity<List<String>> getProductImages(@PathVariable Long id) {
        Product product = productService.getProduct(id);
        if (product == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(product.getImageUrls() != null ? product.getImageUrls() : new ArrayList<>());
    }

    // Получение всех товаров
    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    // Удаление товара
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable Long id, @RequestParam Long userId) {
        User user = userService.getUserById(userId);
        if (user != null && user.getRole().equals("ADMIN")) {
            productService.deleteProduct(id);
            return ResponseEntity.ok("Product deleted successfully");
        } else {
            return ResponseEntity.status(403).body("You do not have permission to delete products.");
        }
    }
}
