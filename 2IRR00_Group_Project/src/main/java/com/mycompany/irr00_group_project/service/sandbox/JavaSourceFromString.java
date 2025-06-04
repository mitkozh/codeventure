package com.mycompany.irr00_group_project.service.sandbox;

import javax.tools.SimpleJavaFileObject;
import java.net.URI;

/**
 * JavaSourceFromString is a custom Java file object that represents a source
 * code in memory. It extends SimpleJavaFileObject and provides the source code
 * as a CharSequence.
 */
public class JavaSourceFromString extends SimpleJavaFileObject {

    private String sourceCode;

    /**
     * Constructor for JavaSourceFromString.
     * @param name       the name of the source file ("com.example.MyClass")
     * @param sourceCode the source code as a String
     */
    public JavaSourceFromString(String name, String sourceCode) {
        super(URI.create("string:///" + name.replace('.', '/') + Kind.SOURCE.extension),
                Kind.SOURCE);
        this.sourceCode = java.util.Objects
                .requireNonNull(sourceCode, "sourceCode must not be null");
    }

    @Override
    public CharSequence getCharContent(boolean ignoreEncodingErrors) {
        return sourceCode;
    }
}