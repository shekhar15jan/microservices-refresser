package com.chandrashekhar.bookstore.catelog.web.controller;

import com.chandrashekhar.bookstore.catelog.domain.PageResult;
import com.chandrashekhar.bookstore.catelog.domain.Product;
import com.chandrashekhar.bookstore.catelog.domain.ProductNotFoundException;
import com.chandrashekhar.bookstore.catelog.domain.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
class ProductController {

    private final ProductService productService;

    ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    PageResult<Product> getProducts(@RequestParam(name = "page", defaultValue = "1") int pageNo){
        return productService.getProducts(pageNo);
    }

    @GetMapping("/{code}")
    ResponseEntity<Product> findProductByCode(@PathVariable(name="code") String code){
        return productService.getProductByCode(code)
                .map(ResponseEntity::ok)
                .orElseThrow(()-> ProductNotFoundException.forCode(code)); //used factory method for custom exception
    }
}
