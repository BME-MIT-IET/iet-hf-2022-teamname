package junit;

import InputTranslator.Token;
import InputTranslator.TokenTypes;
import InputTranslator.Tokenizer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import Exception.InputErrorException;

import java.util.List;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

public class StringToTokenTest {

    @Test
    @DisplayName("Turns simple string command: 'e 100' into tokens")
    public void single_operation_to_tokens() throws InputErrorException {
        List<Token> expectedTokens= List.of(
                new Token(TokenTypes.STRING, "e"),
                new Token(TokenTypes.NUMBER, "100")
        );
        List<Token> tokens = new Tokenizer("e 100").getTokens();
        assertArrayEquals(expectedTokens.toArray(),tokens.toArray());
    }

    @Test
    @DisplayName("Multiple commands in one line")
    public void multiple_operation_in_one_line_to_tokens() throws InputErrorException {
        List<Token> expectedTokens= List.of(
                new Token(TokenTypes.STRING, "e"),
                new Token(TokenTypes.NUMBER, "100"),
                new Token(TokenTypes.STRING, "j"),
                new Token(TokenTypes.NUMBER, "90")
        );
        List<Token> tokens = new Tokenizer("e 100j90").getTokens();
        assertArrayEquals(expectedTokens.toArray(),tokens.toArray());
    }
    @Test
    @DisplayName("Parse variable name")
    public void parse_varable_name_to_tokens() throws InputErrorException {
        List<Token> expectedTokens= List.of(
                new Token(TokenTypes.VARIABLE, "testVariable31")
        );
        List<Token> tokens = new Tokenizer("$testVariable31").getTokens();
        assertArrayEquals(expectedTokens.toArray(),tokens.toArray());
    }

    @Test
    @DisplayName("Incomplete parentheses")
    public void testIncompleteInput(){

        Exception exception = assertThrows(InputErrorException.class, () -> {
            new Tokenizer("e (2+2").getTokens();
        });

        String expectedMessage = "Incomplete ()";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
    @Test
    @DisplayName("Incomplete curly parentheses")
    public void testIncompleteCurlyInput(){

        Exception exception = assertThrows(InputErrorException.class, () -> {
            new Tokenizer("loop 4 {loop 3 {e 20}").getTokens();
        });

        String expectedMessage = "Incomplete {}";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }

}
