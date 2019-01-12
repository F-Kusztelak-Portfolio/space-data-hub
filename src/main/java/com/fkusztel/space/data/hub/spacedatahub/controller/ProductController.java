package com.fkusztel.space.data.hub.spacedatahub.controller;

import com.fkusztel.space.data.hub.spacedatahub.entity.Product;
import com.fkusztel.space.data.hub.spacedatahub.exception.ProductNotFoundException;
import com.fkusztel.space.data.hub.spacedatahub.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * @author Filip.Kusztelak
 */
@Slf4j
@Controller
@RequestMapping(path = "product")
public class ProductController {

    @Autowired
    ProductService productService;

    //Create new product object and save it to database
    @PostMapping(path = "/create")
    public @ResponseBody
    String createProduct(@RequestParam String acquisitionDate, @RequestParam String missionName,
                         @RequestParam List<String> footprint, @RequestParam Double price) {

        Product product = Product.builder()
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

    //Find all products in database -[FOR DEVELOPMENT PURPOSE]
    @GetMapping(path = "/all")
    public @ResponseBody
    Iterable<Product> getAllProducts() {
        return productService.findAll();
    }

    //Finds a date with lower value than given one
    @GetMapping(path = "/lower")
    public @ResponseBody
    Iterable<Product> findProductByDateLower(String date) throws ProductNotFoundException {
        log.info("findProductByDateLower: {}", date);

        try {
            return productService.findProductByDateLower(LocalDate.parse(date));
        } catch(NoSuchElementException e) {
            throw new ProductNotFoundException();
        }
    }

    //Finds a date with grater value than given one
    @GetMapping(path = "/greater")
    public @ResponseBody
    Iterable<Product> findProductByDateGreater(String date) throws ProductNotFoundException {
        log.info("findProductByDateGreater: {}", date);

        try {
            return productService.findProductByDateGreater(LocalDate.parse(date));
        } catch(NoSuchElementException e) {
            throw new ProductNotFoundException();
        }
    }

    //Finds a date between two given dates
    @GetMapping(path = "/between")
    public @ResponseBody
    Iterable<Product> findProductByDateBetween(String startDate,
                                               String endDate) throws ProductNotFoundException {

        log.info("findProductByDateBetween: {}{}", startDate, endDate);

        try {
            return productService.findProductByDateBetween(LocalDate.parse(startDate),
                    LocalDate.parse(endDate));
        } catch(NoSuchElementException e) {
            throw new ProductNotFoundException();
        }
    }

    //Find product with given ID
    @GetMapping(path = "/read")
    public @ResponseBody
    Product readProductById(@RequestParam Long productId) throws ProductNotFoundException {
        log.info("readProductById {}", productId);

        try {
            return productService.findProduct(productId).get();
        } catch(NoSuchElementException e) {
            throw new ProductNotFoundException();
        }
    }

    //Delete product with given ID
    @DeleteMapping(path = "/delete")
    public @ResponseBody
    String deleteProduct(@RequestParam Long productId) throws ProductNotFoundException {
        log.info("deleteProduct with id: {}" , productId);

        try {
            productService.deleteProduct(productId);
            return "Product: " + productId + " deleted successfully";
        } catch(NoSuchElementException e) {
            throw new ProductNotFoundException();
        }
    }

    //Purchase product with given ID
    @PutMapping(path = "/order")
    public @ResponseBody
    String purchaseProduct(@RequestParam List<Long> productId) {
        return productService.purchaseProduct(productId);
    }
}
