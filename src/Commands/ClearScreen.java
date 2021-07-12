package Commands;

import Poloska.Poloska;

public class ClearScreen implements Command{
	/**Let�rli a k�perny�t
	 * 
	 * @param p A j�t�kban l�v� Poloska objektum.
	 */
    @Override
    public void execute(Poloska p) {
        p.resetCanvas();
    }
}
