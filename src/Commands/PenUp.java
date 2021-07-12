package Commands;

import Poloska.Poloska;

public class PenUp implements Command{
	/** Felemeli a tollat.*/
    @Override
    public void execute(Poloska p) {
        p.getPen().setPenUp(true);
    }
}
