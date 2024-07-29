package com.sparta.myselectshop.service;

import com.sparta.myselectshop.dto.ProductMypriceRequestDto;
import com.sparta.myselectshop.dto.ProductRequestDto;
import com.sparta.myselectshop.dto.ProductResponseDto;
import com.sparta.myselectshop.entity.Product;
import com.sparta.myselectshop.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.task.ThreadPoolTaskSchedulerBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ProductService {

    public static final int MIN_MY_PRICE = 100;

    private static final Logger logger = LoggerFactory.getLogger(ProductService.class);
    private final ProductRepository productRepository;
    private final ThreadPoolTaskSchedulerBuilder threadPoolTaskSchedulerBuilder;

    @Transactional
    public ProductResponseDto createProduct(ProductRequestDto requestDto) {
        logger.info("Creating product with title: {}", requestDto.getTitle());
        Product product = new Product(requestDto);
        logger.info("Saving product to database");
        product = productRepository.save(product);
        logger.info("Product saved with id: {}", product.getId());
        return new ProductResponseDto(product);
    }

    //    @Transactional
    //    public ProductResponseDto createProduct(ProductRequestDto requestDto) {
    //        Product product = new Product(requestDto);
    //        product = productRepository.save(product);
    //        return new ProductResponseDto(product);
    //    }


    @Transactional      //더티체킹 - 변경감지
    public ProductResponseDto updateProduct(Long id, ProductMypriceRequestDto requestDto) {
        int myprice = requestDto.getMyprice();
        //logger.info("Updating product with id: {}", id);
        if (myprice < MIN_MY_PRICE) {
            throw new IllegalArgumentException("유효하지 않은 관심 가격, 최소 " + MIN_MY_PRICE + "이상 설정해주시오");
        }

        Product product = productRepository.findById(id).orElseThrow(() ->
                new NullPointerException("해당 상품을 찾을 수 없음!")
        );

        product.update(requestDto);

        return new ProductResponseDto(product);

    }

    //스트림 사용 안하는 코드
//    public List<ProductResponseDto> getProducts() {
//        List<Product> productList = productRepository.findAll();
//        List<ProductResponseDto> responseDtoList = new ArrayList<>();
//        for (Product product : productList) {
//            responseDtoList.add(new ProductResponseDto(product));
//        }
//        return responseDtoList;


    //스트림 사용 코드
    public List<ProductResponseDto> getProducts() {
        return productRepository.findAll().stream()
                .map(ProductResponseDto::new)
                .collect(Collectors.toList());
    }

}



