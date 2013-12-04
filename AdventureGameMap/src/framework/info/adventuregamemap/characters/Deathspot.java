package framework.info.adventuregamemap.characters;

import framework.info.adventuregamemap.mapping.GameMap;

public class Deathspot extends GameClass {
	public Deathspot(String coords, GameMap gmap){
		super(coords, "Deathspot", gmap);
		gmap.deathspots.put(coords, this);
	}
}
