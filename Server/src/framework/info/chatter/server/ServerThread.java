package framework.info.chatter.server;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import framework.info.chatter.client.Message;
import framework.info.chatter.client.NewContact;



public class ServerThread extends Thread implements Runnable {
    private KnockKnockServer server = null;
    private final int number;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private String mine;
    private Scr clientrecord;
    private BufferedInputStream bin;

    public ServerThread(Socket socket, KnockKnockServer kks, int i, Scr record, ObjectOutputStream o, ObjectInputStream input, BufferedInputStream buff) throws IOException {
    	super("ServerThread");
    	this.server=kks;
    	number=i;
    	out= o;
    	in = input;
    	clientrecord=record;
    	bin=buff;
    }
    public void run(){
    	try {
			out.writeObject("Initiated Server");
			out.flush();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
    	while(true){
    		try {
    			Object obj;
    			if (bin.available()>0){
    				obj=in.readObject();
    				if (!(obj instanceof Message)&&!(obj instanceof Object[])){
    					mine=obj.toString();
    					if (mine.equals("NewContact")){
    						NewContact con=(NewContact)obj;
    						clientrecord=con.registry;
    						if (server.validContact(con.newContact)){
    							server.clientConnect(number, con.newContact);
    							clientrecord.newContact(con.newContact, server.getID(con.newContact));
    							out.writeObject(clientrecord);
    							out.flush();
    					    	out.writeObject(server.onlineCheck(clientrecord.contacts()));
    					    	out.flush();
    						}
    						else{
    							out.writeObject("invalidcontact");
    							out.flush();
    						}
    					}
    					if (mine.equals("windowclosing")){
    						out.close();
    						in.close();
    						server.recordUpdate(clientrecord, number);
    						Thread.currentThread().interrupt();
    						return;
    					}
    				}
    				else if (obj instanceof Object[]){
    					out.writeObject(server.onlineCheck((Object[])obj));
    					out.flush();
    				}
					else{
						Message message=(Message)obj;
						clientrecord=message.register;
						server.input(message.message, number, message.target);
					}
    			}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	    try
    	    {
    	        Thread.sleep(50);
    	    }
    	    catch (Exception e)
    	    {
    	        e.printStackTrace();
    	    }
    	}
    }
    protected void pass(String str, int i) throws IOException{
    	clientrecord.newMessage(i, str);
    	out.writeObject(clientrecord);
    	out.flush();
    }
    protected void onlineCheck() throws IOException{
    	out.writeObject(server.onlineCheck(clientrecord.contacts()));
		out.flush();
    }
    protected void newContact(String str, int i) throws IOException{
    	clientrecord.newContact(str, i);
    	out.writeObject(clientrecord);
    	out.flush();
    }
    
    

}
