
import java.util.Scanner;

public class TestContactList {

	public static void main(String[] args) {

		Scanner kb = new Scanner(System.in);
		ContactList contacList = new ContactList();
		boolean loop = true;
		while(loop){
			System.out.println("\nEnter the number of your choice: ");
			System.out.println("1. Add a contact:");
			System.out.println("2. Search for a contact:");
			System.out.println("3. Edit a contact:");
			System.out.println("4. Delete a contact:");
			System.out.println("5. Display all contacts:");
			System.out.println("6. Sort the contact list:");
			System.out.println("0. Quit");
			int choice = kb.nextInt();
			switch (choice){
				case 1 : contacList.addContact(); break;
				case 2 : contacList.searchContact(); break;
				case 3 : contacList.editContact(); break;
				case 4 : contacList.deleteContact(); break;
				case 5 : contacList.displayContacts(); break;
				case 6 : contacList.sort(); break;
				case 0 : System.out.println("Bye..."); loop = false;
			}
		}

		kb.close();
	}
}
