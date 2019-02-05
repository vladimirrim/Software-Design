package ru.spbhse.erokhina.parser;

/**
 * Exception that is thrown when the parser fails.
 */
public class ParserException extends RuntimeException {
    ParserException(String message) {
        super(message);
    }
}