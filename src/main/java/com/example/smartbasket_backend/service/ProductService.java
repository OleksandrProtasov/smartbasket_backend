package com.example.smartbasket_backend.service;

import com.example.smartbasket_backend.model.Product;
import com.example.smartbasket_backend.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    // Добавление продукта в базу
    public Product addProduct(Product product) {
        return productRepository.save(product);
    }

    // Получение всех продуктов
    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    // Получение товара по ID
    public Product getProduct(Long id) {
        return productRepository.findById(id).orElse(null); // Возвращаем товар или null, если не найден
    }

    // Получение продукта по ID
    public Optional<Product> getProductById(Long id) {
        return productRepository.findById(id);
    }
    // Метод обновления продукта
    public Product updateProduct(Product product) {
        return productRepository.save(product); // Сохраняем изменения в базе данных
    }


    // Сохранение изображения для продукта
    public String saveImage(Long productId, MultipartFile file) throws IOException {
        Optional<Product> productOpt = productRepository.findById(productId);
        if (productOpt.isPresent()) {
            Product product = productOpt.get();

            // Генерация уникального имени для файла
            String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
            String uploadDir = "uploads/"; // Папка для сохранения файлов

            // Проверка и создание папки, если она не существует
            File dir = new File(uploadDir);
            if (!dir.exists()) {
                boolean created = dir.mkdir();  // Попытка создать папку
                if (!created) {
                    throw new IOException("Не удалось создать папку для загрузки.");
                }
            }

            // Сохранение файла на диск
            File destinationFile = new File(uploadDir + fileName);
            file.transferTo(destinationFile);  // Перенос файла в папку

            // Добавление URL изображения в список
            List<String> imageUrls = product.getImageUrls();
            imageUrls.add("/uploads/" + fileName);  // Добавляем ссылку на изображение
            product.setImageUrls(imageUrls);

            productRepository.save(product);  // Сохраняем продукт в БД

            return "/uploads/" + fileName;  // Возвращаем ссылку на файл
        }

        throw new IOException("Продукт с таким ID не найден.");
    }
}
