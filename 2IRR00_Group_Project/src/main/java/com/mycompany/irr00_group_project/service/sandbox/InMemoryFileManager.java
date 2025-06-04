package com.mycompany.irr00_group_project.service.sandbox;

import java.util.Hashtable;
import java.util.Map;

import javax.tools.FileObject;
import javax.tools.ForwardingJavaFileManager;
import javax.tools.JavaFileManager;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;

/**
 * InMemoryFileManager is a custom Java file manager that stores compiled
 * classes in memory. It extends ForwardingJavaFileManager.
 */
public class InMemoryFileManager extends ForwardingJavaFileManager<JavaFileManager> {

    private Map<String, JavaClassAsBytes> compiledClasses;

    private ClassLoader loader;

    /**
     * Constructor for InMemoryFileManager.
     * @param standardManager the standard Java file manager.
     */
    public InMemoryFileManager(StandardJavaFileManager standardManager) {
        super(standardManager);
        this.compiledClasses = new Hashtable<>();
        this.loader = new InMemoryClassLoader(this.getClass().getClassLoader(), this);
    }

    @Override
    public JavaFileObject getJavaFileForOutput(Location location,
            String className, JavaFileObject.Kind kind, FileObject sibling) {

        JavaClassAsBytes classAsBytes = new JavaClassAsBytes(className, kind);
        compiledClasses.put(className, classAsBytes);

        return classAsBytes;
    }

    public Map<String, JavaClassAsBytes> getBytesMap() {
        return compiledClasses;
    }

    @Override
    public ClassLoader getClassLoader(Location location) {
        return loader;
    }
}