package bridgecontroller; 

import java.util.LinkedList;

import java.util.Iterator;

public class VehicleList {
  
  private LinkedList <Vehicle> list = new LinkedList<Vehicle>();
  
  public void add(Vehicle v) {
    list.add(v);
  }
  
  
  public void remove(Vehicle v) {
    list.remove(v);
  }
  
 
  public Vehicle get(int index) {
    
    return list.get(index);
    
  }
  
  
  public void printList() {
    
   Iterator <Vehicle> itr = list.iterator();

   while(itr.hasNext()) {
    Vehicle v = itr.next();
    System.out.println("Vehicle #"+v.getVehicleId() + "(" + v.getVehicleDirection() +
        ", Type: "+v.getVehicleType()+")");
    }  
  }
  
  
  public LinkedList<Vehicle> returnList () {
    return list;
  }
  
  
  public boolean isEmpty() {
    return list.isEmpty();
  }
  
  
  public int getTotalWeight() {
    
    int totalWeight = 0;
    Iterator<Vehicle> itr = list.iterator();
    
    while(itr.hasNext()) {
      Vehicle v = itr.next();
      totalWeight += v.getVehicleWeight();
    }
    return totalWeight;
    
  }
  
}
