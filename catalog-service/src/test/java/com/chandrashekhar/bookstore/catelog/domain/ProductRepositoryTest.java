package com.chandrashekhar.bookstore.catelog.domain;

import static org.assertj.core.api.Assertions.assertThat;

import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

@DataJpaTest(
        properties = {
            "spring.test.database.replace=none",
            "spring.datasource.url=jdbc:tc:postgresql:16-alpine:///db",
        }) // required to individually test of repository
// @Import(TestcontainersConfiguration.class) //this is not recommended because testcontainerConfiguraiton can contain
// multiple containers
@Sql("/test-data.sql")
class ProductRepositoryTest {

    @Autowired
    private ProductRepository productRepository;

    @Test
    void shouldGetAllTheProducts() {
        List<ProductEntity> productEntityList = productRepository.findAll();
        assertThat(productEntityList).hasSize(15);
    }

    @Test // Repository successfully return the product details by product
    void shouldGetProductByCode() {
        ProductEntity product = productRepository.findByCode("P104").orElseThrow();
        assertThat(product.getCode()).isEqualTo("P104");
        assertThat(product.getName()).isEqualTo("The Fault in Our Stars");
        assertThat(product.getDescription())
                .isEqualTo(
                        "Despite the tumor-shrinking medical miracle that has bought her a few years, Hazel has never been anything but terminal, her final chapter inscribed upon diagnosis.");
        assertThat(product.getImageUrl()).isEqualTo("https://images.gr-assets.com/books/1360206420l/11870085.jpg");
        assertThat(product.getPrice()).isEqualTo(new BigDecimal("14.50"));
    }

    @Test // Repository should return empty if product code is not exist
    void shouldReturnEmptyWhenProductCodeNotExist() {
        assertThat(productRepository.findByCode("P1041")).isEmpty();
    }
}
