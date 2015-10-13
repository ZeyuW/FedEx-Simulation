package eecs285.proj3.simplee.Simulate.Vehicle;

import eecs285.proj3.util.Constants;
import eecs285.proj3.util.Location;
import eecs285.proj3.util.PickupPriorityLevel;

/**
 * The FreightTruck subclass of Vehicle.
 */
public class FreightTruck extends Vehicle implements Cloneable
{
  private static double freightTrucksTotalFuelCost = 0;
  private static double freightTrucksTotalMilesTraveled = 0;

  // ---------------------------------------------------------------------------

  /**
   * FreightTruck constructor.
   *
   * @param inStartLocation
   *     The starting location of the vehicle.
   */
  public FreightTruck( Location inStartLocation )
  {
    super(inStartLocation);
  }

  // ---------------------------------------------------------------------------

  /**
   * @return freightTrucksTotalFuelCost
   */
  public static double getFreightTrucksTotalFuelCost()
  {
    return freightTrucksTotalFuelCost;
  }

  // ---------------------------------------------------------------------------

  /**
   * @return freightTrucksTotalMilesTraveled
   */
  public static double getFreightTrucksTotalMilesTraveled()
  {
    return freightTrucksTotalMilesTraveled;
  }

  // ---------------------------------------------------------------------------

  /**
   * Resets the private static statistic variables.
   */
  public static void reset()
  {
    freightTrucksTotalFuelCost = 0;
    freightTrucksTotalMilesTraveled = 0;
  }

  // ---------------------------------------------------------------------------

  @Override
  public Object clone() throws CloneNotSupportedException
  {
    return super.clone();
  }

  // ---------------------------------------------------------------------------

  @Override
  public double getFuelCostPerGallon()
  {
    return Constants.FUEL_COST_PER_GALLON__FREIGHT_TRUCK;
  }

  // ---------------------------------------------------------------------------

  @Override
  public double getMPG()
  {
    return Constants.MPG__FREIGHT_TRUCK;
  }

  // ---------------------------------------------------------------------------

  @Override
  public double getSpeed()
  {
    return Constants.SPEED_PER_TICK__FREIGHT_TRUCK;
  }

  // ---------------------------------------------------------------------------

  @Override
  public String getVehicleType()
  {
    return "Freight Truck";
  }

  // ---------------------------------------------------------------------------

  @Override
  public void addFuelCostToTotalForVehicleType( double fuelCost )
  {
    freightTrucksTotalFuelCost += fuelCost;
  }

  // ---------------------------------------------------------------------------

  @Override
  public void addTotalMilesTraveledForVehicleType( double totalMilesTraveled )
  {
    freightTrucksTotalMilesTraveled += totalMilesTraveled;
  }

  // ---------------------------------------------------------------------------

  @Override
  public double getMaxDropoffDistance()
  {
    return Constants.MAX_DROPOFF_DISTANCE__FREIGHT_TRUCK;
  }

  // ---------------------------------------------------------------------------

  @Override
  public boolean isAllowedToPickupPriorityLevel( String priority )
  {
    return priority.equals(PickupPriorityLevel.MEDIUM_PRIORITY.toString());
  }

  // ---------------------------------------------------------------------------
}
