package junit;

import InputTranslator.ShuntingYardParser;
import InputTranslator.Token;
import InputTranslator.TokenTypes;

import org.junit.jupiter.api.DisplayName;
import Exception.InputErrorException;
import org.junit.jupiter.api.Test;


import java.util.List;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class ShuntingYardParserTest {

    ShuntingYardParser parser= new ShuntingYardParser();



    @Test
    @DisplayName("Simple addition should work")
    public void testSimpleAddition() throws InputErrorException {
        List<Token> tokens= List.of(
                new Token(TokenTypes.NUMBER, "3"),
                new Token(TokenTypes.OPERATOR, "+"),
                new Token(TokenTypes.NUMBER, "3")
                );
        assertEquals(6.0,parser.convertTokenToAST(tokens).evaluate());
    }

    @Test
    @DisplayName("Simple substraction should work")
    public void testSimpleSubstraction() throws InputErrorException {
        List<Token> tokens= List.of(
                new Token(TokenTypes.NUMBER, "3"),
                new Token(TokenTypes.OPERATOR, "-"),
                new Token(TokenTypes.NUMBER, "3")
        );
        assertEquals(0.0,parser.convertTokenToAST(tokens).evaluate());
    }
    @Test
    @DisplayName("Simple multiplication should work")
    public void testSimpleMultiplication() throws InputErrorException {
        List<Token> tokens= List.of(
                new Token(TokenTypes.NUMBER, "3"),
                new Token(TokenTypes.OPERATOR, "*"),
                new Token(TokenTypes.NUMBER, "3")
        );
        assertEquals(9.0,parser.convertTokenToAST(tokens).evaluate());
    }
    @Test
    @DisplayName("Simple division should work")
    public void testSimpleDivision() throws InputErrorException {
        List<Token> tokens= List.of(
                new Token(TokenTypes.NUMBER, "9"),
                new Token(TokenTypes.OPERATOR, "/"),
                new Token(TokenTypes.NUMBER, "3")
        );
        assertEquals(3.0,parser.convertTokenToAST(tokens).evaluate());
    }
    @Test
    @DisplayName("Simple to power should work")
    public void testSimpleToPower() throws InputErrorException {
        List<Token> tokens= List.of(
                new Token(TokenTypes.NUMBER, "2"),
                new Token(TokenTypes.OPERATOR, "^"),
                new Token(TokenTypes.NUMBER, "8")
        );
        assertEquals(256.0,parser.convertTokenToAST(tokens).evaluate());
    }



    @Test
    @DisplayName("Multiple operations of the same type")
    public void chainAdditions() throws InputErrorException {
        List<Token> tokens= List.of(
                new Token(TokenTypes.NUMBER, "2"),
                new Token(TokenTypes.OPERATOR, "+"),
                new Token(TokenTypes.NUMBER, "8"),
                new Token(TokenTypes.OPERATOR, "+"),
                new Token(TokenTypes.NUMBER, "8")
                );
        assertEquals(18.0,parser.convertTokenToAST(tokens).evaluate());
    }
    @Test
    @DisplayName("Incomplete calculation should throw InputErrorException exception")
    public void testIncompleteInput(){
        List<Token> tokens= List.of(
                new Token(TokenTypes.NUMBER, "2"),
                new Token(TokenTypes.OPERATOR, "+")
        );

        Exception exception = assertThrows(InputErrorException.class, () -> {
            parser.convertTokenToAST(tokens).evaluate();
        });

        String expectedMessage = "Number missing!";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));
    }
    @Test
    @DisplayName("Natural operation order correct")
    public void operationOrderCorrect() throws InputErrorException{
        List<Token> tokensWithMultiplication= List.of(
                new Token(TokenTypes.NUMBER, "2"),
                new Token(TokenTypes.OPERATOR, "+"),
                new Token(TokenTypes.NUMBER, "8"),
                new Token(TokenTypes.OPERATOR, "*"),
                new Token(TokenTypes.NUMBER, "8")
        );
        List<Token> tokensWithDivision= List.of(
                new Token(TokenTypes.NUMBER, "2"),
                new Token(TokenTypes.OPERATOR, "-"),
                new Token(TokenTypes.NUMBER, "8"),
                new Token(TokenTypes.OPERATOR, "/"),
                new Token(TokenTypes.NUMBER, "8")
        );
        List<Token> tokensWithPowerToOperation= List.of(
                new Token(TokenTypes.NUMBER, "2"),
                new Token(TokenTypes.OPERATOR, "^"),
                new Token(TokenTypes.NUMBER, "8"),
                new Token(TokenTypes.OPERATOR, "/"),
                new Token(TokenTypes.NUMBER, "8")
        );

        assertEquals(66.0,parser.convertTokenToAST(tokensWithMultiplication).evaluate());
        assertEquals(1.0,parser.convertTokenToAST(tokensWithDivision).evaluate());
        assertEquals(32.0,parser.convertTokenToAST(tokensWithPowerToOperation).evaluate());
    }

    @Test
    @DisplayName("Natural operation order functions")
    public void operationOrderWithParentheses() throws InputErrorException{
        List<Token> tokensWithMultiplication= List.of(
                new Token(TokenTypes.LPARAM,""),
                new Token(TokenTypes.NUMBER, "2"),
                new Token(TokenTypes.OPERATOR, "+"),
                new Token(TokenTypes.NUMBER, "8"),
                new Token(TokenTypes.RPARAM,""),
                new Token(TokenTypes.OPERATOR, "*"),
                new Token(TokenTypes.NUMBER, "8")
        );
        assertEquals(parser.convertTokenToAST(tokensWithMultiplication).evaluate(),80.0);
    }

    @Test
    @DisplayName("Multiple parentheses should work")
    public void operationOrderWithMultipleParentheses() throws InputErrorException{
        List<Token> tokens= List.of(
                new Token(TokenTypes.LPARAM,""),
                new Token(TokenTypes.NUMBER, "2"),
                new Token(TokenTypes.OPERATOR, "+"),
                new Token(TokenTypes.NUMBER, "8"),
                new Token(TokenTypes.RPARAM,""),
                new Token(TokenTypes.OPERATOR, "*"),
                new Token(TokenTypes.LPARAM,""),
                new Token(TokenTypes.NUMBER, "2"),
                new Token(TokenTypes.OPERATOR, "+"),
                new Token(TokenTypes.NUMBER, "8"),
                new Token(TokenTypes.RPARAM,"")
        );
        assertEquals(parser.convertTokenToAST(tokens).evaluate(),100.0);
    }

}
