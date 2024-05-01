// Código parcialmente copiado do Stack Overflow, mas foi adaptado e reduzido
package com.abel.encurtador.Util;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.HashMap;
import java.util.Random;

public class URLShortener {
    // Armazenamento para chaves geradas
    private HashMap<String, String> keyMap; // Mapa de chave-url
    private HashMap<String, String> valueMap; // Mapa de url-chave para verificar rapidamente
    // se uma url já foi inserida no nosso sistema
    private String domain; // Use este atributo para gerar URLs para um nome de domínio personalizado
    private char myChars[]; // Este array é usado para mapeamento de caracteres para números
    private Random myRand; // Objeto Random usado para gerar números inteiros aleatórios
    private int keyLength; // O comprimento da chave na URL

    // Construtor que permite definir o comprimento da chave da URL pequena e o nome do domínio base
    public URLShortener(int length, String newDomain) {
        this.keyLength = length;
        this.domain = newDomain;
        this.keyMap = new HashMap<String, String>();
        this.valueMap = new HashMap<String, String>();
        this.myRand = new Random();
        this.myChars = new char[62];
        for (int i = 0; i < 62; i++) {
            int j = 0;
            if (i < 10) {
                j = i + 48;
            } else if (i > 9 && i <= 35) {
                j = i + 55;
            } else {
                j = i + 61;
            }
            this.myChars[i] = (char) j;
        }
    }

    // Método para encurtar URL que verifica se é um link válido e lança exceção caso não seja
    public String shortenURL(String longURL) throws MalformedURLException, URISyntaxException {
        String shortURL = "";
        new URL(longURL).toURI(); // Valida a URL
        shortURL = domain + "/" + getKey(longURL);
        return shortURL;
    }

    // Método público que retorna a URL original dado a URL encurtada
    public String expandURL(String shortURL) {
        String longURL = "";
        String key = shortURL.substring(domain.length() + 1);
        longURL = keyMap.get(key);
        return longURL;
    }

    // Método para obter a chave
    private String getKey(String longURL) {
        String key;
        key = generateKey();
        keyMap.put(key, longURL);
        valueMap.put(longURL, key);
        return key;
    }

    // Método para gerar a chave
    private String generateKey() {
        String key = "";
        boolean flag = true;
        while (flag) {
            key = "";
            for (int i = 0; i <= keyLength; i++) {
                key += myChars[myRand.nextInt(62)];
            }
            if (!keyMap.containsKey(key)) {
                flag = false;
            }
        }
        return key;
    }
}
