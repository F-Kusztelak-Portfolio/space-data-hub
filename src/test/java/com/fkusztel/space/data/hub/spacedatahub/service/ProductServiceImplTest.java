package com.fkusztel.space.data.hub.spacedatahub.service;

import com.fkusztel.space.data.hub.spacedatahub.config.Constants;
import com.fkusztel.space.data.hub.spacedatahub.config.TestObjectFactory;
import com.fkusztel.space.data.hub.spacedatahub.entity.Product;
import com.fkusztel.space.data.hub.spacedatahub.entity.ProductRepository;
import com.fkusztel.space.data.hub.spacedatahub.exception.ProductNotFoundException;
import com.google.common.collect.Lists;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author Filip.Kusztelak
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = {ProductServiceImplTest.class, ProductServiceImpl.class})
public class ProductServiceImplTest {

  @Autowired private ProductServiceImpl productService;

  @MockBean private ProductRepository productRepository;

  @Test
  public void findProduct_successPurchased() throws ProductNotFoundException {
    Mockito.when(productRepository.findById(1L))
        .thenReturn(Optional.of(TestObjectFactory.NewProduct.productNotPurchased));

    Product exceptedResult = TestObjectFactory.NewProduct.productNotPurchased;
    exceptedResult.setUrl(Constants.ftp.FTP_URL);

    Product result = productService.findProduct(1L);

    Assert.assertEquals(exceptedResult, result);
  }

  @Test
  public void findProduct_successNotPurchased() throws ProductNotFoundException {
    Mockito.when(productRepository.findById(1L))
        .thenReturn(Optional.of(TestObjectFactory.NewProduct.productNotPurchased));

    Product exceptedResult = TestObjectFactory.NewProduct.productNotPurchased;
    exceptedResult.setUrl(Constants.ftp.FTP_PURCHASE);

    Product result = productService.findProduct(1L);

    Assert.assertEquals(exceptedResult, result);
  }

  @Test(expected = ProductNotFoundException.class)
  public void findProduct_exception() throws ProductNotFoundException {
    Mockito.when(productRepository.findById(1L)).thenReturn(Optional.empty());

    Product exceptedResult = TestObjectFactory.NewProduct.productNotPurchased;
    exceptedResult.setUrl(Constants.ftp.FTP_URL);

    Product result = productService.findProduct(1L);

    Assert.assertEquals(exceptedResult, result);
  }

  @Test
  public void findAllProducts_success() {
    List<Product> exceptedResult =
        Lists.newArrayList(
            TestObjectFactory.NewProduct.productPurchased,
            TestObjectFactory.NewProduct.productPurchased);

    Mockito.when(productRepository.findAll()).thenReturn((exceptedResult));

    List<Product> result = (List<Product>) productService.findAll();

    Assert.assertEquals(exceptedResult, result);
  }

  @Test
  public void findAllProducts_emptyList() {
    List<Product> exceptedResult = Lists.newArrayList();

    Mockito.when(productRepository.findAll()).thenReturn((exceptedResult));

    List<Product> result = (List<Product>) productService.findAll();

    Assert.assertEquals(exceptedResult, result);
  }

  @Test
  public void purchaseProduct_success() {
    List<Long> productId = Lists.newArrayList(1L);

    Mockito.when(productRepository.findById(productId.get(0)))
        .thenReturn(Optional.of(TestObjectFactory.NewProduct.productPurchased));

    String exceptedResult = "Order completed";

    String result = productService.purchaseProduct(productId);

    Assert.assertEquals(exceptedResult, result);
  }

  @Test
  public void purchaseProduct_fail() {
    List<Long> productId = Lists.newArrayList(1L);

    Mockito.when(productRepository.findById(productId.get(0))).thenReturn(Optional.empty());

    String exceptedResult = "Purchase failed, no such products available";

    String result = productService.purchaseProduct(productId);

    Assert.assertEquals(exceptedResult, result);
  }

  @Test
  public void findProductByDateLower_success() {
    Mockito.when(productRepository.findAll()).thenReturn(TestObjectFactory.ProductList.datesAll);

    List<Product> result =
        Lists.newArrayList(
            productService.findProductByDateLower(
                LocalDate.parse(TestObjectFactory.Dates.THIRD_DATE)));

    List<Product> exceptedResult = TestObjectFactory.ProductList.datesLower;

    Assert.assertEquals(exceptedResult, result);
  }

  @Test
  public void findProductByDateLower_emptyList() {
    List<Product> mockResult = Lists.newArrayList();

    Mockito.when(productRepository.findAll()).thenReturn(mockResult);

    List<Product> result =
        Lists.newArrayList(
            productService.findProductByDateLower(
                LocalDate.parse(TestObjectFactory.Dates.THIRD_DATE)));

    List<Product> exceptedResult = Lists.newArrayList();

    Assert.assertEquals(exceptedResult, result);
  }

  @Test
  public void findProductByDateGreater_success() {
    Mockito.when(productRepository.findAll()).thenReturn(TestObjectFactory.ProductList.datesAll);

    List<Product> result =
        Lists.newArrayList(
            productService.findProductByDateGreater(
                LocalDate.parse(TestObjectFactory.Dates.THIRD_DATE)));

    List<Product> exceptedResult = TestObjectFactory.ProductList.datesGreater;

    Assert.assertEquals(exceptedResult, result);
  }

  @Test
  public void findProductByDateGreater_emptyList() {
    List<Product> mockResult = Lists.newArrayList();

    Mockito.when(productRepository.findAll()).thenReturn(mockResult);

    List<Product> result =
        Lists.newArrayList(
            productService.findProductByDateGreater(
                LocalDate.parse(TestObjectFactory.Dates.THIRD_DATE)));

    List<Product> exceptedResult = Lists.newArrayList();

    Assert.assertEquals(exceptedResult, result);
  }

  @Test
  public void findProductByDateBetween_success() {
    Mockito.when(productRepository.findAll()).thenReturn(TestObjectFactory.ProductList.datesAll);

    List<Product> result =
        Lists.newArrayList(
            productService.findProductByDateBetween(
                LocalDate.parse(TestObjectFactory.Dates.FIRST_DATE),
                LocalDate.parse(TestObjectFactory.Dates.FIFTH_DATE)));

    List<Product> exceptedResult = TestObjectFactory.ProductList.datesBetween;

    Assert.assertEquals(exceptedResult, result);
  }

  @Test
  public void findProductByDateBetween_emptyList() {
    List<Product> mockResult = Lists.newArrayList();

    Mockito.when(productRepository.findAll()).thenReturn(mockResult);

    List<Product> result =
        Lists.newArrayList(
            productService.findProductByDateBetween(
                LocalDate.parse(TestObjectFactory.Dates.FIRST_DATE),
                LocalDate.parse(TestObjectFactory.Dates.FIFTH_DATE)));

    List<Product> exceptedResult = Lists.newArrayList();

    Assert.assertEquals(exceptedResult, result);
  }

  @Test
  public void findProductByDateBetween_onlyDateLowerThanRangeFound() {
    List<Product> mockResult = Lists.newArrayList(TestObjectFactory.ProductList.productFirst);

    Mockito.when(productRepository.findAll()).thenReturn(mockResult);

    List<Product> result =
        Lists.newArrayList(
            productService.findProductByDateBetween(
                LocalDate.parse(TestObjectFactory.Dates.SECOND_DATE),
                LocalDate.parse(TestObjectFactory.Dates.FIFTH_DATE)));

    List<Product> exceptedResult = Lists.newArrayList();

    Assert.assertEquals(exceptedResult, result);
  }

  @Test
  public void findProductByDateBetween_onlyDateGreaterThanRangeFound() {
    List<Product> mockResult = Lists.newArrayList(TestObjectFactory.ProductList.productFifth);

    Mockito.when(productRepository.findAll()).thenReturn(mockResult);

    List<Product> result =
        Lists.newArrayList(
            productService.findProductByDateBetween(
                LocalDate.parse(TestObjectFactory.Dates.FIRST_DATE),
                LocalDate.parse(TestObjectFactory.Dates.FOURTH_DATE)));

    List<Product> exceptedResult = Lists.newArrayList();

    Assert.assertEquals(exceptedResult, result);
  }

  @Test
  public void findProductByDateBetween_onlyDateFoundIsEqualToLowerRange() {
    List<Product> mockResult = Lists.newArrayList(TestObjectFactory.ProductList.productFirst);

    Mockito.when(productRepository.findAll()).thenReturn(mockResult);

    List<Product> result =
        Lists.newArrayList(
            productService.findProductByDateBetween(
                LocalDate.parse(TestObjectFactory.Dates.FIRST_DATE),
                LocalDate.parse(TestObjectFactory.Dates.FOURTH_DATE)));

    List<Product> exceptedResult = Lists.newArrayList();

    Assert.assertEquals(exceptedResult, result);
  }

  @Test
  public void findProductByDateBetween_onlyDateFoundIsEqualToGreaterRange() {
    List<Product> mockResult = Lists.newArrayList(TestObjectFactory.ProductList.productFifth);

    Mockito.when(productRepository.findAll()).thenReturn(mockResult);

    List<Product> result =
        Lists.newArrayList(
            productService.findProductByDateBetween(
                LocalDate.parse(TestObjectFactory.Dates.FIRST_DATE),
                LocalDate.parse(TestObjectFactory.Dates.FIFTH_DATE)));

    List<Product> exceptedResult = Lists.newArrayList();

    Assert.assertEquals(exceptedResult, result);
  }
}
