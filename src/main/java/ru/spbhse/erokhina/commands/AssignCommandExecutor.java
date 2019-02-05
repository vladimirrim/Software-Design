package ru.spbhse.erokhina.commands;

import ru.spbhse.erokhina.Environment;

import java.util.ArrayList;
import java.util.List;

/**
 * CommandExecutor for assignment command. Assignment command assigns a value to a variable.
 * Requires two arguments: variable name and value.
 */
public class AssignCommandExecutor implements CommandExecutor {
    @Override
    public void execute(List<String> args, Environment environment) throws RuntimeException {
        switch (args.size()) {
            case 0:
            case 1:
                throw new RuntimeException("Too few parameters for `=` command");
            case 2:
                environment.put(args.get(0), args.get(1));
                environment.setPrevCommandOutputLines(new ArrayList<>());
                break;
            default:
                throw new RuntimeException("Unexpected symbols: " + args.get(2));
        }

    }
}