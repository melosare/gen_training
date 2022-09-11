
public class Person {

	private Name name;
	private Person spouse;

	public Person(String fullName){
		name = new Name(fullName);
		spouse = null;
	}

	public Person(Name name){
		this.name = name;
		spouse = null;
	}

	public Person(Person aPerson){//copy constructor
		name = new Name(aPerson.name);
		spouse = aPerson.spouse;
	}

	public Name getName(){
		return name;
	}

	public void setSpouse(Person sp){
		spouse = sp;
		sp.spouse = this;
	}

	public String toString(){
		return "Name: " + name +
				(spouse == null ? "" : "\nMarried to: " + spouse.name);
	}
}
