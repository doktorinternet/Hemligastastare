package superpaketet;

import java.sql.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.xml.ws.Holder;

public class Menu extends JFrame implements ActionListener{
	
	GridLayout mainMenuGrid 			= new GridLayout(1, 2, 4, 4);
	GridLayout subMenuGrid 				= new GridLayout(3, 2, 4, 4);
	
	static MainPanel mainMenu 			= new MainPanel();
	static BrowsingPanel browsingMenu 	= new BrowsingPanel();
	static EditingPanel editingMenu 	= new EditingPanel();
	static DatabaseHandler dbh 			= new DatabaseHandler();
	static Menu window 					= new Menu("Menysystem");
	
	static JPanel backgroundLeft 		= new JPanel(new BorderLayout());
	static JPanel backgroundRight 		= new JPanel(new BorderLayout());
	static JPanel resultPane 			= new JPanel(new BorderLayout()); 
	static JScrollBar scrollBar				= new JScrollBar(JScrollBar.VERTICAL, 30, 40, 0, 300);
	
	
	public Menu(String title){
		
		setTitle(title);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(350,160);
		setLocationRelativeTo(null);
		setVisible(true);
	
	}
	
	private static void createAndShowGUI(){

		
		Container contentPane = window.getContentPane();
		contentPane.add(backgroundLeft, BorderLayout.LINE_START);
		contentPane.add(backgroundRight, BorderLayout.LINE_END);
		backgroundLeft.add(mainMenu, BorderLayout.PAGE_START);
		backgroundLeft.add(browsingMenu, BorderLayout.LINE_START);
		backgroundLeft.add(editingMenu, BorderLayout.LINE_END);
		backgroundRight.add(resultPane, BorderLayout.CENTER);
		browsingMenu.setVisible(false);
		editingMenu.setVisible(false);
		backgroundLeft.setVisible(true);
		backgroundRight.setVisible(true);
		window.setVisible(true);
		browsingMenu.b3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				listOfNames();
				resultPane.setVisible(true);
				//mainMenuPane.setVisible(false);
				browsingMenu.setVisible(false);
				window.pack();
				System.out.println("Edit");
			} });
		
		mainMenu.edit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				browsingMenu.setVisible(false);
				editingMenu.setVisible(true);
				window.pack();
				System.out.println("Edit");
			} });
		
		mainMenu.browse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				resultPane.setVisible(false);
				editingMenu.setVisible(false);
				browsingMenu.setVisible(true);
				window.pack();
				System.out.println("Browse");
			} });
		
		//frame.pack();
		/* JLabel ourLabel = new JLabel();
		ourLabel.setOpaque(true);
		ourLabel.setBackground(new Color(235, 245, 240));
		ourLabel.setPreferredSize(new Dimension(200, 20));
		*/		
		/* menu.addContentBrowsePane();
		menu.addContentEditPane(); */
	}
	
	public void actionPerformed(ActionEvent e) {}
	
	
	public static void listOfNames(){
		ArrayList<String> listData = dbh.listNames();
		JList<String> list = new JList(listData.toArray());
		resultPane.removeAll();
		resultPane.updateUI();
		resultPane.add(scrollBar, BorderLayout. LINE_END);
		resultPane.add(list, BorderLayout.CENTER);
	}
	
	public static void main(String[] args) {
		//Schedule a job for the event-dispatching thread:
		//creating and showing this application's GUI.
		javax.swing.SwingUtilities.invokeLater(new Runnable()  {
			public void run() {
				dbh.initDatabase();
				createAndShowGUI();
			}
        });
    }
}
class MainPanel extends JPanel implements ActionListener{
	
	JButton browse, edit;
	GridLayout mainMenuGrid = new GridLayout(1,2);
	
	public MainPanel(){
		
		setLayout(mainMenuGrid);
		browse = new JButton("Browse database");
/* 		browse.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				Menu.editingMenu.setVisible(false);
				Menu.browsingMenu.setVisible(true);
				Menu.menu.pack();
				System.out.println("Browse");
			} }); */
	
		edit = new JButton("Edit database");
/* 		edit.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				Menu.browsingMenu.setVisible(false);
				Menu.editingMenu.setVisible(true);
				Menu.menu.pack();
				System.out.println("Edit");
			} }); */
		
		add(browse);
		add(edit);
	}

	public void actionPerformed(ActionEvent e) {}
}

class BrowsingPanel extends JPanel implements ActionListener{
	
	JButton b1, b2, b3, b4, b5, b6;
	GridLayout subMenuGrid = new GridLayout(3,2);
	public BrowsingPanel(){
		
		setLayout(subMenuGrid);
		b1 = new JButton("Search");
		b1.setToolTipText("This is the first alternative of this menu.");
		//b1.addActionListener(this);
	
		b2 = new JButton("Search by ID");
		b2.setToolTipText("BEHOLD! This is the second alternative of this menu.");
		
		b3 = new JButton("Search by family name");
		/* b3.addActionListener(new ActionListener() {
		 	public void actionPerformed(ActionEvent event) {
				Menu.listOfNames();
				Menu.resultPane.setVisible(true);
				//mainMenuPane.setVisible(false);
				setVisible(false);
				//frame.pack();
				System.out.println("Edit");
			} }); */
			
		b4 = new JButton("List by ID");
	
		b5 = new JButton("List by family name");
	
		b6 = new JButton("Info about a team");
		
		add(b1);
		add(b2);
		add(b3);
		add(b4);
		add(b5);
		add(b6);
		//frame.setContentPane(browseMenuPane);
		//frame.getContentPane().remove(mainMenuPane);
		//frame.getContentPane().add(browseMenuPane, BorderLayout.CENTER);
		//browseMenuPane.setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {}
}

class EditingPanel extends JPanel implements ActionListener{
	
	JButton b1, b2, b3, b4, b5, b6;
	GridLayout subMenuGrid = new GridLayout(3,2);
	
	public EditingPanel(){
		
		setLayout(subMenuGrid);
		b1 = new JButton("Create new member");
		b2 = new JButton("Update existing member");
		b3 = new JButton("Activate/deactivate member");
		b4 = new JButton("Update mail");
		b5 = new JButton("Update function");
		b6 = new JButton("Delete member");
		add(b1);
		add(b2);
		add(b3);
		add(b4);
		add(b5);
		add(b6);
		//editMenuPane.setVisible(true);
		//frame.setContentPane(editMenuPane);
		//frame.getContentPane().add(editMenuPane, BorderLayout.CENTER);
	}
	
	public void actionPerformed(ActionEvent e) {}

}
/* 
class resultPanel extends JPanel implements ActionListener{

	public resultPanel(){
		
		
		
	}
	
	public void actionPerformed(ActionEvent e) {}

} */

/*	public void addContentBrowsePane(){
		
		b1 = new JButton("Search");
		//b1.setVerticalTextPosition(AbstractButton.CENTER);
		//b1.setHorizontalTextPosition(AbstractButton.LEADING);
		b1.setToolTipText("This is the first alternative of this menu.");
		//b1.addActionListener(this);
		
		b2 = new JButton("Search by ID");
		b2.setToolTipText("BEHOLD! This is the second alternative of this menu.");
		
		b3 = new JButton("Search by family name");
		b3.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent event) {
				listOfNames();
				resultPane.setVisible(true);
				//mainMenuPane.setVisible(false);
				browseMenuPane.setVisible(false);
				//frame.pack();
				System.out.println("Edit");
			} });
			
		b4 = new JButton("List by ID");
		
		b5 = new JButton("List by family name");
		
		b6 = new JButton("Info about a team");
		
		browseMenuPane.add(b1);
		browseMenuPane.add(b2);
		browseMenuPane.add(b3);
		browseMenuPane.add(b4);
		browseMenuPane.add(b5);
		browseMenuPane.add(b6);
		//frame.setContentPane(browseMenuPane);
		//frame.getContentPane().remove(mainMenuPane);
		//frame.getContentPane().add(browseMenuPane, BorderLayout.CENTER);
		//browseMenuPane.setVisible(true);
	}
	
	public void addContentEditPane(){
	
		b7 = new JButton("Create new member");
		b8 = new JButton("Update existing member");
		b9 = new JButton("Activate/deactivate member");
		b10 = new JButton("Update mail");
		b11 = new JButton("Update function");
		b12 = new JButton("Delete member");
		editMenuPane.add(b7);
		editMenuPane.add(b8);
		editMenuPane.add(b9);
		editMenuPane.add(b10);
		editMenuPane.add(b11);
		editMenuPane.add(b12);
		//editMenuPane.setVisible(true);
		//frame.setContentPane(editMenuPane);
		//frame.getContentPane().add(editMenuPane, BorderLayout.CENTER);
		
	}
	*/