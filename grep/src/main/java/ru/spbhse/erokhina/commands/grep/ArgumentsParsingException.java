package ru.spbhse.erokhina.commands.grep;

/**
 * Exceptions for command line args parsers
 */
public class ArgumentsParsingException extends Exception {
    public ArgumentsParsingException(Exception e) {
        super(e);
    }
}