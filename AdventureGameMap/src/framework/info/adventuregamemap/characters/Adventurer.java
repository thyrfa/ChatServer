package framework.info.adventuregamemap.characters;

import framework.info.adventuregamemap.mapping.Coordinate;
import framework.info.adventuregamemap.mapping.GameMap;

public class Adventurer extends GameClass {
	public int experience;
	int level;
	public String location;
	double damage;
	GameMap gmap;
	int[] explevels={100, 200, 300, 400, 500};
	double[] dmglevels={20.0, 30.0, 40.0, 50.0, 60.0};
	public Adventurer(GameMap gameMap, String loc){
		super(loc, "Adventurer", gameMap);
		this.setHealth(100);
		this.setRegen(10);
		location=loc;
		experience=0;
		level=0;
		damage=10.0;
		gmap=gameMap;
	}
	public void move(String newcoords){
		this.gmap.Move(location, newcoords, damage, this);
	}
	public Coordinate toCoord(){
		return (new Coordinate(location));
	}
	
	public void expGain(int increase){
		experience+=increase;
		if (level!=4&&experience>=explevels[level]){
			level++;
			damage=dmglevels[level];
		}
		
	}
}
