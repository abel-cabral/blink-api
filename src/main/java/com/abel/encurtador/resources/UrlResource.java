package com.abel.encurtador.resources;

import com.abel.encurtador.Util.URLShortener;
import com.abel.encurtador.domain.Url;
import com.abel.encurtador.services.UrlService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@RestController
public class UrlResource {
    @Autowired
    private UrlService service;

    @PostMapping("/encurtador/{url}")
    ResponseEntity<Url> encurteUrl(@PathVariable("url") String fullUrl) {
        URLShortener uc = new URLShortener(5, "https://encurtadorx.herokuapp.com/"); // Tamanho e Dominio
        String shortUrl;
        Url aux;

        // Verifica se o hash gerado tem colisoes
        do {
            shortUrl = uc.shortenURL(fullUrl);
            aux = service.findShortUrl(uc.shortenURL(fullUrl));
        } while (aux != null);

        // Se nao houveram colisoes chama o salvar
        aux = service.saveUrl(new Url(null, fullUrl, shortUrl));
        return ResponseEntity.ok().body(aux);
    }

    @GetMapping("/{code}")
    public void buscarUrl(@PathVariable("code") String code, HttpServletRequest request, HttpServletResponse response) {
        Url aux = service.findShortUrl(code);
        try {
            if (aux != null ) {
                response.sendRedirect("http://" + aux.getLongUrl());
            }
        } catch (IOException e) {
            ResponseEntity.badRequest().body("Error ao consultar");
        }
    }
}
