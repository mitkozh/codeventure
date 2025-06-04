package com.mycompany.irr00_group_project.service.sandbox;

import com.mycompany.irr00_group_project.model.core.Character;
import com.mycompany.irr00_group_project.model.core.CharacterProxy;

import java.io.File;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLClassLoader;

/**
 * Class used to run the code submitted by the user.
 */
public class UserCodeRunner {
    public static void main(String[] args) {
        if (args.length < 2) {
            System.err.println("Usage: UserCodeRunner <compiled_user_code_path> <user_class_name>");
            System.exit(1);
        }

        String compiledUserCodePath = args[0];
        String userClassName = args[1];

        try (PrintWriter commandSender = new PrintWriter(System.out, true)) {
            Character characterProxy = new CharacterProxy(commandSender);
            File compiledCodeDir = new File(compiledUserCodePath);
            URL[] urls = { compiledCodeDir.toURI().toURL() };
            URLClassLoader classLoader = new URLClassLoader(
                    urls, UserCodeRunner.class.getClassLoader());

            Class<?> userClass = Class.forName(userClassName, true, classLoader);
            Object userInstance = userClass.getDeclaredConstructor().newInstance();

            if (userInstance instanceof UserCode) {
                ((UserCode) userInstance).execute(characterProxy);
            } else {
                System.err.println("User class " + userClassName
                        + " does not implement UserCode.");
            }

        } catch (Exception e) {
            e.printStackTrace(System.err);
        }
    }

}
