package ru.spbhse.erokhina.commands;

import ru.spbhse.erokhina.Environment;

import java.util.List;

import static java.util.Collections.singletonList;

/**
 * CommandExecutor for pwd command. Pwd command prints current user directory.
 */
public class PwdCommandExecutor implements CommandExecutor {
    @Override
    public void execute(List<String> args, Environment environment) {
        environment.setPrevCommandOutputLines(singletonList(System.getProperty("user.dir")));
    }
}