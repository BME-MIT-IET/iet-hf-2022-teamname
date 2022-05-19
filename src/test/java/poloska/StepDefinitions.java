package poloska;

import Poloska.Poloska;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import javafx.scene.canvas.Canvas;

import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;


public class StepDefinitions {
    private HashMap<String,Integer> faceToRotation = new HashMap<String,Integer>();
    Poloska poloska;
    public StepDefinitions(){
        faceToRotation.put("up", 90);
        faceToRotation.put("left", 180);
        faceToRotation.put("right", 0);
        faceToRotation.put("down", 270);

    }

    @Given("a poloska at x:{int} y:{int} facing {word}")
    public void a_poloska_at_x_y_facing(Integer x, Integer y, String facing) {
        poloska = new Poloska(x,y,new Canvas());
        Integer rotation = faceToRotation.get(facing);
        poloska.rotate(rotation-poloska.getRotation());

    }
    @When("(the poloska )goes forward {int}")
    public void the_poloska_goes_forward(Integer amount) {
        poloska.move(amount);
    }
    @When("(the poloska )turns {word}")
    public void the_poloska_goes_forward(String facing) {
        Integer rotation = faceToRotation.get(facing);
        poloska.rotate(rotation-poloska.getRotation());
    }
    @Then("x position is {double}")
    public void x_position_is(Double expectedValue) {
        assertEquals(expectedValue,poloska.getX());
    }
    @Then("y position is {double}")
    public void y_position_is(Double expectedValue) {
        assertEquals(expectedValue,poloska.getY());
    }

    @And("facing {word}")
    public void facing(String direction) {
        double rotation = faceToRotation.get(direction);
        assertEquals(rotation, poloska.getRotation());
    }
}
