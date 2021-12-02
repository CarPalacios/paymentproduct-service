package com.nttdata.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.reactive.ReactiveSecurityAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.nttdata.model.PaymentProduct;
import com.nttdata.repository.PaymentProductRepository;
import com.nttdata.service.impl.PaymentProductServiceImpl;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Import(PaymentProductServiceImpl.class)
@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = PaymentProductController.class, excludeAutoConfiguration = {ReactiveSecurityAutoConfiguration.class})
class PaymentProductControllerTest {
  
  @Autowired
  private WebTestClient paymentproduct;
  
  @MockBean
  private PaymentProductRepository repository;

  @Test
  void testFindAll() {
    
    PaymentProduct paymentproducts = new PaymentProduct("1","45518052352497921",180.0,"Primer pago de producto",LocalDateTime.now());
    List<PaymentProduct> list = new ArrayList<>();
    list.add(paymentproducts);
    Flux<PaymentProduct> fluxpaymentproduct = Flux.fromIterable(list);
    
    Mockito.when(repository.findAll()).thenReturn(fluxpaymentproduct);
    paymentproduct.get().uri("/payment").accept(MediaType.APPLICATION_JSON)
    .exchange().expectStatus().isOk().expectHeader().contentType(MediaType.APPLICATION_JSON)
    .expectBodyList(PaymentProduct.class).hasSize(1);    
    
  }

  @Test
  void testFindById() {
    
    PaymentProduct paymentproducts = new PaymentProduct("1","45518052352497921",180.0,"Primer pago de producto",LocalDateTime.now());
    
    Mockito.when(repository.findById("1")).thenReturn(Mono.just(paymentproducts));
    paymentproduct.get().uri("/payment/1")
    .accept(MediaType.APPLICATION_JSON).exchange()
    .expectStatus().isOk()
    .expectHeader().contentType(MediaType.APPLICATION_JSON)
    .expectBody()
    .jsonPath("$.id").isNotEmpty()
    .jsonPath("$.cardNumber").isEqualTo("45518052352497921")
    .jsonPath("$.amount").isEqualTo(180.0)
    .jsonPath("$.description").isEqualTo("Primer pago de producto");
        
  }

  @Test
  void testCreate() {
    
    PaymentProduct paymentproducts = new PaymentProduct("2","45518052352497032",230.0,"Segundo pago de producto",LocalDateTime.now());
    
    Mockito.when(repository.save(ArgumentMatchers.any())).thenReturn(Mono.just(paymentproducts));
    paymentproduct.post().uri("/payment")
    .contentType(MediaType.APPLICATION_JSON)
    .accept(MediaType.APPLICATION_JSON)
    .body(Mono.just(paymentproducts), PaymentProduct.class)
    .exchange()
    .expectStatus().isCreated()
    .expectHeader().contentType(MediaType.APPLICATION_JSON)
    .expectBody()
    .jsonPath("$.id").isNotEmpty()
    .jsonPath("$.cardNumber").isEqualTo("45518052352497032")
    .jsonPath("$.amount").isEqualTo(230.0)
    .jsonPath("$.description").isEqualTo("Segundo pago de producto");
    
  }

}
