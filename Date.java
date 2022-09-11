package assign3;

import java.util.Scanner;
import java.util.StringTokenizer;
public class Date 
{
	private int day;   // 1-31 based on month
	private int month; // 1-12
	private int year;  // 1000 - 3000
	
	private static final int[] daysInMonths = // days in each month
								{ 0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };
	private static final String[] nameOfMonths = // name of months
				{"", "January", "February", "March", "April", "May", "June",
						"July", "August", "September", "October", "November", "December"};
	
	public Date( String aDate )
	{
		aDate = aDate.trim();
		StringTokenizer dateToken = new StringTokenizer( aDate, " /,.-" );
		if( dateToken.countTokens() < 3 ) {
			System.out.println("Invalid date entered. Default date object \"1/1/1000\" is created.");
			
			day = 1;
			month = 1;
			year = 1000;
		}
		else {
			int theDay = Integer.parseInt(dateToken.nextToken());
			int theMonth = Integer.parseInt(dateToken.nextToken()); 
			int theYear = Integer.parseInt(dateToken.nextToken());
			
			try {		
				setMonth(theMonth);
			}		
			catch(InvalidMonthException me){
				System.out.println(me.getMessage());
				System.out.println("Invalid month. Enter a valid month (1 - 12): ");
				month = secondChance();
			}
			try{
				setYear(theYear);
			}
			catch(InvalidYearException ye){
				System.out.println(ye.getMessage());
				System.out.println("Invalid year. Enter a valid year (1000 - 3000): ");
				year = secondChance();
			}
			try{
				setDay(theDay);
			}
			catch(InvalidDayException de){
				System.out.println(de.getMessage());
				System.out.println("Invalid day for " + nameOfMonths[month] + " of " + year +". Enter a valid day: ");
				day = secondChance();
			}	
		}
	} // end Date constructor
	
	public Date()//no-argument constructor, creates default date
	{
		this("1/1/1000");
	}
	
	public int secondChance(){
		Scanner kb = new Scanner(System.in);
		int value = kb.nextInt();
		return value;
	}
	
	public String getDate() //return date as dd/mm/yyyy
	{
		return day + "/" + month + "/" + year;
	}
	
	public void setDay(int d)throws InvalidDayException{
		if(!validDay(d)) 
			throw new InvalidDayException();
		day = d;
	}
	
	public int getDay(){
		return day;
	}
	
	public void setMonth(int m)throws InvalidMonthException {
		if(!validMonth(m)) 
			throw new InvalidMonthException();
		month = m;
	}
	
	public int getMonth(){
		return month;
	}
	
	public void setYear(int y)throws InvalidYearException {
		if(!validYear(y)) 
			throw new InvalidYearException();
		year = y;
	}
	
	public int getYear(){
		return year;
	}
	
public boolean validDay(int d){
		
		if(month == 4 || month == 6 || month == 9 || month == 11){
			return (d >= 1 && d <= 30);
		}
		else if(month == 2){
			if(leapYear(year))
				return (d >= 1 && d <= 29);
			else
				return (d >= 1 && d <= 28);
		}
		else
			return (d >= 1 && d <= 31);
	}
	
	public boolean validMonth(int m){
		return (m >= 1 && m <= 12);
	}
	
	public boolean validYear(int y){
		return (y >= 1000 && y <= 3000);
	}
	
	public boolean leapYear(int y){
		
		return ((y % 4 == 0) && (y % 100 != 0))
				|| (y % 400 == 0);
	}
	
	public boolean earlierThan( Date otherDate )
	{
		if( this.year < otherDate.year ) 
			return true;
		
		else if( this.year == otherDate.year ) 
		{
			if( this.month < otherDate.month )
				return true;
			else if(this.month == otherDate.month ) {
				if( this.day < otherDate.day )
					return true;
			}
		}
		return false;
	}
	
	public boolean equals( Date otherDate )
	{
		if( this.year == otherDate.year &&
			this.month == otherDate.month &&
			this.day == otherDate.day )
		//then
			return true;
		//otherwise
		return false;
	}
	
	public String toString() 
	{
		return nameOfMonths[month] + " " + day + ", " + year;
	}
	
}
