
import java.util.ArrayList;
import java.util.Scanner;
import java.util.StringTokenizer;

public class ContactList implements Sortable{

	private ArrayList<Contact> contactList;

	public ContactList(){
		contactList = new ArrayList<Contact>();
	}

	public void addContact(){

		Scanner kb = new Scanner(System.in);
		String ans;
		do{
			System.out.println("\nEnter full name:");
			String name = kb.nextLine();
			System.out.println("\nEnter phone number:");
			String phone = kb.nextLine();
			System.out.println("\nEnter email address:");
			String email = kb.nextLine();
			contactList.add(new Contact(name, phone, email));
			System.out.println("Add another contact? [Y/N]: ");
			ans = kb.nextLine();
		} while(ans.equalsIgnoreCase("Y"));
	}

	public void searchContact(){
		if(contactList.isEmpty()){
			System.out.println("The list is empty!");
		}
		else{
			Scanner kb = new Scanner(System.in);
			System.out.println("\nEnter name, phone or email, to search for contact:");
			String str = kb.nextLine();
			boolean found = false;
			for(Contact c : contactList){
				if(c.getName().toString().toLowerCase().contains(str.toLowerCase()) ||
						c.getPhone().contains(str) ||
						c.getEmail().toLowerCase().contains(str.toLowerCase())){
					System.out.println("Found contact:\n" + c.toString());
					found = true;
				}
			}

			if(!found)
				System.out.println("Found no such contact.");
		}
	}

	public void editContact(){
		if(contactList.isEmpty()){
			System.out.println("The list is empty!");
		}
		else{
			Scanner kb = new Scanner(System.in);
			System.out.println("\nEnter name, phone or email, to edit contact:");
			String str = kb.nextLine();
			boolean found = false;
			for(int index = 0; index < contactList.size(); index++){
				if(contactList.get(index).getName().toString().toLowerCase().contains(str.toLowerCase()) ||
						contactList.get(index).getPhone().contains(str) ||
						contactList.get(index).getEmail().toLowerCase().contains(str.toLowerCase())){
					System.out.println("Found contact:\n" + contactList.get(index).toString());
					System.out.println("Edit this contact? [Y/N]:");
					String ans = kb.next();
					if(ans.equalsIgnoreCase("Y")){
						kb.nextLine();
						System.out.println("Enter full name [Press <Enter> to skip]:");
						String input = kb.nextLine();
						if(!input.equals(""))
							contactList.get(index).setName(input);
						System.out.println("Enter phone number [Press <Enter> to skip]:");
						input = kb.nextLine();
						if(!input.equals(""))
							contactList.get(index).setPhone(input);
						System.out.println("Enter email address [Press <Enter> to skip]:");
						input = kb.nextLine();
						if(!input.equals(""))
							contactList.get(index).setEmail(input);
					}
					found = true;
				}
			}

			if(!found)
				System.out.println("Found no such contact.");
		}
	}

	public void deleteContact(){
		if(contactList.isEmpty()){
			System.out.println("The list is empty!");
		}
		else{
			Scanner kb = new Scanner(System.in);
			System.out.println("\nEnter name, phone or email, to delete contact:");
			String str = kb.nextLine();
			boolean found = false;
			for(int index = 0; index < contactList.size(); index++){
				if(contactList.get(index).getName().toString().toLowerCase().contains(str.toLowerCase()) ||
						contactList.get(index).getPhone().contains(str) ||
						contactList.get(index).getEmail().toLowerCase().contains(str.toLowerCase())){
					System.out.println("Found contact:\n" + contactList.get(index).toString());
					System.out.println("Delete this contact? [Y/N]:");
					String ans = kb.next();
					if(ans.equalsIgnoreCase("Y")){
						contactList.remove(index);
					}
					found = true;
				}
			}

			if(!found)
				System.out.println("Found no such contact.");
		}
	}

	public void displayContacts(){
		if(contactList.isEmpty()){
			System.out.println("The list is empty!");
		}
		else{
			Scanner kb = new Scanner(System.in);
			System.out.println("Display all contacts? [Y/N]:");
			String ans = kb.next();
			if(ans.equalsIgnoreCase("Y")){
				for(Contact c : contactList){
					System.out.println(c.toString());
				}
			}
		}
	}

	public void sort(){
		for(int i = 0; i < contactList.size(); i++){
			for(int j = 0; j < contactList.size() - 1; j++){
				if(contactList.get(j+1).lessThan(contactList.get(j)))
					swap(contactList.get(j), contactList.get(j+1));
			}
		}

		System.out.println("The list is sorted!");
	}

	private void swap(Contact x, Contact y){
		Contact z;
		z = x;
		x = y;
		y = z;
	}
}
