package framework.info.adventuregamemap.mapping;

import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import framework.info.adventuregamemap.characters.Floor;
import framework.info.adventuregamemap.characters.Rock;

public class LevelDesign implements MouseListener, ActionListener {
	double gamesize;
	GameMap gmap;
	MainGui gui;
	Point p;
	int xz;
	int yz;
	Object[] Goblin=new Object[] {"Goblin", 35, 1, 50, 5};
	ArrayList<Object[]> information = new ArrayList<Object[]>();
	ClassEditor editor;
	ArrayList<String> index= new ArrayList<String>();
	String clicked;
	
	public LevelDesign(double gsize, GameMap gamemap, MainGui that){
		gamesize=gsize;
		gmap=gamemap;
		gui=that;
		information.add(Goblin);
		index.add("Goblin");
		editor=new ClassEditor(information, this, gmap, index);

	}
	public void newInfo(ArrayList<Object[]> e, ArrayList<String> indexs){
		if (information.size()!=e.size()){
			gui.addNewClass((String)e.get(e.size()-1)[0]);
		}
		information=e;
		index=indexs;
		gmap.classUpdate(e, indexs);
		
	}
	
	public void mouseClicked(MouseEvent e) {	
		p=e.getLocationOnScreen();
		xz=(int)(p.getX()-8)/48;
		yz=(int)(p.getY()-30)/48;
		gui.builder.show(e.getComponent(), e.getX(), e.getY());
		

		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		String command=e.getActionCommand();
		String location;
		System.out.println(command);
		location=xz+", "+yz;
		if (command.equals("floor")){
			gmap.killThing(location);
			gmap.placeThing(location, new Floor(gui.gmap, location));
		}
		else if (command.equals("rock")){
			gmap.killThing(location);
			gmap.placeThing(location, new Rock(gui.gmap, location));
		}
		else if (index.indexOf(command)>=0){
			editor.newMob(command, location);
		}
		else if(index.indexOf(command.substring(1))>=0){
			editor.getOption(command.substring(1));
		}
		else{
			editor.getOption(null);
		}
		gui.clicked=null;
		gui.render(gmap.getMap());
}
}
