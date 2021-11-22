package com.nttdata.repository;

import com.nttdata.model.Purchase;

import reactor.core.publisher.Mono;

public interface IPurchaseRepository extends IRepository<Purchase, String> {

	Mono<Purchase> findByCardNumber(String cardNumber);
	
}
