package framework.info.adventuregamemap.characters;

import framework.info.adventuregamemap.mapping.GameMap;

public abstract class GameClass {
	public GameMap gameMap;
	public String location;
	public String className;
	public double health;
	public double basehealth;
	public double regen;
	public int expvalue;
	public boolean dead;
	public double attack;
	
	public GameClass(String l, String c, GameMap gameMap){
		location=l;
		className=c;
		dead=false;
		attack=0;
	}
	public void setHealth(int x){
		health=x;
		basehealth=x;
	}
	public void setRegen(int x){
		regen=x;
	}
	public void setExp(int x){
		expvalue=x;
	}
	public void setAttack(int x){
		attack=(double)x;
	}
	public void turn(){
		if (health+regen<=basehealth){
			health+=regen;
		}
	}
	public double getHealth(){
		return health;
	}
	public void setClass(String str){
		className=str;
	}
	public boolean attacked(double damage, Adventurer source){
		source.setHealth((int)(source.getHealth()-this.attack()));
		if (this.className.equals("Rock")){
			return false;
		}
		if (this.className.equals("Floor")){
			return true;
		}
		if (source.health<=0){
			source.dead=true;
			return false;
		}
		setHealth((int)(getHealth()-damage));
		if (health<=0){
			source.experience+=expvalue;
			return true;
		}
		return false;
	}
	public double attack(){
		return attack;
	}
}
