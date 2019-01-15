package com.fkusztel.space.data.hub.spacedatahub.controller;

import com.fkusztel.space.data.hub.spacedatahub.entity.Product;
import com.fkusztel.space.data.hub.spacedatahub.exception.ProductNotFoundException;
import com.fkusztel.space.data.hub.spacedatahub.service.ProductService;
import java.time.LocalDate;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Filip.Kusztelak
 */
@Slf4j
@Controller
@RequestMapping(path = "product")
public class ProductController {

  @Autowired ProductService productService;

  /**
   * Create a new Product with the specified values.
   *
   * @param acquisitionDate Date when mission was acquired (2018-09-08).
   * @param missionName Name of mission that took remote sensing images.
   * @param footprint Image footprint (["18769", "22213", "99123", "8876"]).
   * @param price Price of given product.
   */
  @PostMapping(path = "/create")
  public @ResponseBody String createProduct(
      @RequestParam String acquisitionDate,
      @RequestParam String missionName,
      @RequestParam List<String> footprint,
      @RequestParam Double price) {

    Product product =
        Product.builder()
            .acquisitionDate(LocalDate.parse(acquisitionDate))
            .footprint(footprint)
            .nameMission(missionName)
            .purchased(false)
            .price(price)
            .build();

    log.info("createProduct: {}", product.toString());
    productService.saveProduct(product);
    return "Created " + product.toString();
  }

  /** Find all products in database - [FOR DEVELOPMENT PURPOSE] */
  @GetMapping(path = "/all")
  public @ResponseBody Iterable<Product> getAllProducts() {
    return productService.findAll();
  }

  /**
   * Find a date with lower value than given one with the specified values.
   *
   * @param date Date from which the search should be smaller (2018-09-08).
   */
  @GetMapping(path = "/lower")
  public @ResponseBody Iterable<Product> findProductByDateLower(String date) {
    log.info("findProductByDateLower: {}", date);

    return productService.findProductByDateLower(LocalDate.parse(date));
  }

  /**
   * Find a date with grater value than given one with the specified values.
   *
   * @param date Date from which the search should be greater (2018-01-01).
   */
  @GetMapping(path = "/greater")
  public @ResponseBody Iterable<Product> findProductByDateGreater(String date) {
    log.info("findProductByDateGreater: {}", date);

    return productService.findProductByDateGreater(LocalDate.parse(date));
  }

  /**
   * Find a date between two given dates with the specified values.
   *
   * @param startDate Date from which the search should be smaller (2018-01-01).
   * @param endDate Date from which the search should be greater (2018-02-02).
   */
  @GetMapping(path = "/between")
  public @ResponseBody Iterable<Product> findProductByDateBetween(
      String startDate, String endDate) {

    log.info("findProductByDateBetween: {}{}", startDate, endDate);

    return productService.findProductByDateBetween(
        LocalDate.parse(startDate), LocalDate.parse(endDate));
  }

  /**
   * Find product in database with the specified values.
   *
   * @param productId ID of the product.
   * @exception ProductNotFoundException Given product was not found in database
   */
  @GetMapping(path = "/read")
  public @ResponseBody Product readProductById(@RequestParam Long productId)
      throws ProductNotFoundException {
    log.info("readProductById {}", productId);

    return productService.findProduct(productId);
  }

  /**
   * Delete product from database with the specified values.
   *
   * @param productId ID of the product.
   * @exception ProductNotFoundException Given product was not found in database
   */
  @DeleteMapping(path = "/delete")
  public @ResponseBody String deleteProduct(@RequestParam Long productId)
      throws ProductNotFoundException {
    log.info("deleteProduct with id: {}", productId);

    productService.deleteProduct(productId);
    return "Product: " + productId + " deleted successfully";
  }

  /**
   * Purchase products with the specified values.
   *
   * @param productId List of products ID ([2, 3]).
   */
  @PutMapping(path = "/order")
  public @ResponseBody String purchaseProduct(@RequestParam List<Long> productId) {
    return productService.purchaseProduct(productId);
  }
}
