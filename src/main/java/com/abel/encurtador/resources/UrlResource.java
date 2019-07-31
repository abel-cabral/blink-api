package com.abel.encurtador.resources;

import com.abel.encurtador.Util.URLShortener;
import com.abel.encurtador.domain.Url;
import com.abel.encurtador.services.UrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class UrlResource {
    @Autowired
    private UrlService service;

    @PostMapping("/encurtar/{url}")
    ResponseEntity<Url> encurteUrl(@PathVariable("url") String fullUrl) {
        URLShortener uc;
        Url aux;

        // Verifica se o hash gerado tem colisoes
        do {
            uc = new URLShortener(5, "gool.com/"); // Tamanho e Dominio
            aux = service.findShortUrl(uc.shortenURL(fullUrl));
        } while (aux != null);

        // Se nao houveram colisoes chama o salvar
        aux = service.saveUrl(new Url(null, fullUrl, uc.shortenURL(fullUrl)));
        return ResponseEntity.ok().body(aux);
    }

    @GetMapping("/")
    ResponseEntity<Url> buscarUrl(@RequestBody Url obj) {
        Url aux = service.findShortUrl(obj.getShortUrl());
        return ResponseEntity.ok().body(aux);
    }
}
