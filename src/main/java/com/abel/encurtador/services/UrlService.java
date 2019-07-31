package com.abel.encurtador.services;

import com.abel.encurtador.domain.Url;
import com.abel.encurtador.repository.UrlRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UrlService {
    @Autowired
    private UrlRepository repo;

    public Url saveUrl(Url obj) {
        // Verifica se a URL completa já esta no BD,
        Url aux = findLongUrl(obj.getLongUrl());
        if (aux == null) {
            // Não havendo registro inserimos o novo
            return repo.insert(obj);
        }
        // Havendo registro devolvemos a consulta
        return aux;
    }

    public Url findShortUrl(String text) {
        return repo.findByShortUrlContaining(text);
    }

    public Url findLongUrl(String text) {
        return repo.findByLongUrlContaining(text);
    }
}
