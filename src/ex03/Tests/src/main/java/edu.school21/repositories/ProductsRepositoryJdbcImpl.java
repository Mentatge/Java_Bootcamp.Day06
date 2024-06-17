package edu.school21.repositories;

import edu.school21.models.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ProductsRepositoryJdbcImpl implements ProductsRepository {
    private Connection connection;

    public ProductsRepositoryJdbcImpl(Connection connection) {
        this.connection = connection;
    }

    @Override
    public List<Product> findAll() throws SQLException {
        List<Product> products = new ArrayList<>();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM PRODUCT");
        while (resultSet.next()) {
            products.add(new Product(resultSet.getInt("id"), resultSet.getString("name"), resultSet.getInt("price")));
        }
        resultSet.close();
        return products;
    }

    @Override
    public Optional<Product> findById(int id) throws SQLException {
        Optional<Product> product = Optional.empty();
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery("SELECT * FROM PRODUCT WHERE id = " + id);
        if (resultSet.next()) {
            return Optional.of(new Product(resultSet.getInt("id"), resultSet.getString("name"), resultSet.getInt("price")));
        }
        return product;
    }

    @Override
    public void save(Product product) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO PRODUCT (name, price) VALUES (?, ?)");
        preparedStatement.setString(1, product.getName());
        preparedStatement.setInt(2, product.getPrice());
        preparedStatement.executeUpdate();
        ResultSet resultSet = preparedStatement.getGeneratedKeys();
        if (resultSet.next()) {
            product.setId(resultSet.getInt(1));
        }
    }

    @Override
    public void delete(Long id) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM PRODUCT WHERE id = " + id);
        if (preparedStatement.executeUpdate() == 0) {
            throw new SQLException("Could not delete product");
        }
        preparedStatement.close();
    }

    @Override
    public void update(Product product) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("UPDATE PRODUCT SET name = ?, price = ? WHERE id = ?");
        preparedStatement.setString(1, product.getName());
        preparedStatement.setInt(2, product.getPrice());
        preparedStatement.setInt(3, product.getId());
        if (preparedStatement.executeUpdate() == 0) {
            throw new SQLException("Could not update product");
        }
        preparedStatement.close();
    }
}
