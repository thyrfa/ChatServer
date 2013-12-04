package framework.info.adventuregamemap.mapping;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import framework.info.adventuregamemap.characters.Adventurer;
import framework.info.adventuregamemap.characters.EnemyMob;
import framework.info.adventuregamemap.characters.Floor;
import framework.info.adventuregamemap.characters.GameClass;
import framework.info.adventuregamemap.characters.Rock;

public class Loader {
	public String saveFolder;
	public int gamesize;
	public HashMap<String, GameClass> mainmap;
	public ArrayList<String> enemies=new ArrayList<String>();
	ArrayList<Object[]> information = new ArrayList<Object[]>();
	ArrayList<String> index= new ArrayList<String>();
	public Adventurer advent;

	
	public Loader(String x){
		saveFolder=x;
	}
	public HashMap<String, String> getMap(){
		HashMap<String, String> returner= new HashMap<String, String>();
		StringBuilder key= new StringBuilder();
		StringBuilder object=new StringBuilder();
		try{
		
		InputStream ist = new FileInputStream(new File(saveFolder+"\\map.txt"));
		BufferedReader in = new BufferedReader(new InputStreamReader(ist));
		boolean moretext=true;
		boolean iskey=true;
		while(moretext){
			int x=in.read();
			if (x!=-1){
				if ((char)x=='~'){
					iskey=false;
				}
				else if ((char)x=='`'){
					iskey=true;
					returner.put(key.toString(), object.toString());
					key.delete(0, key.length());
					object.delete(0, object.length());
				}
				else{
					if (iskey){
						key.append((char)x);
					}
					else{
						object.append((char)x);
					}
				}
			}
			else{
				moretext=false;
			}
		}
		in.close();
		ist.close();

		}catch (Exception e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		return returner;
	}
	public ArrayList<String> getIndex(){
		ArrayList<String> info= new ArrayList<String>();
		StringBuilder object=new StringBuilder();
		try{
			InputStream ist = new FileInputStream(new File(saveFolder+"\\index.txt"));
			BufferedReader in = new BufferedReader(new InputStreamReader(ist));
			boolean moretext=true;
			while(moretext){
				int x=in.read();
				if (x!=-1){
					if ((char)x=='~'){
						if (object.length()==0){}
						else{
						info.add(object.toString());
						object.delete(0, object.length());
						}
					}
					else if ((char)x=='@'){
						char y;
						x=in.read();
						int size=0;
						y=(char)x;
						int len=Character.getNumericValue(y);
						for (int i=len; i>0; i--){
							x=in.read();
							y=(char)x;
							x=Character.getNumericValue(y);
							size+=(Math.pow(10, i-1)*x);
						}
						gamesize=size;
					}
					else{
						object.append((char)x);
					}
				}
				else{
					moretext=false;
				}
			}
			in.close();
			ist.close();
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return info;
	}
	
	public ArrayList<Object[]> getInfo(){
		ArrayList<Object[]> info= new ArrayList<Object[]>();
		StringBuilder object=new StringBuilder();
		ArrayList<Object> holder= new ArrayList<Object>();
		try{
			InputStream ist = new FileInputStream(new File(saveFolder+"\\info.txt"));
			BufferedReader in = new BufferedReader(new InputStreamReader(ist));
			boolean moretext=true;
			boolean istext=true;
			while(moretext){
				int x=in.read();
				if (x!=-1){
					if ((char)x=='~'){
						if (!istext&&object.toString().length()>0){
							holder.add(Integer.parseInt(object.toString()));
						}
						else{
							holder.add(object.toString());
						}
						object.delete(0, object.length());
						istext=false;
					}
					else if ((char)x=='`'){
						holder.add(object.toString());
						info.add(holder.toArray());
						holder.clear();
						object.delete(0, object.length());
						istext=true;
					}
					else{
						object.append((char)x);
					}
				}
				else{
					moretext=false;
				}
			}
			in.close();
			ist.close();
		}
		catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return info;
	}
	public void cleanup(GameMap gmap){
		information=getInfo();
		index=getIndex();
		mainmap=new HashMap<String, GameClass>(gamesize);
		HashMap<String, String> map= getMap();
		for (int i=0; i<Math.sqrt(gamesize);i++){
			for (int n=0; n<Math.sqrt(gamesize);n++){
				if (!(map.get(i+", "+n).equals("Rock")||map.get(i+", "+n).equals("Adventurer")||map.get(i+", "+n).equals("Floor"))){
					EnemyMob mob= new EnemyMob(i+", "+n, map.get(i+", "+n), gmap);
					mainmap.put(i+", "+n, mob);
					enemies.add(i+", "+n);
				}
				else if (map.get(i+", "+n).equals("Rock")){
					mainmap.put(i+", "+n, new Rock(gmap, i+", "+n));
				}
				else if (map.get(i+", "+n).equals("Floor")){
					mainmap.put(i+", "+n, new Floor(gmap, i+", "+n));
				}
				else{
					advent=new Adventurer(gmap, i+", "+n);
					mainmap.put(i+", "+n, advent);
				}
			}
		}
	}
		

}
