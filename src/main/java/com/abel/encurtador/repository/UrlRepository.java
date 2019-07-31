package com.abel.encurtador.repository;

import com.abel.encurtador.domain.Url;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UrlRepository extends MongoRepository<Url, String> { // Espera receber a classe que ira gerenciar e qual é o tipo do Id da classe

}

