
import java.util.StringTokenizer;

public class Contact {
	private Name name;
	private String phone;
	private String email;

	public Contact(String name, String phone, String email){
		this.name = new Name(name);
		this.phone = phone;
		this.email = email;
	}

	public Contact(String name, String phone){
		this(name, phone, null);
	}

	public Contact(String name){
		this(name, null);
	}

	public void setName(String name){
		this.name = new Name(name);
	}

	public Name getName(){
		return name;
	}

	public void setPhone(String ph){
		phone = ph;
	}

	public String getPhone(){
		return phone;
	}

	public void setEmail(String em){
		email = em;
	}

	public String getEmail(){
		return email;
	}

	public String toString(){
		return String.format("Name: %s\nPhone: %s\nEmail: %s\n", getName().toString(), getPhone(), getEmail());
	}

	public boolean lessThan(Contact otherContact){

		int last = this.name.lastName.compareTo(otherContact.name.lastName);
		if(last < 0) return true;
		if(last == 0){
			int first = this.name.firstName.compareTo(otherContact.name.firstName);
			if(first < 0) return true;
		}
		return false;
	}

	class Name {

		private String firstName;
		private String middleName;
		private String lastName;

		public Name( String name ) {
			name = name.trim();
			StringTokenizer nameToken = new StringTokenizer( name, " ,." );
			setFirstName(nameToken.nextToken());
			if( nameToken.countTokens() > 1 )
				setMiddleName(nameToken.nextToken());
			else
				middleName = "";
			setLastName(nameToken.nextToken());
		}

		public Name(Name name) { //copy constructor
			firstName  = name.firstName;
			middleName = name.middleName;
			lastName   = name.lastName;
		}

		public String getFirstName(){
			return firstName;
		}

		public void setFirstName( String first ) {
			firstName  = first.substring(0, 1).toUpperCase() + first.substring(1).toLowerCase();
		}

		public String getMiddleName(){
			return middleName;
		}

		public void setMiddleName( String middle ) {
			middleName  = middle.substring(0, 1).toUpperCase() + middle.substring(1).toLowerCase();
		}

		public String getLastName(){
			return lastName;
		}

		public void setLastName( String last ) {
			lastName  = last.substring(0, 1).toUpperCase() + last.substring(1).toLowerCase();
		}

		public String toString() {
			return getLastName() + ", " + getFirstName() + (getMiddleName().equals("") ? "" : " " + getMiddleName().charAt(0) + ".");
		}
	}
}
