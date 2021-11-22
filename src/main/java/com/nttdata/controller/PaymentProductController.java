package com.nttdata.controller;

import java.util.List;

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

import com.nttdata.dto.Response;
import com.nttdata.model.PaymentProduct;
import com.nttdata.service.IPaymentProductService;
import com.nttdata.service.IPurchaseService;

import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/payment")
public class PaymentProductController {
	
	@Autowired
	private IPaymentProductService service;
	
	@Autowired
	private IPurchaseService purchaseService;
	

	@GetMapping
	public Mono<ResponseEntity<List<PaymentProduct>>> findAll() {
		
		return service.findAll()
				.collectList()
				.flatMap(list -> {
					return list.size() > 0 ?
							Mono.just(ResponseEntity
									.ok()
									.contentType(MediaType.APPLICATION_JSON)
									.body(list)) :
							Mono.just(ResponseEntity
									.noContent()
									.build());
				});
		
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
	public Mono<ResponseEntity<Response>> create(@RequestBody PaymentProduct creditPayment, final ServerHttpRequest request) {
		
		return purchaseService.findByCardNumber(creditPayment.getPurchase().getCardNumber())
				.flatMap(purchase -> {
					if(creditPayment.getAmount() < purchase.getAmount()) {
						return Mono.just(ResponseEntity
								.badRequest()
								.body(Response
										.builder()
										.error("El monto a pagar es : " + purchase.getAmount())
										.build()));
					}
					purchase.setAmount(purchase.getAmount() - creditPayment.getAmount());
					creditPayment.setPurchase(purchase);
					return service.create(creditPayment)
							.flatMap(created -> {
								return Mono.just(ResponseEntity
										.ok()
										.contentType(MediaType.APPLICATION_JSON)
										.body(Response
												.builder()
												.data(creditPayment)
												.build()));
							});
				})
				.defaultIfEmpty(ResponseEntity
						.badRequest()
						.body(Response
								.builder()
								.error("No es posible realizar el pago, la tarjeta no existe")
								.build()));
		
	}

}
