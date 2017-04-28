package bridgecontroller;

import java.util.Scanner;

public class ScheduleController {

  private int scheduleNo;
  
  public ScheduleController(int choice) {
    scheduleNo = choice;
  }
  
  public void chooseSchedule() {
    switch (scheduleNo) {
    case 1: firstSchedule();
    break;
    
    case 2: secondSchedule();
    break;
    
    case 3: thirdSchedule();
    break;
    
    case 4: fourthSchedule();    
    break;
    
    case 5: fifthSchedule();     
    break;
    
    case 6: customSchedule();    
    break;
    
    default: System.out.println("Wrong Input");
    }
  }
  
  private void firstSchedule() {
    
    int [] vehicles  = new int[2];
    vehicles[0] = 20;
    vehicles[1] = 10;
    
    int [] delay = new int[2];
    delay[0] = 10;

    runSchedule(vehicles, delay, 1.0, 0.5);
    
  }
  
  private void secondSchedule() {
    
    int [] vehicles  = new int[2];
    vehicles[0] = 20;
    vehicles[1] = 10;
    
    int [] delay = new int[1];
    delay[0] = 10;

    runSchedule(vehicles, delay, 0.2, 0.5);
  }
  
  private void thirdSchedule() {
    
    int [] vehicles  = new int[1];
    vehicles[0] = 20;
    
    int [] delay = new int[1];
    delay[0] = 0;

    runSchedule(vehicles, delay, 0.8, 0.5);
  }
  
  private void fourthSchedule() {
    
    int [] vehicles  = new int[2];
    vehicles[0] = 15;
    vehicles[1] = 15;
    
    int [] delay = new int[1];
    delay[0] = 45;

    runSchedule(vehicles, delay, 0.7, 0.5);
  }
  
  private void fifthSchedule() {
    
    int [] vehicles  = new int[3];
    vehicles[0] = 10;
    vehicles[1] = 10;
    vehicles[2] = 10;
    
    int [] delay = new int[2];
    delay[0] = 2;
    delay[1] = 2;

    runSchedule(vehicles, delay, 0.5, 0.5);
  }
  
  private void customSchedule() {
    
    Scanner scan = new Scanner(System.in);
    
    System.out.print("Please enter the number of groups of vehicles : ");
    int groups = Integer.parseInt(scan.nextLine());
    int [] vehicles = new int[groups];
    int [] delay = new int [groups-1];
    
    for(int i = 0; i < groups; i++) {
      
      if(i > 0) {
        System.out.println("Enter the DELAY period between group "+i+" and group "+(i+1)+" vehicles :");
        delay[i-1] = Integer.parseInt(scan.nextLine());
        }
      
      System.out.println("Enter Number of vehicles in the "+(i+1)+" group :");
      vehicles[i] = Integer.parseInt(scan.nextLine());
      
    }
    
    System.out.println("Enter the Car Probability : ");
    double cProbability = Double.parseDouble(scan.nextLine());
    
    System.out.println("Enter Vehicle Direction Probability for North bound traffic: ");
    double dProbability = Double.parseDouble(scan.nextLine());
    
    runSchedule(vehicles, delay, cProbability, dProbability);
    scan.close();
    
  }
  
  private void runSchedule(int[] vehicles, int[] delay, double cProbability, double dProbability) {
 
    VehicleBridge vb = new VehicleBridge();
    
    for(int i=0; i< vehicles.length;i++) {
      
      if(i > 0) {
        try {
          Thread.sleep(delay[i-1]*1000);
        }
        catch (InterruptedException ex) {
          ex.printStackTrace();
        }
      }
      
      int vCount = vehicles[i];
      for(int j=1; j<= vCount; j++) {     
        Thread v = new Thread(new Vehicle(j,cProbability,dProbability,vb));
        v.start();   
      }
    }
  }
  
}
