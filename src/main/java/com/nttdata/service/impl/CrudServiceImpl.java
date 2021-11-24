package com.nttdata.service.impl;

import com.nttdata.repository.Repository;
import com.nttdata.service.CrudService;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**Se crea la clase CrudServiceImpl implementado a CrudService.*/
public abstract class CrudServiceImpl<T, K> implements CrudService<T, K> {

  protected abstract Repository<T, K> getRepository();
  
  @Override
  public Mono<T> create(T o) {
    
    return getRepository().save(o);
    
  }

  @Override
  public Flux<T> findAll() {
    
    return getRepository().findAll();
    
  }

  @Override
  public Mono<T> findById(K id) {
    
    return getRepository().findById(id);
    
  }

  @Override
  public Mono<T> update(T o) {

    return getRepository().save(o);
    
  }

  @Override
  public Mono<Void> delete(K id) {
    
    return getRepository().deleteById(id);
    
  }

}
