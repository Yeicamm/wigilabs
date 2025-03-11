package com.prueba.wigilabs.controller;

import com.prueba.wigilabs.dto.ProductDTO;
import com.prueba.wigilabs.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/products")
@RequiredArgsConstructor
@Tag(name = "Productos", description = "API para gestión de productos")
public class ProductController {

    private final ProductService productService;

    @GetMapping
    @Operation(
            summary = "Obtener todos los productos",
            description = "Devuelve una lista de productos disponibles en la base de datos.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Lista de productos encontrada",
                            content = @Content(mediaType = "application/json", schema = @Schema(implementation = ProductDTO.class))),
                    @ApiResponse(responseCode = "403", description = "No autorizado")
            }
    )
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        return ResponseEntity.ok(productService.getAllProducts(0, 10));
    }

    @GetMapping("/{id}")
    @Operation(
            summary = "Obtener producto por ID",
            description = "Busca un producto por su ID en la base de datos.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Producto encontrado"),
                    @ApiResponse(responseCode = "404", description = "Producto no encontrado")
            }
    )
    public ResponseEntity<ProductDTO> getProductById(@PathVariable Long id) {
        Optional<ProductDTO> product = productService.getProductById(id);
        return product.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    @Operation(
            summary = "Crear un nuevo producto",
            description = "Registra un nuevo producto en la base de datos.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Producto creado"),
                    @ApiResponse(responseCode = "400", description = "Datos inválidos")
            }
    )
    public ResponseEntity<ProductDTO> createProduct(@Valid @RequestBody ProductDTO productDTO) {
        return ResponseEntity.ok(productService.createProduct(productDTO));
    }

    @PutMapping("/{id}")
    @Operation(
            summary = "Actualizar un producto",
            description = "Modifica un producto existente en la base de datos.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Producto actualizado"),
                    @ApiResponse(responseCode = "404", description = "Producto no encontrado")
            }
    )
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long id, @Valid @RequestBody ProductDTO productDTO) {
        Optional<ProductDTO> updatedProduct = productService.updateProduct(id, productDTO);
        return updatedProduct.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(
            summary = "Eliminar un producto",
            description = "Elimina un producto de la base de datos.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "Producto eliminado"),
                    @ApiResponse(responseCode = "404", description = "Producto no encontrado")
            }
    )
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        boolean deleted = productService.deleteProduct(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
