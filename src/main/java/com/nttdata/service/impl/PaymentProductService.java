package com.nttdata.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.nttdata.model.PaymentProduct;
import com.nttdata.repository.IPaymentProductRepository;
import com.nttdata.repository.IRepository;
import com.nttdata.service.IPaymentProductService;

@Service
public class PaymentProductService extends CRUDServiceImpl<PaymentProduct, String> implements IPaymentProductService {

	@Autowired
	private IPaymentProductRepository repository;
	
	@Override
	protected IRepository<PaymentProduct, String> getRepository() {
		
		return repository;
		
	}
	
}
