package Commands;
import Poloska.Poloska;
import Exception.InputErrorException;
public interface Command {
    public void execute(Poloska p)throws InputErrorException;
}
