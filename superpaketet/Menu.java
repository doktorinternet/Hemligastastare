package superpaketet;

import superpaketet.DatabaseHandler;
import java.sql.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.xml.ws.Holder;
import java.text.*;
import javax.swing.text.NumberFormatter;


public class Menu extends JFrame{
	
	GridLayout mainMenuGrid 			= new GridLayout(1, 2, 4, 4);
	GridLayout subMenuGrid 				= new GridLayout(3, 2, 4, 4);
	
	static BrowsingPanel browsingMenu 	= new BrowsingPanel();
	static EditingPanel editingMenu 	= new EditingPanel();
	static DatabaseHandler dbh 			= new DatabaseHandler();
	static Menu window 					= new Menu("Menysystem");
	
	static JPanel backgroundLeft 		= new JPanel(new BorderLayout());
	static JPanel backgroundRight 		= new JPanel(new BorderLayout());
	static JPanel resultPane			= new JPanel(new BorderLayout());
	static JScrollPane resultView 		= new JScrollPane(); 
	static JPanel browseTab 			= new JPanel(new BorderLayout()); 
	static JPanel editTab	 			= new JPanel(new BorderLayout()); 
	static JTabbedPane tab				= new JTabbedPane();	
	
	
	public Menu(String title){
		
		setTitle(title);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(725,400);
		setLocationRelativeTo(null);
		setVisible(true);
	
	}
	
	private static void createAndShowGUI(){

		
		Container contentPane = window.getContentPane();
		backgroundLeft.setPreferredSize(new Dimension(460,400));
		contentPane.add(backgroundLeft, BorderLayout.LINE_START);
		contentPane.add(backgroundRight, BorderLayout.LINE_END);
		tab.add("Browse", browseTab);
		tab.add("Edit", editTab);
		browseTab.add(browsingMenu, BorderLayout.CENTER);
		editTab.add(editingMenu, BorderLayout.CENTER);
		backgroundLeft.add(tab, BorderLayout.PAGE_START);
		
		resultView.setPreferredSize(new Dimension(260,400));
		backgroundRight.add(resultView, BorderLayout.CENTER);
		resultView.setViewportView(resultPane);
		resultPane.setVisible(true);
		backgroundLeft.setVisible(true);
		backgroundRight.setVisible(true);
		window.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e) {}
	
	
	public static void listOfNames(ArrayList<String> listData){
		JList<String> list = new JList(listData.toArray());
		resultPane.removeAll();
		resultPane.add(list, BorderLayout.CENTER);
		resultPane.updateUI();
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
	
	JButton b1, b2, b3;
	JTextField textField = new JTextField();
	JComboBox cb; 
	
	public BrowsingPanel(){
	
		b1 = new JButton("Go");
		b2 = new JButton("Go");
		b3 = new JButton("Go");

		setLayout(new GridLayout(12, 1));
		String [] cbOptions = {"Select:", "Search member", "Search teamleader", "List all members", "Search info on team"};
		cb = new JComboBox(cbOptions);
	
		add(cb);
		
		cb.addItemListener(new ItemListener(){
			public void itemStateChanged(ItemEvent e){
				removeAll();
				add(cb);
				if(e.getItem().equals("Search member"))
				{
					b1.addActionListener(new ActionListener() {
		 				public void actionPerformed(ActionEvent event) {
							String text = textField.getText();
							Menu.listOfNames(Menu.dbh.getMember(text));
					} });
					addLabeledComponent("Last name:", textField);
					add(b1);
					
				}else if(e.getItem().equals("Search teamleader")){
					
					b1.addActionListener(new ActionListener() {
		 				public void actionPerformed(ActionEvent event) {
							String text = textField.getText();
							Menu.listOfNames(Menu.dbh.getTeamcoaches(text));
					} });					
					addLabeledComponent("Team name:", textField);
					add(b1);

				}else if(e.getItem().equals("List all members")){
					
					b2.addActionListener(new ActionListener() {
		 				public void actionPerformed(ActionEvent event) {
							Menu.listOfNames(Menu.dbh.listNamesByID());
					} });

					b3.addActionListener(new ActionListener() {
		 				public void actionPerformed(ActionEvent event) {
							Menu.listOfNames(Menu.dbh.listNamesByFamilyName());
					} });
					addLabeledComponent("List by ID:", b2);
					addLabeledComponent("List by last name:", b3);

				}else if(e.getItem().equals("Search info on team")){
					
					b1.addActionListener(new ActionListener() {
		 				public void actionPerformed(ActionEvent event) {
							String text = textField.getText();
							Menu.listOfNames(Menu.dbh.searchTeamInfo(text));
					} });					
					addLabeledComponent("Team name:", textField);
					add(b1);				
				}
			updateUI();
			}
		});
	}

	public void addLabeledComponent(String label, Component comp){
		JPanel p = new JPanel(new GridLayout(1,2));
		p.add(new JLabel ("           " + label));
		p.add(comp);
		add(p);
	}

}

class EditingPanel extends JPanel{
	
	JButton b1, b2, b3, b4, b5, b6;
	JButton submitButton = new JButton("Submit");
	JButton submitButton2 = new JButton("Check existance");
	GridLayout subMenuGrid = new GridLayout(12,1);
	JFormattedTextField inputField1;
	JTextField inputField2 = new JTextField();
	JTextField inputField3 = new JTextField();
	JTextField inputField4 = new JTextField();
	JTextField inputField5 = new JTextField();
	JTextField inputField6 = new JTextField();
	JFormattedTextField birthField = new JFormattedTextField(new SimpleDateFormat("dd/MM/yyyy"));
	JFormattedTextField joinField = new JFormattedTextField(new SimpleDateFormat("dd/MM/yyyy"));
	JComboBox cb;
	JCheckBox active = new JCheckBox();
	JComboBox roleBox;
	JPanel idPanel = new JPanel(new GridLayout(1,2));
	String idPanelInput;


	
	public EditingPanel(){

		NumberFormat numberFormat = NumberFormat.getInstance();
    	NumberFormatter formatter = new NumberFormatter(numberFormat);
    	formatter.setValueClass(Integer.class);

    	inputField1 = new JFormattedTextField(formatter);

		setLayout(subMenuGrid);
		String [] roles = {"player", "coach", "parent"};
		roleBox = new JComboBox(roles);
		String [] cbOptions = {
			"Select:", 
			"Create new member", 
			"Update existing member", 
			"Activate/deactivate member",
			"Update e-mail",
			"Update function",
			"Delete member"};	
		cb = new JComboBox(cbOptions);
		add(cb);

		cb.addItemListener(new ItemListener(){
			public void itemStateChanged(ItemEvent e){
				removeAll();
				add(cb);
				if(e.getItem().equals("Create new member")){
					idPanel = new JPanel(new GridLayout(1,2));
					idPanel.add(new JLabel ("           ID:"));
					idPanel.add(inputField1);
					add(idPanel);
					submitButton2.addActionListener(new ActionListener() {
			 			public void actionPerformed(ActionEvent event) {
							if(!Menu.dbh.checkMemberExistance(inputField1.getText())){
								idPanelInput = inputField1.getText();
								remove(idPanel);
								remove(submitButton2);
								add(labeledComponent("Given name:", inputField2));
								add(labeledComponent("Family name:", inputField3));
								add(labeledComponent("E-mail:", inputField4));
								add(labeledComponent("Gender:", inputField5));
								add(labeledComponent("Birth date:", birthField));
								add(labeledComponent("Member since:", joinField));
								add(labeledComponent("Active status:", active));
								add(labeledComponent("Role:", roleBox));
								add(labeledComponent("Team:", inputField6));
								submitButton.addActionListener(new ActionListener() {
					 				public void actionPerformed(ActionEvent event) {
										if(validMember()){
											String activeString;
											if(active.isSelected()){
												activeString = "1";
											}
											else{
												activeString = "0";
											}

											String roleString;
											if (roleBox.getSelectedItem().equals("player")){
												roleString = "0";
											}
											else if (roleBox.getSelectedItem().equals("coach")){
												roleString = "1";
											}
											else{
												roleString = "2";
											}

											Menu.dbh.addMember(idPanelInput, inputField2.getText(), inputField3.getText(),
															   inputField4.getText(), inputField5.getText(), birthField.getText(),
															   joinField.getText(), activeString, roleString, inputField6.getText());
										}
										else{
											JOptionPane.showMessageDialog(null, "All fields must have input", "Error", 0);
										}						
									} 
								});
								add(submitButton);
							}
							else{
								JOptionPane.showMessageDialog(null, "ID occupied", "Error", 0);
							}
						}		
					});
					add(submitButton2);
				}

				else if(e.getItem().equals("Update existing member")){

				}

				else if(e.getItem().equals("Activate/deactivate member")){

				}

				else if(e.getItem().equals("Update e-mail")){

				}

				else if(e.getItem().equals("Update function")){

				}

				else if(e.getItem().equals("Delete member")){

					add(labeledComponent("ID:", inputField1));
					submitButton.addActionListener(new ActionListener() {
		 				public void actionPerformed(ActionEvent event) {
		 					if(Menu.dbh.checkMemberExistance(inputField1.getText())){
		 						Menu.dbh.deleteMember(inputField1.getText());
		 					}
		 					else{
		 						JOptionPane.showMessageDialog(null, "No such ID", "Error", 0);
		 					}
		 				}
		 			});
		 			add(submitButton);		
				}
			}
		});
		

	}	

	public JPanel labeledComponent(String label, Component comp){
		JPanel p = new JPanel(new GridLayout(1,2));
		p.add(new JLabel ("           " + label));
		p.add(comp);
		return p;
	}

	public boolean validMember(){
		if (inputField1.getText().equals("") || inputField2.getText().equals("") || 
			inputField3.getText().equals("") || inputField4.getText().equals("") || 
			inputField5.getText().equals("") || inputField6.getText().equals("") ||
			birthField.getText().equals("")  || joinField.getText().equals("")){

			return false;
		}
		else{

			return true;
		}
	}

}
