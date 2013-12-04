package framework.info.adventuregamemap.mapping;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

public class StartupOptions implements ActionListener {
	GameMap gmap;
	JFrame panel;
	public StartupOptions(){
		getOption();
	}
	public void getOption(){
		File hub= new File("C:\\Users\\"+System.getProperty("user.name")+"\\Documents\\Adventure Game");
		if (hub.mkdirs()){
			hub= new File("C:\\Users\\"+System.getProperty("user.name")+"\\Documents\\Adventure Game\\Saves");
			hub.mkdir();
			hub= new File("C:\\Users\\"+System.getProperty("user.name")+"\\Documents\\Adventure Game\\Images");
			hub.mkdir();
		}
		panel=new JFrame();
		panel.setLayout(new GridBagLayout());
		GridBagConstraints c= new GridBagConstraints();
		panel.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JLabel question=new JLabel("Which mode would you like to start AdventureGameMap in?");
		JButton game=new JButton("Adventure Mode");
		game.setActionCommand("game");
		game.addActionListener(this);
		JButton make=new JButton("Build Mode");
		make.setActionCommand("build");
		make.addActionListener(this);
		JButton load=new JButton("Load game in Build Mode");
		load.setActionCommand("bl");
		load.addActionListener(this);
		JButton loadplay=new JButton("Load game in Adventure Mode");
		loadplay.setActionCommand("bp");
		loadplay.addActionListener(this);
		c.gridy=0;
		c.gridx=1;
		panel.add(question, c);
		c.gridx++;
		c.gridy=1;
		panel.add(game, c);
		c.gridx=0;
		panel.add(make, c);
		c.gridy=2;
		c.gridx=0;
		panel.add(load, c);
		c.gridx=2;
		panel.add(loadplay, c);
		panel.pack();
		panel.setVisible(true);
	}
	public int gameSize(){
		int s = Integer.parseInt(JOptionPane.showInputDialog(panel, "What size should the board be? (Must be square atm)"));
		if ((double)(int)Math.sqrt(s)*(int)Math.sqrt(s)!=s){
			gameSize();
		}
		return s;
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals("build")){
			int s=gameSize();
			panel.dispose();
			gmap=new GameMap("build", s);
		}
		else if (e.getActionCommand().equals("bl")||e.getActionCommand().equals("bp")){
			System.out.println("yup");
			JFileChooser fc = new JFileChooser(new File("C:\\Users\\"+System.getProperty("user.name")+"\\Documents\\Adventure Game\\Saves"));
		    FileNameExtensionFilter filter = new FileNameExtensionFilter(
		            "SaveFiles", "work");
		    fc.setFileFilter(filter);
		    int returnVal = fc.showOpenDialog(panel);
		    if(returnVal == JFileChooser.APPROVE_OPTION){
		    	System.out.println("yup2");
		    	panel.dispose();
		    	Loader load= new Loader(fc.getSelectedFile().getParent());
		    	gmap=new GameMap(load, e.getActionCommand());
		    	
		    }
			
		}
		else{
			panel.dispose();
			gmap=new GameMap();
		}
	}
}
