package ru.spbhse.erokhina;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Class which contains existing variables with their values and the result of executing the last command (before the
 * last pipe).
 */
public class Environment {
    private final Map<String, String> map;
    private List<String> prevCommandOutputLines = new ArrayList<>();
    private boolean exitFlag = false;

    public Environment () {
        map = new HashMap<>();
    }

    /**
     * Puts a new variable.
     * @param key the name of variable
     * @param value the value of variable
     */
    public void put(String key, String value) {
        map.put(key, value);
    }

    /**
     * Gets the value of the variable or returns default value (if variable does not exist).
     * @param key the name of variable
     * @param defaultValue defaukt value
     * @return the value of the variable or returns default value
     */
    public String getOrDefault(String key, String defaultValue) {
        return map.getOrDefault(key, defaultValue);
    }

    public List<String> getPrevCommandOutputLines() {
        return prevCommandOutputLines;
    }

    public void setPrevCommandOutputLines(List<String> list) {
        prevCommandOutputLines = list;
    }

    public void setExitFlag(boolean val) {
        exitFlag = val;
    }

    public boolean getExitFlag() {
        return exitFlag;
    }

    /**
     * Reset environment for a new command from user.
     */
    public void resetForNewCommand() {
        setPrevCommandOutputLines(new ArrayList<>());
        setExitFlag(false);
    }
}
