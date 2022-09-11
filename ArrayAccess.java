
import java.util.InputMismatchException;
import java.util.Scanner;

public class ArrayAccess {

	private int[] theArray;
	private int count;

	public ArrayAccess(int size){
		theArray = new int[size];
		count = 0;
		System.out.println("Created an integer array of size " + size);

	}

	public void testArray(){
		int choice = 0;
		do{
			choice = printMenu();
			switch (choice) {
			case 0 :
				System.out.println("Bye...");
				System.exit(0);
			case 1 :
				inputData();
				break;
			case 2:
				retriveData();
				break;
			case 3 :
				searchData();
				break;
			default :
				System.out.println("Wrong choice! Try again:");
			}
		} while(choice != 0);
	}

	public int printMenu(){
		System.out.println("\n   ****** MENU ******");
		System.out.println("(1)-Enter a value into the array.");
		System.out.println("(2)-Retrive a value using index.");
		System.out.println("(3)-Search for a value.");
		System.out.println("Enter your choice (0 to quit):");
		Scanner kb = new Scanner(System.in);
		return kb.nextInt();
	}

	public void inputData(){
		System.out.println("Enter an integer to store:");
		Scanner kb = new Scanner(System.in);
		try{
			int num = kb.nextInt();
			theArray[count] = num;
			count++;
		}
		catch ( InputMismatchException ime ){
			System.out.println("Input not a number. Please enter only integer values.");
		}
		catch ( ArrayIndexOutOfBoundsException aob ){
			System.out.println("Array may contain only " + theArray.length + "elements, and it is already full!");
		}
	}

	public void retriveData(){
		if(count == 0)
			System.out.println("The array is empty. Cannot search an empty array!");
		else {
			System.out.println("Enter the index:");
			Scanner kb = new Scanner(System.in);
			try{
				int i = kb.nextInt();
				if(i >= count || i < 0 ) throw new ArrayIndexOutOfBoundsException();
				System.out.println("Value at position " + i + " is " + theArray[i]);
			}
			catch ( InputMismatchException ime ){
				System.out.println("Index must be a number.");
			}
			catch ( ArrayIndexOutOfBoundsException aob ){
				System.out.println("Currently the index must be within 0 - " + (count - 1));
			}
		}
	}

	public void searchData(){
		if(count == 0)
			System.out.println("The array is empty. Cannot search an empty array!");
		else {
			System.out.println("Enter the value to search:");
			Scanner kb = new Scanner(System.in);
			try{
				int value = kb.nextInt();
				boolean found = false;
				for(int i = 0; i < count; i++){
					if(value == theArray[i]){
						System.out.println("Found " + value + " at position " + i);
						found = true;
					}
				}

				if(!found) throw new NumberNotFoundException();
			}
			catch (NumberNotFoundException nfe){
				System.out.println("Value to search could be found.");
			}
			catch ( InputMismatchException ime ){
				System.out.println("Value to searched must be a number.");
			}
			catch ( ArrayIndexOutOfBoundsException aob ){
				System.out.println("Currently the search index must be within 0 - " + (count - 1));
			}
		}
	}
}
