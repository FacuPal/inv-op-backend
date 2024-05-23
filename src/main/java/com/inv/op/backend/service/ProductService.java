package com.inv.op.backend.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inv.op.backend.dto.ProductResponseDto;
import com.inv.op.backend.model.Product;
import com.inv.op.backend.repository.ProductRepository;

@Service
public class ProductService {
    
    @Autowired
    ProductRepository productRepository;

    public void saveProduct(Product newProduct) {
        productRepository.save(newProduct);
    }

    public ProductResponseDto getProducts(Long id) {

        ProductResponseDto productResponseDto = new ProductResponseDto();
        Optional<Product> product = productRepository.findById(id);

        if (product.isPresent()) {
            Product productData = product.get();
            productResponseDto.setDescription(productData.getDescription());
            productResponseDto.setPrice(productData.getPrice());
            productResponseDto.setFamily(productData.getFamily());
            productResponseDto.setStock(productData.getStock());
        }
        return productResponseDto;
    }
    
}
