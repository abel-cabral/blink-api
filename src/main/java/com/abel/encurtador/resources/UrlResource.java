package com.abel.encurtador.resources;

import com.abel.encurtador.Util.URLShortener;
import com.abel.encurtador.domain.Url;
import com.abel.encurtador.services.UrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@RestController
public class UrlResource {
    @Autowired
    private UrlService service;

    @PostMapping("/api/encurtador")
    ResponseEntity<Url> encurteUrl(@RequestBody Url obj) {
        URLShortener uc = new URLShortener(5, "https://rxls.herokuapp.com/"); // Tamanho e Dominio
        Url aux;

        // Garante que a url longa terá http ou https
        obj.setLongUrl(uc.sanitizeURL(obj.getLongUrl()));
        // Verifica se o hash gerado tem colisoes
        do {
            obj.setShortUrl(uc.shortenURL(obj.getLongUrl()));
            aux = service.findShortUrl(obj.getShortUrl());
        } while (aux != null);

        // Verifica se o retorno da verificacao de URL é valido
        if(obj.getShortUrl() == "") {
            return ResponseEntity.badRequest().build();
        }
        // Se nao houveram colisoes chama o salvar
        aux = service.saveUrl(obj);
        return ResponseEntity.ok().body(aux);
    }

    @GetMapping("/{code}")
    public void buscarUrl(@PathVariable("code") String code, HttpServletRequest request, HttpServletResponse response) {
        Url aux = service.findShortUrl(code);
        try {
            if (aux != null ) {
                response.sendRedirect(aux.getLongUrl());
            }
        } catch (IOException e) {
            ResponseEntity.badRequest().body("Error ao consultar");
        }
    }
}
