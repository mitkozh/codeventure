package com.codeventure.service.sandbox;

import com.codeventure.model.core.Character;

/**
 * UserCode is a functional interface that defines a method to execute
 * user-defined code on a Character object. It is used in the sandbox
 * environment.
 */
public interface UserCode {
    void execute(Character character);
}
