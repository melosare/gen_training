
public class Employee extends Person {

	private static int employeeCount = 0;
	private int employeeNumber;
	private Date dateJoined;
	private double salary;
	private Employee supervisor;

	public Employee(String fullName, String joiningDate, double startingSalary){
		super(fullName);
		employeeCount++;
		employeeNumber = employeeCount;
		dateJoined = new Date(joiningDate);
		salary = startingSalary;
		supervisor = null;
	}

	public Employee(String fullName,  String joiningDate){
		this(fullName, joiningDate, 0.0);
	}

	public Employee(Employee emp){ //copy constructor
		super(emp.getName());
		employeeNumber = emp.employeeNumber;
		dateJoined = new Date(emp.dateJoined);
		salary = emp.salary;
		supervisor = emp.supervisor;

	}

	public void setSalary(double newSalary){
		salary = newSalary;
	}

	public double getSalary(){
		return salary;
	}

	public void setSupervisor(Employee newSupervisor){
		supervisor = newSupervisor;
	}

	public static int getEmployeeCount(){
		return employeeCount;
	}

	public int getEmployeeNumber(){
		return employeeNumber;
	}

	public String getSupervisorName(){
		return (supervisor == null ? "CEO (has no supervisor)." : supervisor.getName().toString());
	}

	public String toString(){
		return ("Employee: " + getEmployeeNumber() + "\n" + super.toString() +
				"\nJoined on: " + dateJoined.toString() +
				"\nSupervisor: " + getSupervisorName() +
				"\nSalary: " + getSalary());
	}

}
