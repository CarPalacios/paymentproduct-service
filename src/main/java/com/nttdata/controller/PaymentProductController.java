package com.nttdata.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nttdata.model.PaymentProduct;
import com.nttdata.service.IPaymentProductService;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/payment")
public class PaymentProductController {
	
	@Autowired
	private IPaymentProductService service;
		
	
	@GetMapping
	public Mono<ResponseEntity<Flux<PaymentProduct>>> findAll() {
		
		Flux<PaymentProduct> paymentproduct = service.findAll();
		
		return Mono.just(ResponseEntity
				.ok()
				.contentType(MediaType.APPLICATION_JSON)
				.body(paymentproduct));							
		
	}
	
	@GetMapping("/{id}")
	public Mono<ResponseEntity<PaymentProduct>> findById(@PathVariable("id") String id) {
		
		return service.findById(id)
				.map(objectFound -> ResponseEntity
						.ok()
						.contentType(MediaType.APPLICATION_JSON)
						.body(objectFound))
				.defaultIfEmpty(ResponseEntity
						.noContent()
						.build());
		
	}
	
	@PostMapping
	public Mono<ResponseEntity<PaymentProduct>> create(@RequestBody PaymentProduct paymentproduct, final ServerHttpRequest request) {
		
		return service.create(paymentproduct)
				.map(c -> ResponseEntity
						.created(URI.create(request.getURI().toString().concat("/").concat(c.getId())))
						.contentType(MediaType.APPLICATION_JSON)
						.body(c));
	}

}
