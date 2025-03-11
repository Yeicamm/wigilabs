package com.prueba.wigilabs.service;

import com.prueba.wigilabs.dto.ProductDTO;
import java.util.List;
import java.util.Optional;

public interface ProductService {
    List<ProductDTO> getAllProducts(int page, int size);
    Optional<ProductDTO> getProductById(Long id);
    ProductDTO createProduct(ProductDTO productDTO);
    Optional<ProductDTO> updateProduct(Long id, ProductDTO productDTO);
    boolean deleteProduct(Long id);
}
