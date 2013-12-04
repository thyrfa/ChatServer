package framework.info.adventuregamemap.characters;

import framework.info.adventuregamemap.mapping.GameMap;

public class Floor extends GameClass {
	public Floor(GameMap gmap, String coords){
		super(coords, "Floor", gmap);
	}
}
