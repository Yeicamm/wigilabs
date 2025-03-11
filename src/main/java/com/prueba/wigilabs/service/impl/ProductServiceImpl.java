package com.prueba.wigilabs.service.impl;

import com.prueba.wigilabs.dto.ProductDTO;
import com.prueba.wigilabs.model.Product;
import com.prueba.wigilabs.repository.ProductRepository;
import com.prueba.wigilabs.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public List<ProductDTO> getAllProducts(int page, int size) {
        log.info("Listando productos - Página: {}, Tamaño: {}", page, size);
        Pageable pageable = PageRequest.of(page, size);
        Page<Product> products = productRepository.findAll(pageable);
        return products.stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public Optional<ProductDTO> getProductById(Long id) {
        return productRepository.findById(id).map(this::convertToDTO);
    }

    @Override
    public ProductDTO createProduct(ProductDTO productDTO) {
        log.info("Creando producto: {}", productDTO.getName());
        Product product = convertToEntity(productDTO);
        return convertToDTO(productRepository.save(product));
    }

    @Override
    public Optional<ProductDTO> updateProduct(Long id, ProductDTO productDTO) {
        return productRepository.findById(id).map(existingProduct -> {
            existingProduct.setName(productDTO.getName());
            existingProduct.setDescription(productDTO.getDescription());
            existingProduct.setPrice(productDTO.getPrice());
            existingProduct.setStock(productDTO.getStock());
            return convertToDTO(productRepository.save(existingProduct));
        });
    }

    @Override
    public boolean deleteProduct(Long id) {
        log.warn("Eliminando producto con ID: {}", id);
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            return true;
        }
        return false;
    }

    private ProductDTO convertToDTO(Product product) {
        return new ProductDTO(product.getName(), product.getDescription(), product.getPrice(), product.getStock());
    }

    private Product convertToEntity(ProductDTO productDTO) {
        return new Product(null, productDTO.getName(), productDTO.getDescription(), productDTO.getPrice(), productDTO.getStock());
    }
}
