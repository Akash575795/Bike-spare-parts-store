package com.akashmailapalli.bike_spare_parts_store.service;

import java.util.List;

import com.akashmailapalli.bike_spare_parts_store.dto.ProductDTO;

public interface ProductService {

    ProductDTO createProduct(ProductDTO productDTO);
    List<ProductDTO> getAllProducts();
    ProductDTO updateProduct(Long id, ProductDTO productDTO);
    void deleteProduct(Long id);
}
