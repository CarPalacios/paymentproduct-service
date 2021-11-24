package com.nttdata.service.impl;

import com.nttdata.model.PaymentProduct;
import com.nttdata.repository.PaymentProductRepository;
import com.nttdata.repository.Repository;
import com.nttdata.service.PaymentProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**Se creo la clase PaymentProductServiceImpl extendido CrudServiceImpl
 *e implementado PaymentProductService.*/
@Service
public class PaymentProductServiceImpl extends 
    CrudServiceImpl<PaymentProduct, String> implements PaymentProductService {

  @Autowired
  private PaymentProductRepository repository;
  
  @Override
  protected Repository<PaymentProduct, String> getRepository() {
    
    return repository;
    
  }
  
}
