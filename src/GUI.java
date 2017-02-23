import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Label;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JSlider;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;


public class GUI {
	//Fields
	private final String[] DAYS = new String[]{"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};
	private Matcher myMatcher;
	private Encrypter myEncrypter;
	private JPanel layout;
	private final String START = "Start";
	private final String CREATEACCOUNT = "Create Account";
	private final String EDITPROFILE = "Edit Profile";
	private final String SEARCH = "Search";
	
	
	
	//Constructor
	public GUI() {		
		myMatcher = new Matcher();
		myEncrypter = new Encrypter();
		
		final JFrame frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setPreferredSize(new Dimension(600,400));
		frame.setResizable(false);

		//Add cards to the layout, each card is a screen
		layout = new JPanel(new CardLayout());
		frame.getContentPane().add(layout);

		layout.add(this.makeStartScreen(), START);
		
		frame.pack();
		frame.setVisible(true);	
	}
	
	//METHODS
	private JPanel makeStartScreen()

	{
		//Start Screen
		JPanel startScreen = new JPanel();

		Box outerBox = Box.createVerticalBox();
		startScreen.add(outerBox);

		//Label at top
		outerBox.add(new Label("Login!", Label.CENTER));

		//Username cluster
		Box name = Box.createHorizontalBox();
		name.add(new Label("Username", Label.LEFT));
		JTextField nameText = new JTextField();
		name.add(nameText);
		outerBox.add(name);
		
		//Password cluster
		Box pass = Box.createHorizontalBox();
		pass.add(new Label("Password", Label.LEFT));
		JPasswordField passText = new JPasswordField();
		pass.add(passText);
		outerBox.add(pass);

		//Submit button
		JButton submit = new JButton("Submit");
		outerBox.add(submit);
		
		submit.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				Player temp = myMatcher.findPlayer(nameText.getText().trim());
				if (temp != null && Arrays.equals(myEncrypter.decrypt(temp.getPassword()), passText.getPassword()))
				{
					layout.add(GUI.this.makeSearchScreen(), SEARCH);
					CardLayout cl = (CardLayout) layout.getLayout();
					cl.show(layout, SEARCH);
					myMatcher.setCurrentPlayer(temp);
				}
			}
		});

		//Go to Create an Account
		JButton makeAccount = new JButton("Make an Account");
		outerBox.add(makeAccount);
		
		makeAccount.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				layout.add(GUI.this.makeCreateAccountScreen(), CREATEACCOUNT);
				CardLayout cl = (CardLayout) layout.getLayout();
				cl.show(layout, CREATEACCOUNT);
			}
		});
		
		return startScreen;
	}

	private JPanel makeCreateAccountScreen()
	{
		//Create an Account
		JPanel makeAccount = new JPanel();

		Box outerBox = Box.createVerticalBox();
		makeAccount.add(outerBox);

		//Username cluster
		Box newName = Box.createHorizontalBox();
		newName.add(new Label("Username", Label.LEFT));
		JTextField nameText = new JTextField();
		newName.add(nameText);
		outerBox.add(newName);

		//Password cluster
		Box newPass = Box.createHorizontalBox();
		newPass.add(new Label("Password", Label.LEFT));
		JPasswordField pass = new JPasswordField();
		newPass.add(pass);
		outerBox.add(newPass);
		
		//Email cluster
		Box emailBox = Box.createHorizontalBox();
		emailBox.add(new Label("Email", Label.LEFT));
		JTextField email = new JTextField();
		emailBox.add(email);
		outerBox.add(emailBox);

		//Age cluster
		Box age = Box.createHorizontalBox();
		age.add(new Label("Age", Label.LEFT));
		JTextField ageVal = new JTextField();
		age.add(ageVal);
		outerBox.add(age);
		
		//Skill Level cluster
		Box skillLevel = Box.createHorizontalBox();
		skillLevel.add(new Label("Skill Level", Label.LEFT));
		
		JSlider selectSkill = new JSlider(1, 5);
		selectSkill.setMajorTickSpacing(1);
		selectSkill.setPaintTicks(true);
		selectSkill.setPaintLabels(true);
		
		skillLevel.add(selectSkill);
		outerBox.add(skillLevel);
		
		//Days Available cluster
		Box days = Box.createHorizontalBox();
		days.add(new Label("Days Available", Label.LEFT));
		JList daysSelected = this.makeListOfDays();
		days.add(daysSelected);
		outerBox.add(days);
		 
		//Submit button
		JButton submit = new JButton("Submit");
		outerBox.add(submit);
		
		submit.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				String name = nameText.getText().trim();
				String emailText = email.getText().trim();
				int age = Integer.parseInt(ageVal.getText().trim());
				int skillLevel = selectSkill.getValue();
				
				int[] temp = daysSelected.getSelectedIndices();
				ArrayList<String> d = new ArrayList<String>();
				for (int x : temp)
				{
					d.add(DAYS[x]);
				}
				String password = myEncrypter.encrypt(pass.getPassword());
				
				Player p = new Player(name, emailText, age, skillLevel, d, password);
				ArrayList<Player> x = myMatcher.getAllPlayers().get(skillLevel);
				x.add(p);
				myMatcher.getAllPlayers().put(skillLevel, x);
				myMatcher.setCurrentPlayer(p);
				
				layout.add(GUI.this.makeSearchScreen(), SEARCH);
				CardLayout cl = (CardLayout) layout.getLayout();
				cl.show(layout, SEARCH);
			}
		});
		
		return makeAccount;
	}
	
	private JList<String> makeListOfDays()
	{
		JList<String> daysList = new JList<String>(DAYS);
		daysList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
		daysList.setLayoutOrientation(JList.HORIZONTAL_WRAP);
		daysList.setVisibleRowCount(-1);
		
		return daysList;
	}

	private JPanel makeEditProfileScreen()
	{
		//Edit Profile Screen
		JPanel editProfile = new JPanel();
		
		Box outerBox = Box.createVerticalBox();
		editProfile.add(outerBox);
		
		//Label at top
		outerBox.add(new Label("Edit Profile", Label.CENTER));
		
		Player current = myMatcher.getCurrentPlayer();
		
		//Username cluster
		Box newName = Box.createHorizontalBox();
		newName.add(new Label("Username", Label.LEFT));
		JTextField name = new JTextField();
		name.setText(current.getName());
		newName.add(name);
		outerBox.add(newName);

		//Password cluster
		Box newPass = Box.createHorizontalBox();
		newPass.add(new Label("Password", Label.LEFT));
		newPass.add(new JPasswordField());
		outerBox.add(newPass);
		
		//Email cluster
		Box newEmail = Box.createHorizontalBox();
		newEmail.add(new Label("Email", Label.LEFT));
		JTextField email = new JTextField();
		email.setText(current.getEmail());
		newEmail.add(email);
		outerBox.add(newEmail);

		//Age cluster
		Box age = Box.createHorizontalBox();
		age.add(new Label("Age", Label.LEFT));
		JTextField ageVal = new JTextField();
		ageVal.setText(Integer.toString(current.getAge()));
		age.add(ageVal);
		outerBox.add(age);
		
		//Skill Level cluster
		Box skillLevel = Box.createHorizontalBox();
		skillLevel.add(new Label("Skill Level", Label.LEFT));
		
		JSlider selectSkill = new JSlider(1, 5);
		selectSkill.setMajorTickSpacing(1);
		selectSkill.setPaintTicks(true);
		selectSkill.setPaintLabels(true);
		selectSkill.setValue(current.getSkillLevel());
		
		skillLevel.add(selectSkill);
		outerBox.add(skillLevel);
		
		//Days Available cluster
		Box days = Box.createHorizontalBox();
		days.add(new Label("Days Available", Label.LEFT));
		JList daysSelected = this.makeListOfDays();	
		days.add(daysSelected);
		outerBox.add(days);
		 
		//Submit button
		JButton submit = new JButton("Submit");
		outerBox.add(submit);
		
		submit.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				String nameText = name.getText().trim();
				String emailText = email.getText().trim();
				int age = Integer.parseInt(ageVal.getText());
				int skillLevel = selectSkill.getValue();
				
				int[] temp = daysSelected.getSelectedIndices();
				ArrayList<String> d = new ArrayList<String>();
				for (int x : temp)
				{
					d.add(DAYS[x]);
				}
				
				current.setName(nameText);
				current.setEmail(emailText);
				current.setAge(age);
				current.setSkillLevel(skillLevel);
				current.setDaysAvailable(d);
				
				layout.add(GUI.this.makeSearchScreen(), SEARCH);
				CardLayout cl = (CardLayout) layout.getLayout();
				cl.show(layout, SEARCH);
			}
		});
		
		return editProfile;
	}

	private JPanel makeSearchScreen()
	{
		JPanel searchScreen = new JPanel();
		
		Box outerBox = Box.createVerticalBox();
		searchScreen.add(outerBox);
		
		//Buttons cluster
		Box buttons = Box.createHorizontalBox();
		JButton match = new JButton("Find me a buddy!");
		buttons.add(match);
		JButton editProfile = new JButton("Edit Profile");
		buttons.add(editProfile);
		outerBox.add(buttons);
		
		//Matches cluster
		Box matches = Box.createVerticalBox();
		matches.add(new Label("Matches!", Label.CENTER));
		
		JTextPane matchesDisplay = new JTextPane();
		matchesDisplay.setEditable(false);
		matches.add(matchesDisplay);
		outerBox.add(matches);
		
		//Logout cluster
		Box back = Box.createHorizontalBox();
		JButton logout = new JButton("Logout");
		back.add(logout);
		outerBox.add(back);
		
		match.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{	
				ArrayList<Player> sameDays = myMatcher.matchPlayer(myMatcher.getCurrentPlayer());
				String t = "";
				for (Player p : sameDays)
				{
					t += p.getName() + "\t" + p.getEmail() + "\n";
				}
				matchesDisplay.setText(t);
			}
		});
		
		editProfile.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				layout.add(GUI.this.makeEditProfileScreen(), EDITPROFILE);
				CardLayout cl = (CardLayout) layout.getLayout();
				cl.show(layout, EDITPROFILE);
			}
		});
		
		logout.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent e)
			{
				myMatcher.setCurrentPlayer(null);
				
				CardLayout cl = (CardLayout) layout.getLayout();
				cl.show(layout, START);
			}
		});
		
		return searchScreen;
	}
	
	public static void main(String[] args)
	{
		SwingUtilities.invokeLater(new Runnable() {
			public void run() { new GUI(); }
		});
	}
}