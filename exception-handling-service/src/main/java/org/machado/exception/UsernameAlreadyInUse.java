package org.machado.exception;

public class UsernameAlreadyInUse extends Exception{

    public UsernameAlreadyInUse(String errorMessage){
        super(errorMessage);
    }
}
