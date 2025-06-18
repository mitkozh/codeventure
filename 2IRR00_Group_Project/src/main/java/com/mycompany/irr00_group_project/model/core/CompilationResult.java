package com.mycompany.irr00_group_project.model.core;


import java.util.List;
import java.util.Map;

import javax.tools.Diagnostic;
import javax.tools.JavaFileObject;

import com.mycompany.irr00_group_project.service.sandbox.JavaClassAsBytes;

/**
 * Class used to check if the compilation of the class submitted by the user is successful or not.
 */ 
public class CompilationResult {
    private final boolean success;
    private final Map<String, JavaClassAsBytes> compiledClasses;
    private final List<Diagnostic<? extends JavaFileObject>> diagnostics;
    private final String formattedDiagnostics;

    /**
     * method for getting compilation result.
     */
    public CompilationResult(boolean success, Map<String, JavaClassAsBytes>
        compiledClasses, List<Diagnostic<? extends JavaFileObject>> diagnostics,
        String formattedDiagnostics) {
        this.success = success;
        this.compiledClasses = compiledClasses;
        this.diagnostics = diagnostics;
        this.formattedDiagnostics = formattedDiagnostics;
    }

    public boolean isSuccess() {
        return success;
    }

    public Map<String, JavaClassAsBytes> getCompiledClasses() {
        return compiledClasses;
    }

    public List<Diagnostic<? extends JavaFileObject>> getDiagnosticsList() {
        return diagnostics;
    }

    public String getFormattedDiagnostics() {
        return formattedDiagnostics;
    }
}
