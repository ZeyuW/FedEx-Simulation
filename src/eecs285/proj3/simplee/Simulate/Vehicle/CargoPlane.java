package eecs285.proj3.simplee.Simulate.Vehicle;

import eecs285.proj3.util.Constants;
import eecs285.proj3.util.Location;
import eecs285.proj3.util.PickupPriorityLevel;

/**
 * The CargoPlane subclass of Vehicle.
 */
public class CargoPlane extends Vehicle implements Cloneable
{
  private static double cargoPlanesTotalFuelCost = 0;
  private static double cargoPlanesTotalMilesTraveled = 0;

  // ---------------------------------------------------------------------------

  /**
   * The CargoPlane constructor.
   *
   * @param inStartLocation
   *     The starting location of the vehicle
   */
  public CargoPlane( Location inStartLocation )
  {
    super(inStartLocation);
  }

  // ---------------------------------------------------------------------------

  /**
   * @return cargoPlanesTotalFuelCost
   */
  public static double getCargoPlanesTotalFuelCost()
  {
    return cargoPlanesTotalFuelCost;
  }

  // ---------------------------------------------------------------------------

  /**
   * @return cargoPlanesTotalMilesTraveled
   */
  public static double getCargoPlanesTotalMilesTraveled()
  {
    return cargoPlanesTotalMilesTraveled;
  }

  // ---------------------------------------------------------------------------

  /**
   * Resets the private static statistic variables.
   */
  public static void reset()
  {
    cargoPlanesTotalFuelCost = 0;
    cargoPlanesTotalMilesTraveled = 0;
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
    return Constants.FUEL_COST_PER_GALLON__CARGO_PLANE;
  }

  // ---------------------------------------------------------------------------

  @Override
  public double getMPG()
  {
    return Constants.MPG__CARGO_PLANE;
  }

  // ---------------------------------------------------------------------------

  @Override
  public double getSpeed()
  {
    return Constants.SPEED_PER_TICK__CARGO_PLANE;
  }

  // ---------------------------------------------------------------------------

  @Override
  public String getVehicleType()
  {
    return "Cargo Plane";
  }

  // ---------------------------------------------------------------------------

  @Override
  public void addFuelCostToTotalForVehicleType( double fuelCost )
  {
    cargoPlanesTotalFuelCost += fuelCost;
  }

  // ---------------------------------------------------------------------------

  @Override
  public void addTotalMilesTraveledForVehicleType( double totalMilesTraveled )
  {
    cargoPlanesTotalMilesTraveled += totalMilesTraveled;
  }

  // ---------------------------------------------------------------------------

  @Override
  public double getMaxDropoffDistance()
  {
    return Constants.MAX_DROPOFF_DISTANCE__CARGO_PLANE;
  }

  // ---------------------------------------------------------------------------

  @Override
  public boolean isAllowedToPickupPriorityLevel( String priority )
  {
    return priority.equals(PickupPriorityLevel.HIGH_PRIORITY.toString());
  }

  // ---------------------------------------------------------------------------
}
