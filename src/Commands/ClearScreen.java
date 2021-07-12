package Commands;

import Poloska.Poloska;

public class ClearScreen implements Command{
	/**Letörli a képernyõt
	 * 
	 * @param p A játékban lévõ Poloska objektum.
	 */
    @Override
    public void execute(Poloska p) {
        p.resetCanvas();
    }
}
