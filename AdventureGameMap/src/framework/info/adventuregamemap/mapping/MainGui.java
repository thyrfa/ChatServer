package framework.info.adventuregamemap.mapping;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;

import framework.info.adventuregamemap.characters.Adventurer;
import framework.info.adventuregamemap.characters.GameClass;

public class MainGui implements KeyListener{
	JFrame mainframe;
    GameMap gmap;
    public JPanel frame;
    boolean hasrun=false;
    JLabel label;
    Adventurer a;
    JLabel mobhealth;
    double gamesize;
    public GameClass clicked;
    boolean isbuild;
    public JPopupMenu builder;
    LevelDesign hearer;
    boolean control=false;
    boolean done=false;
    JLabel adventhealth;
	JFrame frame2= new JFrame();

    
    

    public MainGui(GameMap gamemap, Adventurer advent, int gsize, String str){ //constructor
    		mainframe= new JFrame("Adventure Game!");
            mainframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            mainframe.setLayout(new GridBagLayout());
            gmap=gamemap;
            mainframe.addKeyListener(this);
            a=advent;
            mobhealth=new JLabel("Click on a mob to see their health");
            gamesize=(double)gsize;
            hearer=new LevelDesign(gamesize, gmap, this);
            GridBagConstraints c = new GridBagConstraints();
            c.gridx=1;
            c.gridy=0;
            ImageIcon image = new ImageIcon("src\\framework\\info\\adventuregamemap\\characters\\health.jpg");
            adventhealth= new JLabel(image);
			mainframe.add(adventhealth, c);
            if (str!=null&&!(str.equals("bp"))){
            	isbuild=true;
            	popupBuild();
            }
            else{
            	isbuild=false;
            }

    }
    public void render(HashMap<String, GameClass> map){
    	if (!done){
    	if (hasrun){
    		mainframe.remove(frame);
    	}
    	hasrun=true;
    	frame=new JPanel();
        frame.setLayout(new GridBagLayout()); //set layout
        GridBagConstraints c = new GridBagConstraints();
        File f = new File("src\\framework\\info\\adventuregamemap\\characters");
        ArrayList<String> names = new ArrayList<String>(Arrays.asList(f.list()));
        File two= new File("C:\\Users\\"+System.getProperty("user.name")+"\\Documents\\Adventure Game\\Images");
        ArrayList<String> namestwo = new ArrayList<String>(Arrays.asList(two.list()));
        for (int i=0; i<Math.sqrt(gamesize); i++){
        	c.gridx=i;
        	for (int n=0; n<Math.sqrt(gamesize); n++){
        		c.gridy=n;
        		if (map.get(""+i+", "+n)!=null){
        			if (namestwo.indexOf(map.get(""+i+", "+n).className+".jpg")>=0){
        				ImageIcon image = new ImageIcon("C:\\Users\\"+System.getProperty("user.name")+"\\Documents\\Adventure Game\\Images\\"+map.get(""+i+", "+n).className+".jpg");
        				JLabel label= new JLabel(image);
        				frame.add(label, c);
        			}
        			else if (names.indexOf(map.get(""+i+", "+n).className+".jpg")<0){
        				ImageIcon image = new ImageIcon("src\\framework\\info\\adventuregamemap\\characters\\Default.jpg");
            			JLabel label= new JLabel(image);
            			frame.add(label, c);
        			}
        			else{
        				ImageIcon image = new ImageIcon("src\\framework\\info\\adventuregamemap\\characters\\"+map.get(""+i+", "+n).className+".jpg");
        				JLabel label= new JLabel(image);
        				frame.add(label, c);
        			}
        		}
        		else{
        			frame.add(new JButton("x"), c);
        		}
        	}
        }
        GridBagConstraints test= new GridBagConstraints();
        test.gridx=0;
        test.gridy=0;
        mainframe.add(frame, test);
        test.gridx=1;
        test.gridy=0;
        mainframe.add(adventhealth, test);
        test.gridx=0;
        test.gridy=1;
        if (clicked!=null){
        	mobHealthUpdate(clicked);
        }
        mainframe.add(mobhealth, test);
        mainframe.pack();
        if (!isbuild){
        mainframe.addMouseListener(new GameMouse(gamesize, gmap, this));
        }
        else{
        	frame.addMouseListener(hearer);
        }
        mainframe.setVisible(true);
    	}
    	if (done&&isbuild){
    		mainframe.removeMouseListener(hearer);
    		mainframe.removeKeyListener(this);
    	}
    	else if (done){
    		mainframe.removeKeyListener(this);
    	}
    	

    }
    public void mobHealthUpdate(GameClass mob){
    	mobhealth.setText(mob.className+" health: "+mob.health);
    }
	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode()==17){
			control=true;
		}
		if (e.getKeyCode()==83&&control){
			gmap.save.Save();
		}
	}
	@Override
	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode()==17){
			control=false;
		}
	}
	@Override
	public void keyTyped(KeyEvent e) {
		char typedchar=e.getKeyChar();
		if ("qweadzxc".indexOf(typedchar)>=0){
		gmap.moving(typedchar);
		}
	}
	public void healthUpdate(){
		try{
		BufferedImage img = ImageIO.read(new File("src\\framework\\info\\adventuregamemap\\characters\\health.jpg"));
		// Obtain the Graphics2D context associated with the BufferedImage.
		Graphics2D g = img.createGraphics();
		g.setColor(Color.black);

		// Draw on the BufferedImage via the graphics context.
		int height = 100-(int)a.health;
	    g.fillRect (0, 0, 24, height);
	    

	    
		// Clean up -- dispose the graphics context that was created.
		g.dispose();
		ImageIcon imgi=new ImageIcon(img);
		mainframe.remove(adventhealth);
		adventhealth=new JLabel(imgi);
		}catch(Exception e){
			System.out.println(e);
		}

	}

	public void killedClicked(GameClass x){
	if (clicked!=null){
		if (clicked==x){
			mobhealth.setText("Click on a mob to see their health");
			clicked=null;
		}
	}
	}
	public void addNewClass(String str){
		JMenuItem newclass= new JMenuItem(str);
		newclass.setActionCommand(str);
		newclass.addActionListener(hearer);
		((JMenu)builder.getComponent(0)).add(newclass);
		JMenuItem newclassedit=new JMenuItem("Edit "+str);
		newclassedit.setActionCommand("e"+str);
		newclassedit.addActionListener(hearer);
		((JMenu)builder.getComponent(1)).add(newclassedit, ((JMenu)builder.getComponent(1)).getComponents().length );
	}
	public void popupBuild(){
	    builder = new JPopupMenu();
	    JMenu submenu= new JMenu("New Mob Instance");
	    JMenu subtwo= new JMenu("Edit Mob Characteristics");
	    JMenuItem editGoblin= new JMenuItem("Edit Goblin");
	    editGoblin.setActionCommand("eGoblin");
	    editGoblin.addActionListener(hearer);
	    JMenuItem floor= new JMenuItem("Floor");
	    floor.setActionCommand("floor");
	    floor.addActionListener(hearer);
	    JMenuItem rock= new JMenuItem("Rock");
	    rock.setActionCommand("rock");
	    rock.addActionListener(hearer);
	    submenu.add(floor);
	    submenu.add(rock);
	    JMenuItem newClass= new JMenuItem("New Class");
	    newClass.setActionCommand("new");
	    newClass.addActionListener(hearer);
	    JMenuItem menuItem = new JMenuItem("Goblin");
	    menuItem.setActionCommand("Goblin");
	    menuItem.addActionListener(hearer);
	    subtwo.add(editGoblin);
	    subtwo.add(newClass);
	    submenu.add(menuItem);
	    JMenuItem save= new JMenuItem("Save");
	    save.setActionCommand("save");
	    save.addActionListener(hearer);
	    builder.add(submenu);
	    builder.add(subtwo);
	}



}
