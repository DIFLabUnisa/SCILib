package it.unisa.di.dif.utils;

import java.io.File;

public class Constant {

    private static Constant instance;

    private Constant() {}

    public static Constant getInstance() {
        if(instance == null) {
            instance = new Constant();
        }
        return instance;
    }

    public String getAppdir() {
        // TODO To be implemented
        return null;
    }

    public boolean isWriteMessageLogOnConsole() {
        // TODO To be implemented
        return false;
    }

    public String getStringLogName() {
        // TODO To be implemented
        return null;
    }
}
