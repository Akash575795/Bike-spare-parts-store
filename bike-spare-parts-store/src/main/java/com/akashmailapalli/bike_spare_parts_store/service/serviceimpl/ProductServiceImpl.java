package com.akashmailapalli.bike_spare_parts_store.service.serviceimpl;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.akashmailapalli.bike_spare_parts_store.dto.ProductDTO;
import com.akashmailapalli.bike_spare_parts_store.model.Product;
import com.akashmailapalli.bike_spare_parts_store.repository.ProductRepository;
import com.akashmailapalli.bike_spare_parts_store.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;

    private final ModelMapper modelmapper;

    
    public ProductServiceImpl(ProductRepository productRepository, ModelMapper modelmapper) {
        this.productRepository = productRepository;
        this.modelmapper = modelmapper;
    }

    @Override
    public ProductDTO createProduct(ProductDTO productDTO) {
        
        Product product = modelmapper.map(productDTO,Product.class);

        Product savedProduct = productRepository.save(product);

        ProductDTO saveProductDTO = modelmapper.map((savedProduct), ProductDTO.class);

        return saveProductDTO;
    }

    @Override
    public void deleteProduct(Long id) {
        Product product = productRepository.findById(id) 
        .orElseThrow(()-> new RuntimeException("product Not Found"));
       productRepository.delete(product);
    }

    @Override
    public List<ProductDTO> getAllProducts() {
        List<Product> products = productRepository.findAll();
        return products.stream().map(product -> modelmapper.map(product, ProductDTO.class))
        .collect(Collectors.toList());
    }

    @Override
    public ProductDTO updateProduct(Long id, ProductDTO productDTO) {
        Product exproduct = productRepository.findById(id).orElseThrow(()-> new RuntimeException("product not found"));

        exproduct.setId(productDTO.getId());
        exproduct.setName(productDTO.getName());
        exproduct.setPrice(productDTO.getPrice());
        exproduct.setStock(productDTO.getStock());
        exproduct.setDescription(productDTO.getDescription());
        exproduct.setCreatedAt(LocalDateTime.now());
        exproduct.setUpdatedAt(LocalDateTime.now());
        
        Product updatedProduct = productRepository.save(exproduct);

        return modelmapper.map(updatedProduct, ProductDTO.class);

    }

    
}
