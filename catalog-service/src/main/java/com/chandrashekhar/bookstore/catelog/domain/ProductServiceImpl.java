package com.chandrashekhar.bookstore.catelog.domain;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional //to rollback transaction if it fails
class ProductServiceImpl implements ProductService{

    private final ProductRepository productRepository;
    private final ApplicationProperties applicationProperties;

    ProductServiceImpl(ProductRepository productRepository, ApplicationProperties applicationProperties){
        this.productRepository = productRepository;
        this.applicationProperties = applicationProperties;
    }

    @Override
    public PageResult<Product> getProducts(int pageNo) {
        Sort sort = Sort.by("name").ascending();
        pageNo = pageNo <= 1 ? 0: pageNo -1; // page number stats from 0 in java hence managing it
        Pageable  pageable = PageRequest.of(pageNo,applicationProperties.pageSize(), sort);
//        Page<Product> productPage = productRepository.findAll(pageable)
//                .map(productEntity -> ProductMapper.toProduct(productEntity));
        Page<Product> productPage = productRepository.findAll(pageable)
                .map(ProductMapper::toProduct);

        PageResult<Product> pageResult = new PageResult<>(
                productPage.getContent(),
                productPage.getTotalElements(),
                productPage.getNumber() +1,
                productPage.getTotalPages(),
                productPage.isFirst(),
                productPage.isLast(),
                productPage.hasNext(),
                productPage.hasPrevious()
        );
        return pageResult;
    }
}
