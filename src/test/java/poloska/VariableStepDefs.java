package poloska;

import Poloska.Poloska;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import javafx.scene.canvas.Canvas;
import org.junit.jupiter.api.BeforeEach;
import variables.VariableStorage;
import static org.junit.jupiter.api.Assertions.*;

public class VariableStepDefs {
    private VariableStorage varStorage= VariableStorage.getInstance();


    @Given("there are no other variables")
    public void thereAreNoOtherVariables() {
        varStorage.clear();
    }

    @When("we create a variable called {word} with value {double}")
    public void weCreateAVariableCalledTestWithValue(String varName,double arg0) {
        varStorage.addVariable(varName,arg0,false);
    }

    @Then("the container has {int} variable(s)")
    public void theContainerHasVariable(int arg0) {
        assertEquals(arg0,varStorage.getVariableCount());
    }

    @When("we create a loop variable")
    public void weCreateALoopVariable() {
        String varName = varStorage.giveLoopName();
        varStorage.addVariable(varName,0.0,true);
    }

    @And("there will be a variable called {word} with value {double}")
    public void thereWillBeAVariableCalledTestWithValue(String varName, double arg0) {
        assertEquals(arg0,varStorage.getVariable(varName));
    }

    @Given("we are {int} loops deep")
    public void weAreLoopsDeep(int arg0) {
        for (int i = 0; i < arg0; i++) {
            String varName= varStorage.giveLoopName();
            varStorage.addVariable(varName,0.0,true);
        }
    }

    @Then("there will be a variable called {word}")
    public void thereWillBeAVariableCalledL(String varName) {
        assertNotNull(varStorage.getVariable(varName));
    }

    @Given("we are in a function called {word}")
    public void weAreInAFunctionCalledTestFunc(String funcName) {
        varStorage.setInFunc(true,funcName);
    }

    @And("we remove a loop variable {word}")
    public void weRemoveAVariableL(String varName) {
        varStorage.removeVariable(varName, true);
    }

    @Then("there will not be a variable called {word}")
    public void thereWillNotBeAVariableCalledL(String varName) {
        assertNull(varStorage.getVariable(varName));

    }

    @Given("we have a variable named {word} with value {double}")
    public void weHaveAVariableNamedTestWithValue(String varName, double arg1) {
        varStorage.addVariable(varName,arg1,false);
    }

    @When("we change the value of {word} to {double}")
    public void weChangeTheValueOfTestTo(String varName, double arg1) {
        varStorage.updateVariable(varName,arg1);
    }

    @And("we are not in a function")
    public void weAreNotInAFunction() {
        varStorage.setInFunc(false,"");
    }
}
