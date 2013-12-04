package framework.info.chatter.client;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;


public class Login implements ActionListener, KeyListener {
	JFrame frame;
	JTextField user=new JTextField(50);
	JTextField pass=new JTextField(50);
	JButton button=new JButton("Submit");
	String username;
	String password;
	KnockKnockClient k;
	public Login(KnockKnockClient c){
		System.out.println("log");
		frame=new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
		frame.addKeyListener(this);
		user.addKeyListener(this);
		pass.addKeyListener(this);
		button.addActionListener(this);
		frame.add(new JLabel("Username:"));
		frame.add(user);
		frame.add(new JLabel("Password:"));
		frame.add(pass);
		frame.add(button);
		frame.pack();
		frame.setVisible(true);
		k=c;
	}
	public void sendText(){
		username=user.getText();
		password=pass.getText();
		try {
			k.init2(username, password);
		} catch (ClassNotFoundException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		frame.dispose();
	}
	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode()==10){
			this.sendText();
		}				
	}
	@Override
	public void keyReleased(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void keyTyped(KeyEvent e) {
		if (e.getKeyCode()==10){
			this.sendText();
		}		
	}
	@Override
	public void actionPerformed(ActionEvent arg0) {
		this.sendText();
		
	}
}
