package framework.info.adventuregamemap.mapping;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.security.spec.KeySpec;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import javax.swing.JFrame;

import framework.info.adventuregamemap.characters.GameClass;

public class Saver {
//sighhhhhhhhhhhhhhhhhhhhhhhhhhhhhhhh
	public GameMap gameMap;
	public String savefile;
	public int serial;
	/**
	 * @param gmap
	 * @param x
	 */
	public Saver(GameMap gmap){
		savefile="C:\\Users\\"+System.getProperty("user.name")+"\\Documents\\Adventure Game\\Saves";
		gameMap=gmap;
        File two= new File(savefile);
        ArrayList<String> namestwo = new ArrayList<String>(Arrays.asList(two.list()));
        serial=namestwo.size();
        savefile="C:\\Users\\"+System.getProperty("user.name")+"\\Documents\\Adventure Game\\Saves\\Game "+serial;
	}
	public Saver(GameMap gmap, String x){
		gameMap=gmap;
		savefile=x;
	}
	public void Save() {
		File main=new File(savefile);
		main.mkdirs();
		HashMap<String, GameClass> mainmap=gameMap.getMap();
		Object[] keys=mainmap.keySet().toArray();
		ArrayList<Object[]> information = gameMap.gui.hearer.information;
		ArrayList<String> index=gameMap.gui.hearer.index;
		FileWriter writerone;
		FileWriter writertwo;
		FileWriter writerthree;
		File one=new File(savefile+"\\info.txt");
		File two=new File(savefile+"\\index.txt");
		File three=new File(savefile+"\\map.txt");
		File four=new File(savefile+"\\load.work");
		one.delete();
		two.delete();
		three.delete();
		try {
			writertwo = new FileWriter(two);
		
		for (int i=0; i<index.size(); i++){
			writertwo.write(index.get(i)+"~~");
		}
		writertwo.write("@"+String.valueOf(gameMap.gamesize).length()+gameMap.gamesize);
		writerone=new FileWriter(one);
		for (int i=0; i<information.size(); i++){
			for (int n=0; n<information.get(i).length; n++){
				writerone.write(information.get(i)[n]+"~");
			}
			writerone.write("`");
		}
		
		writerthree=new FileWriter(three);
		for (int i=0; i<gameMap.gamesize; i++){
			writerthree.write(keys[i]+"~~"+mainmap.get(keys[i]).className);
			writerthree.write("```");
		}
		writerone.close();
		writertwo.close();
		writerthree.close();
		four.createNewFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
