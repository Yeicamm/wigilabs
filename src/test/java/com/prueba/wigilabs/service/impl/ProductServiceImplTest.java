package com.prueba.wigilabs.service.impl;

import com.prueba.wigilabs.dto.ProductDTO;
import com.prueba.wigilabs.model.Product;
import com.prueba.wigilabs.repository.ProductRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductServiceImpl productService;

    private Product product;
    private ProductDTO productDTO;

    @BeforeEach
    void setUp() {
        product = new Product(1L, "Laptop Gamer", "Laptop de alto rendimiento", new BigDecimal("1800.50"), 10);
        productDTO = new ProductDTO("Laptop Gamer", "Laptop de alto rendimiento", new BigDecimal("1800.50"), 10);
    }

    @Test
    void testGetAllProducts() {
        Pageable pageable = PageRequest.of(0, 10);
        List<Product> productList = List.of(product);
        Page<Product> productPage = new PageImpl<>(productList, pageable, productList.size());

        when(productRepository.findAll(pageable)).thenReturn(productPage);

        List<ProductDTO> products = productService.getAllProducts(0, 10);

        assertNotNull(products);
        assertEquals(1, products.size());
        assertEquals("Laptop Gamer", products.get(0).getName());

        verify(productRepository, times(1)).findAll(pageable);
    }

    @Test
    void testGetProductById_WhenExists() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        Optional<ProductDTO> result = productService.getProductById(1L);

        assertTrue(result.isPresent());
        assertEquals("Laptop Gamer", result.get().getName());
        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    void testGetProductById_WhenNotExists() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<ProductDTO> result = productService.getProductById(1L);

        assertFalse(result.isPresent());
        verify(productRepository, times(1)).findById(1L);
    }

    @Test
    void testCreateProduct() {
        when(productRepository.save(any(Product.class))).thenReturn(product);

        ProductDTO result = productService.createProduct(productDTO);

        assertNotNull(result);
        assertEquals("Laptop Gamer", result.getName());
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void testUpdateProduct_WhenExists() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product));
        when(productRepository.save(any(Product.class))).thenReturn(product);

        Optional<ProductDTO> result = productService.updateProduct(1L, productDTO);

        assertTrue(result.isPresent());
        assertEquals("Laptop Gamer", result.get().getName());
        verify(productRepository, times(1)).findById(1L);
        verify(productRepository, times(1)).save(any(Product.class));
    }

    @Test
    void testUpdateProduct_WhenNotExists() {
        when(productRepository.findById(1L)).thenReturn(Optional.empty());

        Optional<ProductDTO> result = productService.updateProduct(1L, productDTO);

        assertFalse(result.isPresent());
        verify(productRepository, times(1)).findById(1L);
        verify(productRepository, never()).save(any(Product.class));
    }

    @Test
    void testDeleteProduct_WhenExists() {
        when(productRepository.existsById(1L)).thenReturn(true);
        doNothing().when(productRepository).deleteById(1L);

        boolean result = productService.deleteProduct(1L);

        assertTrue(result);
        verify(productRepository, times(1)).existsById(1L);
        verify(productRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteProduct_WhenNotExists() {
        when(productRepository.existsById(1L)).thenReturn(false);

        boolean result = productService.deleteProduct(1L);

        assertFalse(result);
        verify(productRepository, times(1)).existsById(1L);
        verify(productRepository, never()).deleteById(1L);
    }
}
