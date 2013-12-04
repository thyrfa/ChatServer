package framework.info.adventuregamemap.mapping;

import java.util.ArrayList;
import java.util.HashMap;

import framework.info.adventuregamemap.characters.Adventurer;
import framework.info.adventuregamemap.characters.Deathspot;
import framework.info.adventuregamemap.characters.Floor;
import framework.info.adventuregamemap.characters.GameClass;

public class GameMap {
	public int gamesize=100;
	public HashMap<String, GameClass> mainmap;
	public HashMap<String, Deathspot> deathspots=new HashMap<String, Deathspot>(gamesize);
	public ArrayList<String> enemies=new ArrayList<String>();
	public MainGui gui;
	public Coordinate adventurercoord;
	public Adventurer adventurer;
	public Saver save;
	public boolean attacked=false;
	
	
	public GameMap(){
		save= new Saver(this);
		mainmap=new HashMap<String, GameClass>(gamesize);
		adventurer=new Adventurer(this, "2, 3");
		mainmap.put("2, 3", adventurer);
		adventurercoord=new Coordinate(2, 3);
		fillMap();
		gui=new MainGui(this, adventurer, gamesize, null);
		gui.render(mainmap);
	}
	public GameMap(String str , int s){
		save= new Saver(this);
		gamesize=s;
		mainmap=new HashMap<String, GameClass>(gamesize);
		adventurer=new Adventurer(this, "2, 3");
		mainmap.put("2, 3", adventurer);
		adventurercoord=new Coordinate(2, 3);
		fillMap();
		enemies.add("1, 1");
		gui=new MainGui(this, adventurer, gamesize, "build");
		gui.render(mainmap);
	}
	public GameMap(Loader load, String x){
		save=new Saver(this, load.saveFolder);
		load.cleanup(this);
		gamesize=load.gamesize;
		mainmap=load.mainmap;
		adventurer=load.advent;
		adventurercoord=adventurer.toCoord();
		enemies=load.enemies;
		gui=new MainGui(this, adventurer, gamesize, x);
		if (x.equals("bl")){
			gui.hearer.index=load.index;
			gui.hearer.information=load.information;
		}
		this.classUpdate(load.information, load.index);
		gui.render(mainmap);
	}
	public GameMap(Loader load, boolean play){
		
	}
	public HashMap<String, GameClass> getMap(){
		return mainmap;
	}
	public GameClass occupantName(String s){
		return mainmap.get(s);
	}
	public void placeThing(String s, GameClass o){
		mainmap.put(s, o);
		gui.render(mainmap);
	}
	public void killThing(String s){
		mainmap.remove(s);
	}
	public boolean canMove(String next){
		if (mainmap.get(next)!=null&&occupantName(next).className!="Rock"){
			return true;
		}
		else{
			return false;
		}
	}
	public void Move(String previous, String next, double damage, Adventurer source){
		source.expGain(10);
		if (enemies.indexOf(next)>=0){
			if (mainmap.get(next).attacked(damage, source)){
				gui.killedClicked(mainmap.get(next));
				enemies.remove(next);
				mainmap.remove(next);
				mainmap.remove(previous);
				mainmap.put(next, source);
				source.location=next;
				adventurercoord.move(next);
				new Deathspot(next, this);
				attacked=true;
				if (deathspots.get(previous)!=null){
					mainmap.put(previous, deathspots.get(previous));
				}
				else{
					mainmap.put(previous, new Floor(this, previous));
				}
			}
			else{
				adventurercoord.fix();
				attacked=false;
				if (source.dead){
					mainmap.remove(previous);
					mainmap.put(previous, new Deathspot(previous, this));
					gui.render(mainmap);
					this.end();
				}
			}
		}
		else{

			if (deathspots.get(previous)!=null){
				mainmap.remove(next);
				mainmap.remove(previous);
				mainmap.put(next, source);
				source.location=next;
				mainmap.put(previous, deathspots.get(previous));
			}
			else{
				mainmap.remove(next);
				mainmap.remove(previous);
				mainmap.put(next, source);
				source.location=next;
				mainmap.put(previous, new Floor(this, previous));
			}
		}
		Object[] all=mainmap.values().toArray();
		for (int i=0; i<all.length; i++){
			GameClass n=(GameClass) all[i];
			if (!attacked){n.turn();}
			
		}
		gui.healthUpdate();
		gui.render(mainmap);
	}
	private void fillMap(){
        for (int i=0; i<Math.sqrt(gamesize); i++){
        	for (int n=0; n<Math.sqrt(gamesize); n++){
        		if (mainmap.get(""+i+", "+n)==null){
        			mainmap.put(""+i+", "+n, new Floor(this, ""+i+", "+n));
        		}
        	}
	}
	}
	public void moving(char pressed){
		if (canMove(adventurercoord.change(pressed))){
			adventurer.move(adventurercoord.toString());
		}
		else{
			adventurercoord.fix();
		}
		
		
	}
	public void classUpdate(ArrayList<Object[]> e, ArrayList<String> indexs){
		Object[] all=mainmap.values().toArray();
		for (int i=0; i<all.length; i++){
			GameClass n=(GameClass) all[i];
			if (indexs.indexOf(n.className)<0){
				
			}
			else{
				Object[] values=e.get(indexs.indexOf(n.className));
				n.setHealth((Integer)values[1]);
				n.setAttack((Integer)values[2]);
				n.setExp((Integer)values[3]);
				n.setRegen((Integer)values[4]);
			}
		}
	}
	public void end(){
		gui.done=true;
	}

	public static void main(String[] args){
		new StartupOptions();
	}
}
