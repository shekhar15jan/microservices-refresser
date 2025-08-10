package com.chandrashekhar.bookstore.catelog.domain;

class ProductMapper {
    // static method which will be accessed by class name no instace is created
    static Product toProduct(ProductEntity productEntity) {
        return new Product(
                productEntity.getCode(),
                productEntity.getName(),
                productEntity.getDescription(),
                productEntity.getImageUrl(),
                productEntity.getPrice());
    }
}
