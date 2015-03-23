package superpaketet;

import java.sql.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.xml.ws.Holder;

public class Menu extends JFrame{
	
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
	static JPanel browseTab 			= new JPanel(new BorderLayout()); 
	static JPanel editTab	 			= new JPanel(new BorderLayout()); 
	static JScrollBar scrollBar			= new JScrollBar(JScrollBar.VERTICAL, 30, 40, 0, 300);
	static JTabbedPane tab				= new JTabbedPane();	
	
	
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
		tab.add("Browse", browseTab);
		tab.add("Edit", editTab);
		browseTab.add(browsingMenu, BorderLayout.CENTER);
		editTab.add(editingMenu, BorderLayout.CENTER);
		backgroundLeft.add(tab, BorderLayout.PAGE_START);
		backgroundRight.add(resultPane, BorderLayout.CENTER);
		resultPane.setVisible(true);
		backgroundLeft.setVisible(true);
		backgroundRight.setVisible(true);
		window.setVisible(true);
		window.pack();
	}
	
	public void actionPerformed(ActionEvent e) {}
	
	
	public static void listOfNames(ArrayList<String> listData){
		JList<String> list = new JList(listData.toArray());
		resultPane.removeAll();
		resultPane.updateUI();
		resultPane.add(scrollBar, BorderLayout. LINE_END);
		resultPane.add(list, BorderLayout.CENTER);
		window.pack();
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


class BrowsingPanel extends JPanel{
	
	JButton b1, b3, b4, b5, b6;
	JTextField textField = new JTextField();
	JComboBox cb; 
	
	public BrowsingPanel(){
	
		b1 = new JButton("GO");
		b1.addActionListener(new ActionListener() {
		 	public void actionPerformed(ActionEvent event) {
				String text = textField.getText();
				ArrayList<String> memberList = Menu.dbh.getMember(text);
				Menu.listOfNames(memberList);
			} });
		setLayout(new GridLayout(3, 1));
		String [] cbOptions = {"", "Search member", "Search teamleader", "List all members", "Search team"};
		cb = new JComboBox(cbOptions);
	
		add(cb);
		
		cb.addItemListener(new ItemListener(){
			public void itemStateChanged(ItemEvent e){
				removeAll();
				add(cb);
				if(e.getItem().equals("Search member"))
				{
					
			
					add(textField);
					add(b1);
					
				}else if(e.getItem().equals("Search teamleader")){
					
				}else if(e.getItem().equals("List all members")){
					
				}else if(e.getItem().equals("Search team")){
					
				}
			updateUI();
			}
		});
	}

}

class EditingPanel extends JPanel{
	
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
	}

}

		/* addActionListener(new ActionListener() {
		 	public void actionPerformed(ActionEvent event) {
				Menu.listOfNames();
				Menu.resultPane.setVisible(true);
				//mainMenuPane.setVisible(false);
				//frame.pack();
				System.out.println("Edit");
			} }); 
			 */

/* browsingMenu.b3.addActionListener(new ActionListener() {
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
				resultPane.setVisible(false);
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
		 */