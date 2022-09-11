
public class TestDate {

	public static void main(String[] args) {

		Date date1 = new Date("34/7 / 2008");
		Date date2 = new Date("31-13-2010");
		Date date3 = new Date("22/ 02 - 002");

		System.out.println("date1 = " + date1.toString());
		System.out.println("date2 = " + date2.toString());
		System.out.println("date3 = " + date3.toString());
		System.out.println("date1 is " + (date1.earlierThan(date2) ? "earlier than " : "later than ") + "date2");
		System.out.println("date1 is " + (date1.earlierThan(date3) ? "earlier than " : "later than ") + "date3");
		System.out.println("date2 is " + (date2.earlierThan(date1) ? "earlier than " : "later than ") + "date1");
		System.out.println("date2 is " + (date2.earlierThan(date3) ? "earlier than " : "later than ") + "date3");
		System.out.println("date3 is " + (date3.earlierThan(date1) ? "earlier than " : "later than ") + "date1");
		System.out.println("date3 is " + (date3.earlierThan(date2) ? "earlier than " : "later than ") + "date2");

	}
}
