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
     * @param name the name of the class ("package.ClassName")
     * @param kind the kind of the Java file object (Kind.CLASS)
     */
    public JavaClassAsBytes(String name, Kind kind) {
        super(URI.create("string:///" + name.replace('.', '/')
                + kind.extension), kind);
    }

    /**
     * Returns the byte array containing the compiled class bytes.
     *
     * @return a byte array of the compiled class
     */
    public byte[] getBytes() {
        return bos.toByteArray();
    }

    /**
     * Opens an output stream to write the compiled class bytes.
     *
     * @return an OutputStream to write the class bytes
     */
    @Override
    public OutputStream openOutputStream() {
        return bos;
    }
}