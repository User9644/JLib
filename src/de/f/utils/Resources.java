package de.f.utils;

import java.io.IOException;
import java.io.InputStream;

public class Resources {
    public static String getFileAsString(String path) {
        try(InputStream in = Resources.class.getResourceAsStream(path)) {
            byte[] bytes = in.readAllBytes();
            return new String(bytes);
        } catch (IOException | NullPointerException e) {
            e.printStackTrace();
        }

        return null;
    }
}