package com.fkusztel.space.data.hub.spacedatahub.service;

import com.fkusztel.space.data.hub.spacedatahub.config.Constants;
import com.fkusztel.space.data.hub.spacedatahub.entity.Product;
import com.fkusztel.space.data.hub.spacedatahub.entity.ProductRepository;
import com.fkusztel.space.data.hub.spacedatahub.exception.ProductNotFoundException;
import com.google.common.collect.Lists;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

/**
 * @author Filip.Kusztelak
 */
@Slf4j
@Service
public class ProductServiceImpl implements ProductService {

  @Autowired ProductRepository productRepository;

  /**
   * Save product to database with the specified values.
   *
   * @param product Given product entity.
   */
  @Override
  public void saveProduct(Product product) {
    productRepository.save(product);
  }

  /**
   * Find product in database with the specified values.
   *
   * @param productId ID of the product.
   * @exception ProductNotFoundException Given product was not found in database
   */
  @Override
  public Product findProduct(Long productId) throws ProductNotFoundException {
    Product product =
        productRepository.findById(productId).orElseThrow(ProductNotFoundException::new);

    // Check if product is purchased and set correct URL
    if (product.getPurchased()) {
      product.setUrl(Constants.ftp.FTP_URL);
      saveProduct(product);
      return product;
    } else {
      product.setUrl(Constants.ftp.FTP_PURCHASE);
      saveProduct(product);
      return product;
    }
  }

  /**
   * Find a date with lower value than given one with the specified values.
   *
   * @param date Date from which the search should be smaller (2018-09-08).
   */
  @Override
  public Iterable<Product> findProductByDateLower(LocalDate date) {
    List<Product> productList = Lists.newArrayList(findAll());

    // Filter products and collect all with date lower than given by User
    return productList
        .stream()
        .filter(product -> product.getAcquisitionDate().isBefore(date))
        .collect(Collectors.toList());
  }

  /**
   * Find a date with grater value than given one with the specified values.
   *
   * @param date Date from which the search should be greater (2018-01-01).
   */
  @Override
  public Iterable<Product> findProductByDateGreater(LocalDate date) {
    List<Product> productList = Lists.newArrayList(findAll());

    // Filter products and collect all with date greater than given by User
    return productList
        .stream()
        .filter(product -> product.getAcquisitionDate().isAfter(date))
        .collect(Collectors.toList());
  }

  /**
   * Find a date between two given dates with the specified values.
   *
   * @param startDate Date from which the search should be smaller (2018-01-01).
   * @param endDate Date from which the search should be greater (2018-02-02).
   */
  @Override
  public Iterable<Product> findProductByDateBetween(LocalDate startDate, LocalDate endDate) {
    List<Product> productList = Lists.newArrayList(findAll());
    List<Product> datesBetween = Lists.newArrayList();

    // Check all products and if date is between start and end date
    for (Product product : productList) {
      if (product.getAcquisitionDate().isAfter(startDate)
          && product.getAcquisitionDate().isBefore(endDate)) {

        datesBetween.add(product);
      }
    }
    return datesBetween;
  }

  /** Find all products in database - [FOR DEVELOPMENT PURPOSE] */
  @Override
  public Iterable<Product> findAll() {
    return productRepository.findAll();
  }

  /**
   * Delete product from database with the specified values.
   *
   * @param productId ID of the product.
   * @exception ProductNotFoundException Given product was not found in database
   */
  @Override
  public void deleteProduct(Long productId) throws ProductNotFoundException {
    try {
      productRepository.deleteById(productId);
    } catch (EmptyResultDataAccessException e) {
      throw new ProductNotFoundException();
    }
  }

  /**
   * Purchase products with the specified values.
   *
   * @param productId List of products ID ([2, 3]).
   */
  @Override
  public String purchaseProduct(List<Long> productId) {
    Product product;

    // Get all products from the list and update purchase field
    for (Long id : productId) {

      log.info("purchaseProduct: {}", id);

      // Check if product exists and update purchase field
      try {
        product = findProduct(id);
        product.setPurchased(true);
        saveProduct(product);
        log.info("Product with ID: {}", id + " purchased");
      } catch (ProductNotFoundException e) {
        return "Purchase failed, no such products available";
      }
    }
    return "Order completed";
  }
}
