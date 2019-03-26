package ru.spbhse.erokhina.parser;

import ru.spbhse.erokhina.Environment;

import java.util.List;

/**
 * Interface for parser.
 */
public interface Parser {
    /**
     * Parses given string and transforms it to the list of ParsedCommand.
     *
     * @param str given string
     * @param environment given environment
     * @return list of ParsedCommand
     */
    List<ParsedCommand> parse(String str, Environment environment);
}
