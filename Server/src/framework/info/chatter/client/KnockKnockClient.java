package framework.info.chatter.client;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.SocketException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import framework.info.chatter.server.Scr;


public class KnockKnockClient implements ActionListener, WindowListener, KeyListener {
    Socket kkSocket = null;
    ObjectOutputStream out = null;
    ObjectInputStream in = null;
    JFrame frame= new JFrame();
    JPanel chatter= new JPanel();
    JPanel social= new JPanel();
    JTextArea text= new JTextArea(50, 50);
    JTextField input= new JTextField(50);
    JButton button= new JButton("Send");
    boolean connection=true;
    JScrollPane scroll;
    int target=-1;
    boolean control;
    Scr registry;
    boolean logged=false;
    Login log;
    BufferedInputStream bin;
    int contacts;
    boolean contacting=false;
    
    
    public KnockKnockClient() throws IOException, SocketException {
    	social.setLayout(new GridBagLayout());
        try{
    		HttpClient httpClient = new DefaultHttpClient();
    		HttpPost httpPost = new HttpPost("http://pastebin.com/raw.php?i=kijHHMFv");
    		HttpResponse response = httpClient.execute(httpPost);
    		HttpEntity respEntity = response.getEntity();
    		String content="localhost";
  		    if (respEntity != null) {
   		        // EntityUtils to get the reponse content
   		        content =  EntityUtils.toString(respEntity);
   		    }
  		    kkSocket = new Socket(content, 6667);
        }catch(Exception e){
        	text.append("No Connection to Server");
        	connection=false; 	
        }
        text.append("Welcome!\n");
        if (connection){
        	OutputStream buffs= new BufferedOutputStream(kkSocket.getOutputStream());
            out = new ObjectOutputStream(buffs);
            out.flush();
        	bin= new BufferedInputStream(kkSocket.getInputStream());
            in = new ObjectInputStream(bin);
            try {
				initiate();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
        else{
        	frame.add(new JLabel("Server not Available"));
        	frame.pack();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        	frame.setVisible(true);

        }
        while(!logged){
    	    try
    	    {
    	        Thread.sleep(50);
    	    }
    	    catch (Exception e)
    	    {
    	        e.printStackTrace();
    	    }
        }
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new GridBagLayout());
        chatter.setLayout(new GridBagLayout());
        GridBagConstraints c= new GridBagConstraints();
        c.gridx=0;
        c.gridy=0;
        c.weightx=1;
        c.weighty=1;
        frame.addWindowListener(this);
        frame.addKeyListener(this);
        input.addKeyListener(this);
        text.addKeyListener(this);
        text.setLineWrap(true);
        text.setEditable(false);
        scroll = new JScrollPane (text, 
        		   JScrollPane.VERTICAL_SCROLLBAR_ALWAYS, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scroll.addKeyListener(this);
        c.anchor=GridBagConstraints.PAGE_START;
        chatter.add(scroll, c);
        c.anchor=GridBagConstraints.PAGE_START;
        c.gridy++;
        chatter.add(input, c);
        c.gridx++;
        c.anchor=GridBagConstraints.PAGE_END;
        chatter.add(button);
        c.gridx=0;
        c.gridy=0;
        c.anchor=GridBagConstraints.LINE_START;
        frame.add(social, c);
        c.gridx++;
        c.anchor=GridBagConstraints.PAGE_START;
        System.out.println(chatter.getMinimumSize());
        frame.add(chatter, c);
        frame.pack();
        button.setActionCommand("send");
        button.addActionListener(this);
        button.addKeyListener(this);
        frame.setVisible(true);

        try {
			getMessages();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    }
    public void initiate() throws IOException, ClassNotFoundException{
        log=new Login(this);

    }
    public void init2(String user, String pw) throws ClassNotFoundException, IOException{
        Object obj;
        out.writeObject(user+"!@pass!@"+pw);
        out.flush();
        obj=in.readObject();

        if (obj.equals("badLogon")){
        	initiate();
        }
        else{
        	registry=(Scr)obj;
        	contacts=registry.contacts().length;
        	GridBagConstraints c= new GridBagConstraints();
        	for (int i=0; i<registry.numberContacts();i++){
        		String s=registry.contacts()[i].toString();
				JButton b=new JButton(s);
				b.setActionCommand(""+registry.getID(s));
				b.addActionListener(this);
				social.add(b, c);
				c.gridy++;
        	}
        	logged=true;
        }
    	log=null;
    }
	public static void main(String[] args)throws IOException, InterruptedException {
		new KnockKnockClient();
	}
	public void getMessages() throws IOException, SocketException, ClassNotFoundException{
        Object fromServer;
        while (connection){
        if (bin.available()>0&&!contacting){
        	fromServer=in.readObject();
        	if (fromServer.equals("AWUIIGAJAGIAWO")){
        		out.close();
        		in.close();
        		System.exit(0);
        	}
        	if (fromServer.getClass().toString().indexOf("Scr")>=0){
        		registry=(Scr)fromServer;
        		text.replaceRange(registry.getBuffer(target), 0, text.getText().length());
        		if (registry.contacts().length>contacts){
					GridBagConstraints c= new GridBagConstraints();
					social.removeAll();
                	for (int i=0; i<registry.numberContacts();i++){
                		String s=registry.contacts()[i].toString();
        				JButton b=new JButton(s);
        				b.setActionCommand(""+registry.getID(s));
        				b.addActionListener(this);
        				social.add(b, c);
        				c.gridy++;
                	}
					frame.remove(social);
					c.gridx=0;
					c.gridy=0;
					frame.add(social, c);
					frame.pack();
					frame.setVisible(true);
        		}
        		
        	}
        	if (fromServer instanceof boolean[]){
        		boolean[] status=(boolean[])fromServer;
            	GridBagConstraints c= new GridBagConstraints();
            	c.gridx=0;
            	c.gridy=0;
            	social.removeAll();
            	for (int i=0; i<registry.numberContacts();i++){
            		String s=registry.contacts()[i].toString();
    				JButton b=new JButton(s);
    				b.setActionCommand(""+registry.getID(s));
    				b.addActionListener(this);
    				social.add(b, c);
    				c.gridy++;
            	}
            	c.gridy=0;
            	c.gridx=1;
            	for (int i=0; i<status.length;i++){
    				ImageIcon image = new ImageIcon("src\\framework\\info\\chatter\\client\\"+status[i]+".png");
        			JLabel label= new JLabel(image);
    				social.add(label, c);
    				c.gridy++;
            	}
            	frame.remove(social);
				c.gridx=0;
				c.gridy=0;
				frame.add(social, c);
				frame.pack();
				frame.setVisible(true);
        	}
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
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("send")&&target!=-1){
			this.sendText();
		}
		else if (!(e.getActionCommand().equals("send"))){
			target=Integer.parseInt(e.getActionCommand());
			text.replaceRange(registry.getBuffer(target), 0, text.getText().length());
		}
	}
	@Override
	public void windowActivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void windowClosed(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void windowClosing(WindowEvent arg0) {
		if (connection){
			try {
				out.writeObject("windowclosing");
				out.flush();
				out.close();
				in.close();
				connection=false;
				System.exit(0);
			}
			catch (IOException e) {
			// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.exit(0);
		}
		else{
			System.exit(0);
		}
	}
	@Override
	public void windowDeactivated(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void windowDeiconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void windowIconified(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void windowOpened(WindowEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode()==KeyEvent.VK_CONTROL){
			control=true;
		}
		if (control&&e.getKeyCode()==KeyEvent.VK_N){
			String s = JOptionPane.showInputDialog(null, "What is the username of your new contact?");
			newContact(s);
		}

		
	}
	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode()==KeyEvent.VK_CONTROL){
			control=false;
		}
		
	}
	@Override
	public void keyTyped(KeyEvent e) {
		if (e.getKeyChar()==KeyEvent.VK_ENTER&&target!=-1){
			this.sendText();
		}
	}
	public void newContact(String s){
		try {
			contacting=true;
			out.writeObject(new NewContact(s,registry));
			out.flush();
			Object fromServer;
			while (contacting) {
				if (bin.available()>0){
					fromServer=in.readObject();
					if (fromServer.equals("invalidcontact")){
						JOptionPane.showMessageDialog(null, "Contact does not exist, try again.", "Ghost Contact", JOptionPane.ERROR_MESSAGE);
					}
					else{
						registry=(Scr)fromServer;
						fromServer=in.readObject();
						boolean[] status= (boolean[])fromServer;
		            	GridBagConstraints c= new GridBagConstraints();
		            	c.gridx=0;
		            	c.gridy=0;
		            	social.removeAll();
		            	for (int i=0; i<registry.numberContacts();i++){
		            		String s1=registry.contacts()[i].toString();
		    				JButton b=new JButton(s1);
		    				b.setActionCommand(""+registry.getID(s1));
		    				b.addActionListener(this);
		    				social.add(b, c);
		    				c.gridy++;
		            	}
		            	c.gridy=0;
		            	c.gridx=1;
		            	for (int i=0; i<status.length;i++){
		    				ImageIcon image = new ImageIcon("src\\framework\\info\\chatter\\client\\"+status[i]+".png");
		        			JLabel label= new JLabel(image);
		    				social.add(label, c);
		    				c.gridy++;
		            	}
		            	frame.remove(social);
						c.gridx=0;
						c.gridy=0;
						frame.add(social, c);
						frame.pack();
						frame.setVisible(true);
						contacting=false;
					}
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

		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (HeadlessException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		

	}
	public void sendText(){
		try {
			registry.addText(target, input.getText());
    		text.replaceRange(registry.getBuffer(target), 0, text.getText().length());        	
			out.writeObject(new Message(target, input.getText(), registry));
			out.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		input.setText("");
	}
}
