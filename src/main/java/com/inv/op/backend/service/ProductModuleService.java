package com.inv.op.backend.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import com.inv.op.backend.dto.DTOProductoLista;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;

import com.inv.op.backend.dto.CreateProductRequest;
import com.inv.op.backend.dto.ProductDto;
import com.inv.op.backend.dto.SupplierDto;
import com.inv.op.backend.error.product.ProductFamilyNotFound;
import com.inv.op.backend.error.product.ProductNotFoundError;
import com.inv.op.backend.error.product.ProductSaveError;
import com.inv.op.backend.error.supplier.SupplierNotFoundError;
import com.inv.op.backend.model.Product;
import com.inv.op.backend.model.ProductFamily;
import com.inv.op.backend.repository.InventoryModelRepository;
import com.inv.op.backend.repository.ProductFamilyRepository;
import com.inv.op.backend.repository.ProductRepository;
import com.inv.op.backend.repository.SupplierRepository;

@Service
public class ProductModuleService {

    @Autowired
    ProductRepository productRepository;
    @Autowired
    ProductFamilyRepository productFamilyRepository;
    @Autowired
    InventoryModelRepository inventoryModelRepository;
    @Autowired
    SupplierRepository supplierRepository;

    public CreateProductRequest saveProduct(CreateProductRequest newProduct) {

        ProductFamily productFamily = productFamilyRepository.findById(newProduct.getProductFamilyId()).orElseThrow(() -> new ProductFamilyNotFound());

        // if (!productFamily.isPresent()) {
        //     throw new ProductFamilyNotFound();
        // }

        // Product(Long id, String name, String description, ProductFamily
        // productFamily, Integer optimalBatch, Integer orderLimit, Integer safeStock,
        // Integer stock, Boolean isDeleted

        Product product = new Product(null,
                newProduct.getProductName(),
                newProduct.getProductDescription(),
                productFamily,
                newProduct.getOptimalBatch(),
                newProduct.getOrderLimit(),
                newProduct.getSafeStock(),
                newProduct.getStock(),
                false);

        try {
            productRepository.save(product);
        } catch (Exception e) {
            throw new ProductSaveError();
        }

        // return new DefaultResponseDto(HttpStatus.CREATED, "Product Created");
        return newProduct;

    }

    public Optional<ProductDto> getProduct(Long id) {

        Optional<ProductDto> product = productRepository.findProductByProductId(id);

        if (!product.isPresent()) {
            throw new ProductNotFoundError();
        }

        return product;
    }
    public List<DTOProductoLista> getAllProducts() {
        return productRepository.findAll().stream()
                .map(DTOProductoLista::new)
                .collect(Collectors.toList());
    }
    // Suppliers Services
    public SupplierDto getSupplier(Long id) {

        Optional<SupplierDto> supplier = supplierRepository.getSupplierDtoBySupplierId(id);

        if (!supplier.isPresent()) {
            throw new SupplierNotFoundError();
        }

        return supplier.get();
    }
    public DTOProductoLista getDTOProductoLista(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundError());

        return new DTOProductoLista(product);
    }

}
