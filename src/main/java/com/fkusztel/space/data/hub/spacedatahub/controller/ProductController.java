package com.fkusztel.space.data.hub.spacedatahub.controller;

import com.fkusztel.space.data.hub.spacedatahub.entity.Product;
import com.fkusztel.space.data.hub.spacedatahub.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

/**
 * @author Filip.Kusztelak
 */
@Slf4j
@Controller
@RequestMapping(path = "product")
public class ProductController {

    @Autowired
    ProductService productService;

    //Create ne product object and save it to database
    @PostMapping(path = "/create")
    public @ResponseBody
    String createProduct(@RequestParam String acquisitionDate, @RequestParam String missionName,
                         @RequestParam List<String> footprint, @RequestParam Double price, @RequestParam String url) {

        Product product = Product.builder()
                .acquisitionDate(LocalDate.parse(acquisitionDate))
                .footprint(footprint)
                .nameMission(missionName)
                .price(price)
                .url(url)
                .build();

        log.info("createProduct: {}", product.toString());
        productService.saveProduct(product);
        return "Created " + product.toString();
    }

    //Find all products in database
    @GetMapping(path = "/all")
    public @ResponseBody
    Iterable<Product> getAllProducts() {
        return productService.findAll();
    }

    //Finds a date with lower value than given one
    @GetMapping(path = "/lower")
    public @ResponseBody
    Iterable<Product> findProductByDateLower(String date) {
        log.info("findProductByDateLower: {}", date);
        return productService.findProductByDateLower(LocalDate.parse(date));
    }

    //Finds a date with grater value than given one
    @GetMapping(path = "/greater")
    public @ResponseBody
    Iterable<Product> findProductByDateGreater(String date) {
        log.info("findProductByDateGreater: {}", date);
        return productService.findProductByDateGreater(LocalDate.parse(date));
    }

    //Finds a date between two given dates
    @GetMapping(path = "/between")
    public @ResponseBody
    Iterable<Product> findProductByDateBetween(String startDate,
                                               String endDate) {

        log.info("findProductByDateBetween: {}{}", startDate, endDate);
        return productService.findProductByDateBetween(LocalDate.parse(startDate),
                LocalDate.parse(endDate));
    }
}
