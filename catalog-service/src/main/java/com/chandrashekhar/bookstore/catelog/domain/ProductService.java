package com.chandrashekhar.bookstore.catelog.domain;

import java.util.List;

public interface ProductService {
    PageResult<Product> getProducts(int pageNo);
}
