package com.abel.encurtador.resources.Util;

import java.net.URI;

public class RemovePrefix {

    // Remove https, http e www
    public static final String transformUrl(String url) {
        try {
            URI uri = new URI(url);
            return uri.getPath();
        } catch (Exception e) {
            return url;
        }
    }
}
