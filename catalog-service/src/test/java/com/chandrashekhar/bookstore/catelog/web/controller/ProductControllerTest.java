package com.chandrashekhar.bookstore.catelog.web.controller;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;

import com.chandrashekhar.bookstore.catelog.AbstractIT;
import com.chandrashekhar.bookstore.catelog.domain.Product;
import io.restassured.http.ContentType;
import java.math.BigDecimal;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.jdbc.Sql;

@Sql("/test-data.sql")
public class ProductControllerTest extends AbstractIT {
    @Test
    void shouldReturnProduct() {
        given().contentType(ContentType.JSON)
                .when()
                .get("/api/products")
                .then()
                .statusCode(200)
                .body("data", hasSize(10))
                .body("totalElements", is(15))
                .body("pageNumber", is(1))
                .body("totalPages", is(2))
                .body("isFirst", is(true))
                .body("isLast", is(false))
                .body("hasNext", is(true))
                .body("hasPrevious", is(false));
    }

    @Test
    void shouldReturnProductByCode() {
        Product product = given().contentType(ContentType.JSON)
                .when()
                .get("/api/products/P104")
                .then()
                .statusCode(200)
                .assertThat()
                .extract()
                .body()
                .as(Product.class);

        assertThat(product.code()).isEqualTo("P104");
        assertThat(product.name()).isEqualTo("The Fault in Our Stars");
        assertThat(product.description())
                .isEqualTo(
                        "Despite the tumor-shrinking medical miracle that has bought her a few years, Hazel has never been anything but terminal, her final chapter inscribed upon diagnosis.");
        assertThat(product.imageUrl()).isEqualTo("https://images.gr-assets.com/books/1360206420l/11870085.jpg");
        assertThat(product.price()).isEqualTo(new BigDecimal("14.50"));
    }

    @Test
    void shouldReturnNotFoundWhenProductCodeDoesNotExist() {
        String code = "invalid_code";
        given().contentType(ContentType.JSON)
                .when()
                .get("/api/products/{code}", code)
                .then()
                .statusCode(404)
                .body("status", is(404))
                .body("title", is("Product Not found"))
                .body("detail", is("Product with code " + code + " not found"));
    }
}
