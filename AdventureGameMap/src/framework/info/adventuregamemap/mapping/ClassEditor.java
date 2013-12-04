package framework.info.adventuregamemap.mapping;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import framework.info.adventuregamemap.characters.EnemyMob;

public class ClassEditor implements ActionListener{
	GameMap gmap;
	JFrame panel=new JFrame();
	LevelDesign parent;
	ArrayList<String> indexs= new ArrayList<String>();
	ArrayList<Object[]> info;
	String changed;
	JTextField classname;
	JTextField health;
	JTextField attack;
	JTextField expvalue;
	JTextField regen;
	JButton submit;
	String clicked;
	JLabel messages=new JLabel("Hey whats up, I don't feel like validating input so only put the right data type in");
	
	public ClassEditor(ArrayList<Object[]> information, LevelDesign x, GameMap gamemap, ArrayList<String> index){
		gmap=gamemap;
		info=information;
		parent=x;
		panel.setLayout(new BoxLayout(panel.getContentPane(), BoxLayout.Y_AXIS));
		panel.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		indexs=index;
	}
	public void getOption(String str){
		System.out.println(str);
		changed=str;
		if (str!=null){
			charSet(indexs.indexOf(changed));
		}
		else{
			charSet(-1);
		}
	}
	public void charSet(int n){
		panel=new JFrame();
		panel.setLayout(new BoxLayout(panel.getContentPane(), BoxLayout.Y_AXIS));
		panel.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		submit=new JButton("Submit");
		submit.addActionListener(this);
		submit.setActionCommand("Submit");
		if (changed!=null){
			classname=new JTextField(changed);
			panel.add(messages);
			panel.add(new JLabel("Health, numbers only"));
			panel.add(health=new JTextField(""+info.get(n)[1]));
			panel.add(new JLabel("Attack, numbers only"));
			panel.add(attack=new JTextField(""+info.get(n)[2]));
			panel.add(new JLabel("Exp value to the adventurer on death, numbers only"));
			panel.add(expvalue=new JTextField(""+info.get(n)[3]));
			panel.add(new JLabel("Regen of health per move, numbers only"));
			panel.add(regen=new JTextField(""+info.get(n)[4]));
			panel.add(submit);
		}
		else{
			panel.add(messages);
			messages.setText("Class name here");
			panel.add(messages);
			panel.add(classname=new JTextField());
			panel.add(new JLabel("Health, numbers only"));
			panel.add(health=new JTextField());
			panel.add(new JLabel("Attack, numbers only"));
			panel.add(attack=new JTextField());
			panel.add(new JLabel("Exp value to the adventurer on death, numbers only"));
			panel.add(expvalue=new JTextField());
			panel.add(new JLabel("Regen of health per move, numbers only"));
			panel.add(regen=new JTextField());
			panel.add(submit);
		}
		panel.pack();
		panel.setVisible(true);
		panel.setState(java.awt.Frame.ICONIFIED);
		panel.setState(java.awt.Frame.NORMAL);
		
	}
	public void newMob(String classname, String location){
		EnemyMob mob= new EnemyMob(location, classname, gmap);
		int n=indexs.indexOf(classname);
		mob.setHealth((Integer)info.get(n)[1]);
		mob.setAttack((Integer)info.get(n)[2]);
		mob.setExp((Integer)info.get(n)[3]);
		mob.setRegen((Integer)info.get(n)[4]);
		gmap.killThing(location);
		gmap.placeThing(location, mob);
		gmap.enemies.add(location);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		panel.removeAll();
		panel.dispose();
		if (indexs.indexOf(classname.getText())<0){
			Object[] chars=new Object[] {classname.getText(), Integer.parseInt(health.getText()), Integer.parseInt(attack.getText()),
										Integer.parseInt(expvalue.getText()), Integer.parseInt(regen.getText())
			};
			indexs.add((String)chars[0]);
			info.add(chars);
			parent.newInfo(info, indexs);
			parent.gui.addNewClass(classname.getText());
			}
		else{
			Object[] chars=new Object[] {classname.getText(), Integer.parseInt(health.getText()), Integer.parseInt(attack.getText()),
					Integer.parseInt(expvalue.getText()), Integer.parseInt(regen.getText())
			};
			info.set(indexs.indexOf(changed), chars);
			parent.newInfo(info, indexs);
		}
		
	}

}

