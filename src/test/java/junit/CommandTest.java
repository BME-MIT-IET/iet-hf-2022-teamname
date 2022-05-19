package junit;

import Commands.Command;
import Commands.LoopExpression;
import InputTranslator.Token;
import InputTranslator.TokenTypes;
import Poloska.Poloska;
import javafx.scene.canvas.Canvas;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import Exception.InputErrorException;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CommandTest {

    Poloska poloska= new Poloska(600,400,new Canvas());

    @Test
    @DisplayName("Loop command should work")
    public void loop_command_test() throws InputErrorException {
        List<Token> tokens = List.of(
                new Token(TokenTypes.NUMBER, "4"),
                new Token(TokenTypes.CURLYLP, ""),
                new Token(TokenTypes.STRING, "e"),
                new Token(TokenTypes.NUMBER, "50"),
                new Token(TokenTypes.CURLYRP, "")
        );
        Command cmd = new LoopExpression(tokens);
        cmd.execute(poloska);

        assertEquals(200,poloska.getY());
    }
}
