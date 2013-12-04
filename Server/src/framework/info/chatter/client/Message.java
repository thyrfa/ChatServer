package framework.info.chatter.client;

import java.io.Serializable;
import framework.info.chatter.server.Scr;

public class Message implements Serializable{
	public int target;
	public String message;
	public Scr register;
	private static final long serialVersionUID = -6218726028542840702L;

	public Message(int t, String str, Scr scr){
		message=str;
		target=t;
		register=scr;
	}
	public String toString(){
		return "message";
	}
}
