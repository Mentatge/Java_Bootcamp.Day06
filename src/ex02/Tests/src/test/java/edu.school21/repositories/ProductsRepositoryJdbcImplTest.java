package edu.school21.repositories;

import edu.school21.models.Product;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class ProductsRepositoryJdbcImplTest {
    private DataSource dataSource;
    private Connection connection;
    private ProductsRepositoryJdbcImpl productsRepositoryJdbcImpl;

    final List<Product> EXPECTED_FIND_ALL_PRODUCTS = Arrays.asList(
            new Product(0, "milk", 80),
            new Product(1, "cheese", 200),
            new Product(2, "bread", 50),
            new Product(3, "eggs", 70),
            new Product(4, "tea", 90)
    );
    final Product EXPECTED_FIND_BY_ID_PRODUCT = new Product(0, "milk", 80);
    final Product EXPECTED_UPDATED_PRODUCT = new Product(1, "bear", 250);


    @BeforeEach
    public void init() throws SQLException {
        dataSource = new EmbeddedDatabaseBuilder().setType(EmbeddedDatabaseType.HSQL)
                .addScript("schema.sql")
                .addScript("data.sql")
                .build();
        connection = dataSource.getConnection();
        productsRepositoryJdbcImpl = new ProductsRepositoryJdbcImpl(connection);
    }

    @AfterEach
    public void closeConnection() throws SQLException {
        connection.close();
    }

    @Test
    public void testFindAll() throws SQLException {
        Assertions.assertEquals(EXPECTED_FIND_ALL_PRODUCTS, productsRepositoryJdbcImpl.findAll());
    }

    @Test
    public void testFindById() throws SQLException {
        Assertions.assertEquals(EXPECTED_FIND_BY_ID_PRODUCT, productsRepositoryJdbcImpl.findById(0).get());
    }

    @Test
    public void testUpdate() throws SQLException {
        Product productToUpdate = new Product(1, "bear", 250);
        productsRepositoryJdbcImpl.update(productToUpdate);
        Assertions.assertEquals(EXPECTED_UPDATED_PRODUCT, productsRepositoryJdbcImpl.findById(1).get());
    }

    @Test
    public void testDelete() throws SQLException {
        productsRepositoryJdbcImpl.delete(1L);
        Assertions.assertEquals(Optional.empty(), productsRepositoryJdbcImpl.findById(1));
    }

    @Test
    public void testSave() throws SQLException {
        Product productToSave = new Product(5, "bear", 250);
        productsRepositoryJdbcImpl.save(productToSave);
        Assertions.assertEquals(productToSave, productsRepositoryJdbcImpl.findById(5).get());
    }
}
