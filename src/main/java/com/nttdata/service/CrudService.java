package com.nttdata.service;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**Se crea la interfaz CrudService, donde se indican los metodos a realizar.*/
public interface CrudService<T, K> {

  Mono<T> create(T o);
  
  Flux<T> findAll();
  
  Mono<T> findById(K id);
  
  Mono<T> update(T o);
  
  Mono<Void> delete(K id);
  
}