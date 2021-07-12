package Commands;

import Poloska.Poloska;

public class PenDown implements Command{
	/** Leteszi a tollat.*/
    @Override
    public void execute(Poloska p) {
        p.getPen().setPenUp(false);
    }
}
