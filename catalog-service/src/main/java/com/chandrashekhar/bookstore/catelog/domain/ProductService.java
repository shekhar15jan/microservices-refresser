package com.chandrashekhar.bookstore.catelog.domain;

import java.util.List;
import java.util.Optional;

public interface ProductService {
    PageResult<Product> getProducts(int pageNo);
    Optional<Product> getProductByCode(String code);
}
