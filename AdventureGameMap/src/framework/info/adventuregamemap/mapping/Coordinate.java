package framework.info.adventuregamemap.mapping;

public class Coordinate {
	public int x;
	public int y;
	public int xbuffer;
	public int ybuffer;	
	public Coordinate(int i, int n){
		x=i;
		y=n;
		xbuffer=i;
		ybuffer=n;
	}
	public Coordinate(String str){
		x= Integer.parseInt(str.substring(0, str.indexOf(",")));
		xbuffer= Integer.parseInt(str.substring(0, str.indexOf(",")));
		y=Integer.parseInt(str.substring(str.indexOf(",")+2));
		ybuffer=Integer.parseInt(str.substring(str.indexOf(",")+2));
	}
	public String toString(){
		return (""+x+", "+y);
	}
	public void move(String str){
		x=Integer.parseInt(str.substring(0, str.indexOf(",")));
		y=Integer.parseInt(str.substring(str.indexOf(",")+2, str.length()));
		xbuffer=x;
		ybuffer=y;
	}
	public String change(char pressedchar){
		xbuffer=x;
		ybuffer=y;
		if (pressedchar=='q'||pressedchar=='w'||pressedchar=='e'){
			y-=1;
		}
		if (pressedchar=='z'||pressedchar=='x'||pressedchar=='c'){
			y+=1;
		}
		if (pressedchar=='q'||pressedchar=='a'||pressedchar=='z'){
			x-=1;
		}
		if (pressedchar=='e'||pressedchar=='d'||pressedchar=='c'){
			x+=1;
		}
		return toString();
	}
	public void fix(){
		x=xbuffer;
		y=ybuffer;
		
	}
}
