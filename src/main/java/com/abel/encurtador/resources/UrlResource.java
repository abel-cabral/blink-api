package com.abel.encurtador.resources;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.abel.encurtador.Util.URLShortener;
import com.abel.encurtador.domain.Url;
import com.abel.encurtador.services.UrlService;

@RestController
public class UrlResource {
    @Autowired
    private UrlService service;

    @PostMapping("/api/encurtador")
    ResponseEntity<Object> encurteUrl(@RequestBody Url obj) {
        URLShortener uc = new URLShortener(4, "blk.abelcode.dev"); // Tamanho e Dominio
        Url aux;

        // Garante que a url longa terá http ou https
        obj.setLongUrl(obj.getLongUrl());

        do {
            try {
                obj.setShortUrl(uc.shortenURL(obj.getLongUrl()));
                aux = service.findShortUrl(obj.getShortUrl());
            } catch (MalformedURLException | URISyntaxException e) {
                return ResponseEntity.badRequest().body("Link informado não é válido.");
            } catch (Exception e) {
                return ResponseEntity.badRequest().body(e.getMessage());
            }

        } while (aux != null);

        // Se nao houveram colisoes chama o salvar
        try {
            aux = service.saveUrl(obj);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

        return ResponseEntity.ok().body(aux);
    }

    @GetMapping("/{code}")
    public ResponseEntity<Object> buscarUrl(@PathVariable("code") String code, HttpServletRequest request,
            HttpServletResponse response) {
        try {
            Url aux = service.findShortUrl(code);
            if (aux != null) {
                response.sendRedirect(aux.getLongUrl());
            }
            return ResponseEntity.status(404).body(
                    "<h1 style=\"position: absolute; top: 50%; left: 50%; transform: translate(-50%, -50%); text-align: center;\">Ops esse link não existe</h1>");
        } catch (IOException e) {
            return ResponseEntity.badRequest().body("Error ao consultar");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
