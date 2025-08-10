package com.chandrashekhar.bookstore.catelog.domain;

import java.util.Optional;

public interface ProductService {
    PageResult<Product> getProducts(int pageNo);

    Optional<Product> getProductByCode(String code);
}
