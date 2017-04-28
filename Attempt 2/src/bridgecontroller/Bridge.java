package bridgecontroller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Bridge {
  
  private static final Logger logger = LogManager.getLogger(Bridge.class);

  private int allowedVehicles = 0;
  private int crossedVehicles = 0;
  private int totalWeight = 0;
  private EnumClass.Direction currentDirection = null;
  private EnumClass.Direction previousDirection = null;
  
  
  /*checks for bridge restrictions and traffic restrictions*/
  
  public int vehicleCompability(EnumClass.Direction d, int weight,int id,boolean oppFlag) {
    
    if(!trafficRestrictions(d, oppFlag))
      return -1;
    if(!bridgeRestrictions(weight))
      return 0;
    
    allowedVehicles++;
    currentDirection = d;
    
    return 1;
    
  }
  
  /*checks for the traffic restrictions like direction 
   * and number of vehicles crossed the bridge*/
  
  private boolean trafficRestrictions(EnumClass.Direction d,boolean oppFlag) {
    
    if(currentDirection == null && previousDirection == d)
      return false;
    
    if(currentDirection != null && currentDirection != d)
      return false;
    
    if(allowedVehicles + crossedVehicles >= 6 && !oppFlag) 
      return false;
    
    return true;
    
  }
  
  /*Checks for the Bridge Weight Limits*/
  
  public boolean bridgeRestrictions (int weight) {
    
    totalWeight += weight ;
    if(totalWeight > 750) {
      totalWeight -= weight;
      return false;
    }
    
    
    return true;
          
  }
  
  /*bridgeStatus tells whether bridge is Empty
   flag tells the status of waiting vehicle on the opposite direction*/
  
   public void removeVehicle(int weight, int flag, boolean bridgeStatus) {
    
   
    allowedVehicles--;
    totalWeight -= weight;
    crossedVehicles++;      
    
    if(crossedVehicles > 6 )
      logger.error("Crossed Vehicles Greater than 6 :--->"+crossedVehicles);
    
    
    if((bridgeStatus && crossedVehicles >= 6 ) || (bridgeStatus && flag == 0)) {
   
      previousDirection = currentDirection;
      currentDirection = null;
      crossedVehicles = 0;
      totalWeight = 0;
      
    }
  }
  
}