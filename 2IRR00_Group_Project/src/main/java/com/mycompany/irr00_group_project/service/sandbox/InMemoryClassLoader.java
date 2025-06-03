package com.mycompany.irr00_group_project.service.sandbox;

import java.util.Map;

/**
 * InMemoryClassLoader is a custom class loader that loads classes from an
 * in-memory file manager. It extends the ClassLoader class and overrides the
 * findClass method to define classes from byte arrays stored in the
 * InMemoryFileManager.
 */
public class InMemoryClassLoader extends ClassLoader {

    private InMemoryFileManager manager;

    /**
     * Constructor for InMemoryClassLoader.
     * This class loader is used to load classes from an in-memory file manager.
     * 
     * @param parent  the parent class loader
     * @param manager the in-memory file manager that contains the compiled classes
     */
    public InMemoryClassLoader(ClassLoader parent, InMemoryFileManager manager) {
        super(parent);
        this.manager = java.util.Objects.requireNonNull(manager, "manager must not be null");
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {

        Map<String, JavaClassAsBytes> compiledClasses = manager.getBytesMap();

        if (compiledClasses.containsKey(name)) {
            byte[] bytes = compiledClasses.get(name).getBytes();
            return defineClass(name, bytes, 0, bytes.length);
        } else {
            throw new ClassNotFoundException();
        }
    }
}