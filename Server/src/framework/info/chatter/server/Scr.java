package framework.info.chatter.server;

import java.io.Serializable;
import java.util.HashMap;

import javax.swing.JTextArea;

public class Scr implements Serializable {
	private static final long serialVersionUID = 4956203855277490474L;
	HashMap<Integer, JTextArea> buffered= new HashMap<Integer, JTextArea>(100);
	HashMap<String, Integer> ids= new HashMap<String, Integer>(100);
	HashMap<Integer, String> revids= new HashMap<Integer, String>(100);
	String username;
	public Scr(String str){
		username=str;
	}
	public void newContact(String str, int id){
		System.out.println(str+"new");
		ids.put(str, id);
		revids.put(id, str);
		buffered.put(id, new JTextArea("Welcome to Chatr"));
		System.out.println(ids.keySet().contains("test3")+"newcontact");
	}
	public String getBuffer(int id){
		return buffered.get(id).getText();
	}
	public JTextArea getBuff(int id){
		return buffered.get(id);
	}
	public int getID(String str){
		return ids.get(str);
	}
	public int numberContacts(){
		return ids.size();
	}
	public Object[] contacts(){
		return ids.keySet().toArray();
	}
	public void newMessage(int id, String str){
		buffered.get(id).append("\nThem: "+str);
	}
	public void addText(int id, String str){
		if (id!=-1){
			buffered.get(id).append("\nYou: "+str);
		}
	}
	public boolean isSCR(){
		return true;
	}
}
