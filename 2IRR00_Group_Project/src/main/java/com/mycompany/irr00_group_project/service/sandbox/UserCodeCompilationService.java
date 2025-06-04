package com.mycompany.irr00_group_project.service.sandbox;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.tools.Diagnostic;
import javax.tools.DiagnosticCollector;
import javax.tools.JavaCompiler;
import javax.tools.JavaFileObject;
import javax.tools.StandardJavaFileManager;
import javax.tools.ToolProvider;

import com.mycompany.irr00_group_project.model.core.CompilationResult;
import com.mycompany.irr00_group_project.utils.Constants;

/**
 * Class dedicated for compilation of user code.
 */
public class UserCodeCompilationService {

    /**
     * Method for getting compilation result.
     */
    public CompilationResult compile(String userCode, String sharedJarPath) {
        JavaCompiler compiler = ToolProvider.getSystemJavaCompiler();
        if (compiler == null) {
            return new CompilationResult(false, null, null, "JDK not found. Run with a JDK.");
        }

        DiagnosticCollector<JavaFileObject> diagnosticsCollector = new DiagnosticCollector<>();
        StandardJavaFileManager standardFileManager = compiler.getStandardFileManager(
                diagnosticsCollector, null, null);
        InMemoryFileManager fileManager = new InMemoryFileManager(standardFileManager);

        JavaFileObject sourceFile = new JavaSourceFromString(Constants.USER_CODE_FQN, userCode);

        String compilationClasspath = System.getProperty("java.class.path") + File.pathSeparator 
            + sharedJarPath;
        List<String> compilerOptions = Arrays.asList("-classpath", compilationClasspath, 
            "-Xlint:all");

        JavaCompiler.CompilationTask task = compiler.getTask(null, fileManager, 
            diagnosticsCollector, compilerOptions, null, Collections.singletonList(sourceFile));
        boolean success = task.call();

        String formattedDiagnostics = "";
        if (!diagnosticsCollector.getDiagnostics().isEmpty()) {
            StringBuilder sb = new StringBuilder();
            for (Diagnostic<? extends JavaFileObject> diagnostic : 
                diagnosticsCollector.getDiagnostics()) {
                sb.append(String.format("Line %d: %s\n", diagnostic.getLineNumber(), 
                    diagnostic.getMessage(null)));
            }
            formattedDiagnostics = sb.toString();
        }

        if (!success) {
            return new CompilationResult(false, null, 
                diagnosticsCollector.getDiagnostics(), "Compilation failed:\n" 
                    + formattedDiagnostics);
        }

        Map<String, JavaClassAsBytes> compiledClasses = fileManager.getBytesMap();
        return new CompilationResult(true, compiledClasses, diagnosticsCollector.getDiagnostics(), 
            "Compilation successful.\n" + formattedDiagnostics);
    }
}
