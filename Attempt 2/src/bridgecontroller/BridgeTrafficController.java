package bridgecontroller;

import java.util.Scanner;

public class BridgeTrafficController {
  
  private BridgeTrafficController () {}

	public static void main(String[] args) {
		System.out.println("List of Schedules that can be executed");
		System.out.println("1. 20 : DELAY (10) : 10 Car/Truck Pobability: [1.0,0.0]");
		System.out.println("2. 20 : DELAY (10) : 10 Car/Truck Pobability: [0.2,0.8]");
		System.out.println("3. 30    Car/Truck Pobability: [0.2,0.8]");
		System.out.println("4. 15 : DELAY (45) : 15 Car/Truck Pobability: [0.7,0.3]");
		System.out.println("5. 10 : DELAY (2) : 10 : DELAY (2) : 10 Car/Truck Pobability: [0.2,0.8]");
		System.out.println("6. Custom Schedule Please provide number of groups of vehicle seperated");
		System.out.println("   by DELAY with number of vehicles in each group");
		System.out.println("");
		System.out.print("Please enter the Schedule Number you want to execute : ");
		Scanner scan = new Scanner(System.in);
		int choice = scan.nextInt();
		System.out.println("");
		ScheduleController sc = new ScheduleController(choice);
		sc.chooseSchedule();
		scan.close();
	}

}
