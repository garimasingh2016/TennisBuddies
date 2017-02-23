import java.util.ArrayList;


public class Player 
{
	//FIELDS
	private String myName;
	private String myEmail;
	private int myAge;
	private int mySkillLevel;
	private ArrayList<String> myDaysAvailable;
	private String myPassword;
	
	//CONSTRUCTOR
	public Player(String name, String email, int age, int skillLevel, ArrayList<String> daysAvailable, String password)
	{
		this.myName = name;
		this.myEmail = email;
		this.myAge = age;
		this.mySkillLevel = skillLevel;
		this.myDaysAvailable = daysAvailable;
		this.myPassword = password;
	}

	//METHODS
	public String getName() {
		return myName;
	}

	public void setName(String name) {
		this.myName = name;
	}
	
	public String getEmail() {
		return myEmail;
	}

	public void setEmail(String email) {
		this.myEmail = email;
	}

	public int getAge() {
		return myAge;
	}

	public void setAge(int age) {
		this.myAge = age;
	}

	public int getSkillLevel() {
		return mySkillLevel;
	}

	public void setSkillLevel(int skillLevel) {
		this.mySkillLevel = skillLevel;
	}

	public ArrayList<String> getDaysAvailable() {
		return myDaysAvailable;
	}

	public void setDaysAvailable(ArrayList<String> daysAvailable) {
		this.myDaysAvailable = daysAvailable;
	}
	
	public String getPassword() {
		return myPassword;
	}
	
	public void setPassword(String newPassword){
		this.myPassword = newPassword;
	}
	
	/**
	 * @param x the player to be compared to this one
	 * @return whether the two players have the same days available or not
	 */
	public boolean hasMatchingDays(Player x)
	{
		//Add code to only iterate through the shorter one
		for (String s : this.getDaysAvailable())
		{
			if (x.getDaysAvailable().contains(s))
			{
				return true;
			}
		}
		
		return false;
	}
	
	/**
	 * @param x the player to be compared to this one
	 * @return an array of the days that both players are available
	 */
	public ArrayList<String> getMatchingDays(Player x)
	{
		if (this.hasMatchingDays(x))
		{
			ArrayList<String> xDays = x.getDaysAvailable();
			ArrayList<String> pDays = this.getDaysAvailable();
			
			if (xDays.size() >= pDays.size())
			{
				return this.makeDaysArray(xDays, pDays);
			}
			
			else
			{
				return this.makeDaysArray(pDays, xDays);
			}
		}
		
		return null;
	}
	
	/**
	 * @param larger the array of days available that is longer
	 * @param smaller the array of days available that is shorter
	 * @return an array of the days that both players are available
	 */
	private ArrayList<String> makeDaysArray(ArrayList<String> larger, ArrayList<String> smaller)
	{
		ArrayList<String> days = new ArrayList<String>();
		
		for (String day : smaller)
		{
			if (larger.contains(day))
			{
				days.add(day);
			}
		}
		
		return days;
	}
}
