package com.mycompany.irr00_group_project.service.sandbox;

import javax.tools.SimpleJavaFileObject;
import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.net.URI;

/**
 * JavaClassAsBytes is a custom Java file object that represents a compiled Java
 * class in memory. It extends SimpleJavaFileObject and provides an output
 * stream to write the class bytes.
 */
public class JavaClassAsBytes extends SimpleJavaFileObject {

    protected ByteArrayOutputStream bos = new ByteArrayOutputStream();

    /**
     * Constructor for JavaClassAsBytes.
     * 
     * @param name the name of the class ("package.ClassName")
     * @param kind the kind of the Java file object (Kind.CLASS)
     */
    public JavaClassAsBytes(String name, Kind kind) {
        super(URI.create("string:///" + name.replace('.', '/')
                + kind.extension), kind);
    }

    public byte[] getBytes() {
        return bos.toByteArray();
    }

    @Override
    public OutputStream openOutputStream() {
        return bos;
    }
}