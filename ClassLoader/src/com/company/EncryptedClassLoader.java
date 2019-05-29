package com.company;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class EncryptedClassLoader extends ClassLoader {

    private final String key;
    private final File dir;

    public EncryptedClassLoader(String key, File dir, ClassLoader parent) {
        super(parent);
        this.key = key;
        this.dir = dir;
    }

    @Override
    protected Class<?> findClass(String name) {
        int hashKey= key.hashCode();
        Path path = Paths.get(dir.getPath() + "/" + name.replace('.', '/') + ".class");
        byte[] cipher = new byte[0];
        try {
            cipher = Files.readAllBytes(path);

        } catch (IOException e) {
            e.printStackTrace();
        }
        long len = cipher.length;
        for (int i = 0; i < len; i++) {
            cipher[i] = (byte) (cipher[i] + hashKey);
        }
        return defineClass(name, cipher, 0, cipher.length);
    }
}