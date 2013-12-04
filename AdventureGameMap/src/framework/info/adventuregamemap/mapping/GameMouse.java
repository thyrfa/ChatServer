package framework.info.adventuregamemap.mapping;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

public class GameMouse implements MouseListener {
	double gamesize;
	GameMap gmap;
	MainGui gui;

	public GameMouse(double gsize, GameMap gamemap, MainGui that){
		gamesize=gsize;
		gmap=gamemap;
		gui=that;
	}
	
	public void mouseClicked(MouseEvent e) {
		if (!(e.getLocationOnScreen().getX()/48>Math.sqrt(gamesize))&&!(e.getLocationOnScreen().getY()/48>Math.sqrt(gamesize))){
		int x=(int)(e.getLocationOnScreen().getX()-8)/48;
		int y=(int)(e.getLocationOnScreen().getY()-30)/48;
		gui.mobHealthUpdate(gmap.occupantName(x+", "+y));
		gui.clicked=gmap.occupantName(x+", "+y);
		}
	}
	@Override
	public void mouseEntered(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mousePressed(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void mouseReleased(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	}
