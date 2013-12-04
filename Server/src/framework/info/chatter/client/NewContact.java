package framework.info.chatter.client;

import java.io.Serializable;

import framework.info.chatter.server.Scr;

public class NewContact implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -8530216490523164121L;
	public Scr registry;
	public String newContact;
	public NewContact(String s, Scr r){
		registry=r;
		newContact=s;
	}
	public String toString(){
		return "NewContact";
	}

}
