package bridgecontroller;

public class Bridge {

  private int allowedVehicles = 0;
  private int crossedVehicles = 0;
  private int totalWeight = 0;
  private EnumClass.Direction currentDirection = null;
  private EnumClass.Direction previousDirection = null;
  private VehicleList bridgeList = new VehicleList();
  
  
  
  public int vehicleCompability(EnumClass.Direction d, int weight, int id) {
    
    if(!trafficReatrictions(d, id))
      return -1;
    if(!bridgeRestrictions(weight))
      return 0;
    
    allowedVehicles++;
    currentDirection = d;
    return 1;
    
  }
  
  
  private boolean trafficReatrictions(EnumClass.Direction d, int id) {
    
    if(currentDirection == null && previousDirection == d)
      return false;
    
    if(currentDirection != null && currentDirection != d)
      return false;
    
    if(allowedVehicles + crossedVehicles >= 6) 
      return false;
    
    return true;
    
  }
  
  
  public boolean bridgeRestrictions (int weight) {
    
    totalWeight += weight ;
    if(totalWeight > 750) {
      totalWeight -= weight;
      return false;
    }
    
    
    return true;
          
  }
  
  
  public void addVehicle(Vehicle v) {
    
    bridgeList.add(v);
    System.out.println("Vehicle #"+v.getVehicleId() + " is now crossing the Bridge\n");
    System.out.println("Vehicles on the Bridge: ");
    bridgeList.printList();
    System.out.println("\nTotal weight on the Bridge ("+currentDirection +"): "+bridgeList.getTotalWeight());
    System.out.println("\n.................................... \n");
    
  }
  
  
  public void removeVehicle(Vehicle v, boolean flag) {
    
    bridgeList.remove(v);
    allowedVehicles--;
    totalWeight -= v.getVehicleWeight();
    if(!flag) 
      crossedVehicles++;      
    
    System.out.println("Vehicle #"+v.getVehicleId()+" has exited the Bridge \n");
    System.out.println("Vehicles on the Bridge: ");
    bridgeList.printList();
    System.out.println("\nTotal weight on the Bridge ("+currentDirection +"): "+bridgeList.getTotalWeight());
    System.out.println("\n.................................... \n");
    
    if((bridgeList.isEmpty() && crossedVehicles >= 6) || (bridgeList.isEmpty() && !flag)) {
      
      previousDirection = currentDirection;
      currentDirection = null;
      crossedVehicles = 0;
      totalWeight = 0;
    }
    
  }
  
  public boolean isBridgeEmpty() {
    return bridgeList.isEmpty();
  }
  
  
  public EnumClass.Direction getCurrentDirection () {
    
    return currentDirection;
    
  }

}