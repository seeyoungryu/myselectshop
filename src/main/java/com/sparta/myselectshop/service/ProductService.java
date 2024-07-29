package com.sparta.myselectshop.service;

import com.sparta.myselectshop.dto.ProductRequestDto;
import com.sparta.myselectshop.dto.ProductResponseDto;
import com.sparta.myselectshop.entity.Product;
import com.sparta.myselectshop.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProductService {

    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);
    private final ProductRepository productRepository;

    @Transactional
    public ProductResponseDto createProduct(ProductRequestDto requestDto) {
        logger.info("Creating product with title: {}", requestDto.getTitle());
        Product product = new Product(requestDto);
        logger.info("Saving product to database");
        product = productRepository.save(product);
        logger.info("Product saved with id: {}", product.getId());
        return new ProductResponseDto(product);
    }
}
//    @Transactional
//    public ProductResponseDto createProduct(ProductRequestDto requestDto) {
//        Product product = new Product(requestDto);
//        product = productRepository.save(product);
//        return new ProductResponseDto(product);
//    }

