package ru.spbhse.erokhina.cli;

import ru.spbhse.erokhina.commands.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Pool of commands for CLI. Maps name of commands to their executors.
 */
class CommandsPool {
    private final Map<String, CommandExecutor> commands = new HashMap<>();
    private ExternalCommandExecutor externalCommandExecutor = new ExternalCommandExecutor();

    {
        commands.put("cat", new CatCommandExecutor());
        commands.put("echo", new EchoCommandExecutor());
        commands.put("wc", new WcCommandExecutor());
        commands.put("pwd", new PwdCommandExecutor());
        commands.put("exit", new ExitCommandExecutor());
        commands.put("assign", new AssignCommandExecutor());
    }

    /**
     * Returns CommandExecutor for requested command name.
     * @param commandName requested command name
     * @return CommandExecutor for requested command name
     */
    CommandExecutor getCommandExecutor(String commandName) {
        CommandExecutor commandExecutor;

        if (!commands.containsKey(commandName)) {
            externalCommandExecutor.setCommandName(commandName);
            commandExecutor = externalCommandExecutor;
        } else {
            commandExecutor = commands.get(commandName);
        }

        return commandExecutor;
    }
}
