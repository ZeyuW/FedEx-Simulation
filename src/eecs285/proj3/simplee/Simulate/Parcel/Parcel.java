package eecs285.proj3.simplee.Simulate.Parcel;

import eecs285.proj3.util.Constants;
import eecs285.proj3.util.Location;
import eecs285.proj3.util.PickupPriorityLevel;

/**
 * Base class for the Parcel hierarchy.
 */
public abstract class Parcel implements Cloneable
{
  private static int nextID = 0;
  private static double totalShippingCost = 0;
  private final int parcelID;
  private Location currentLocation;
  private Location destination;
  private boolean inTransit;
  private boolean isDelivered;
  private double shippingCost = 0;

  // ---------------------------------------------------------------------------

  /**
   * @return totalShippingCost
   */
  public static double getTotalShippingCost()
  {
    return totalShippingCost;
  }

  // ---------------------------------------------------------------------------

  /**
   * Gets the weight fuel cost multiplier for a given weight.
   *
   * @param weight
   *     The weight in which to get the multiplier for.
   *
   * @return A weight fuel cost multiplier
   */
  public static double getWeightFuelCostMultiplier( double weight )
  {
    if ( weight == Constants.WEIGHT__LARGE_PARCEL )
    {
      return Constants.WEIGHT_FUEL_COST_MULTIPLIER__LARGE_PARCEL;
    }
    else if ( weight == Constants.WEIGHT__MEDIUM_PARCEL )
    {
      return Constants.WEIGHT_FUEL_COST_MULTIPLIER__MEDIUM_PARCEL;
    }
    else if ( weight == Constants.WEIGHT__SMALL_PARCEL )
    {
      return Constants.WEIGHT_FUEL_COST_MULTIPLIER__SMALL_PARCEL;
    }
    else
    {
      throw new RuntimeException(
          "getWeightFuelCostMultiplier Error weight not known");
    }
  }

  // ---------------------------------------------------------------------------

  /**
   * Resets the internal ID counter to 0. If you wish to run multiple
   * simulations in a single run of the program, then this should be called at
   * the start of each simulation.
   */
  public static void resetIDCounter()
  {
    totalShippingCost = 0;
    nextID = 0;
  }

  // ---------------------------------------------------------------------------

  /**
   * @return A cloned parcel if error is throw return null;
   */
  public Object clone() throws CloneNotSupportedException
  {
    return super.clone();
  }

  // ---------------------------------------------------------------------------

  /**
   * Example: "Large Parcel (id=3 Pickup_Priority=Low)"
   *
   * @return See above.
   */
  @Override
  public String toString()
  {
    return String.format("%s Parcel (id=%d Pickup_Priority=%s)", getSize(),
                         getParcelID(),
                         getPickupPriorityLevelForDistance(
                             Location.getDistanceBetween(
                                 currentLocation,
                                 getDestination())));
  }

  // ---------------------------------------------------------------------------

  /**
   * @return currentLocation
   */
  public Location getCurrentLocation()
  {
    return currentLocation;
  }

  // ---------------------------------------------------------------------------

  /**
   * Sets the currentLocation of the parcel.
   *
   * @param inCurrentLocation
   *     The location in which to set the current location of the parcel
   *     to.
   */
  public void setCurrentLocation( Location inCurrentLocation )
  {
    currentLocation = inCurrentLocation;
  }

  // ---------------------------------------------------------------------------

  /**
   * @return destination
   */
  public Location getDestination()
  {
    return destination;
  }

  // ---------------------------------------------------------------------------

  /**
   * Sets the parcel's destination.
   *
   * @param inDestination
   *     The destination in which to set the parcels to.
   */
  public void setDestination( Location inDestination )
  {
    destination = inDestination;
  }

  // ---------------------------------------------------------------------------

  /**
   * @return parcelID
   */
  public int getParcelID()
  {
    return parcelID;
  }

  // ---------------------------------------------------------------------------

  /**
   * Given a distance to a destination, this method returns the appropriate
   * pickup-priority-level.
   *
   * @param distance
   *     The distance in which to calculate the priority
   *
   * @return A string of the pickup priority.
   */
  public String getPickupPriorityLevelForDistance(
      double distance )
  {
    if ( distance > Constants.MAX_DROPOFF_DISTANCE__CARGO_PLANE )
    {
      return PickupPriorityLevel.HIGH_PRIORITY.toString();
    }
    else if ( distance > Constants.MAX_DROPOFF_DISTANCE__FREIGHT_TRUCK )
    {
      return PickupPriorityLevel.MEDIUM_PRIORITY.toString();
    }

    return PickupPriorityLevel.LOW_PRIORITY.toString();

  }

  // ---------------------------------------------------------------------------

  /**
   * @return The previous priority level of parcel
   */
  public String getPreviousPriorityLevel()
  {
    String currentPriorityLevel = getPickupPriorityLevelForDistance(
        Location.getDistanceBetween(currentLocation, destination));

    switch ( currentPriorityLevel )
    {
      case "Low":
        return "Medium";
      default:
        return "High";
    }
  }

  // ---------------------------------------------------------------------------

  /**
   * @return returns the shippingCost of the parcel
   */
  public double getShippingCost()
  {
    return shippingCost;
  }

  // ---------------------------------------------------------------------------

  /**
   * @return The size ("Small", "Medium", "Large") of the the parcel.
   */
  public abstract String getSize();

  // ---------------------------------------------------------------------------

  /**
   * @return The weight of the parcel.
   */
  public abstract double getWeight();

  // ---------------------------------------------------------------------------

  /**
   * @return isDelivered
   */
  public boolean isDelivered()
  {
    return isDelivered;
  }

  // ---------------------------------------------------------------------------

  /**
   * @return True if the parcel is in transit, false otherwise.
   */
  public boolean isInTransit()
  {
    return inTransit;
  }

  // ---------------------------------------------------------------------------

  /**
   * Sets the parcel's inTransit state.
   *
   * @param inInTransit
   *     True for in transit, false for not in transit.
   */
  public void setInTransit( boolean inInTransit )
  {
    inTransit = inInTransit;
  }

  // ---------------------------------------------------------------------------

  /**
   * Marks the package as having arrived at its final destination.
   */
  public void setDelivered()
  {
    isDelivered = true;
  }

  // ---------------------------------------------------------------------------

  /**
   * Gets the shipping cost multiplier for a given priority level.
   *
   * @param priorityLevel
   *     The priority level in which to check the shipping cost multiplier for.
   *
   * @return A shipping cost multiplier.
   */
  private static double getShippingMultiplierForPriorityLevel(
      String priorityLevel )
  {
    if ( priorityLevel
        .equals(PickupPriorityLevel.HIGH_PRIORITY.toString()) )
    {
      return Constants.SHIPPING_MULTIPLIER__HIGH_PRIORITY;
    }
    else if ( priorityLevel
        .equals(PickupPriorityLevel.MEDIUM_PRIORITY.toString()) )
    {
      return Constants.SHIPPING_MULTIPLIER__MEDIUM_PRIORITY;
    }
    else if ( priorityLevel
        .equals(PickupPriorityLevel.LOW_PRIORITY.toString()) )
    {
      return Constants.SHIPPING_MULTIPLIER__LOW_PRIORITY;
    }
    else
    {
      throw new RuntimeException(
          "getShippingMultiplier Error priorityLevel not known");
    }
  }

  // ---------------------------------------------------------------------------

  /**
   * Calculates the shipping cost of the parcel
   *
   * @return shippingCost
   */
  private double calculateShippingCost()
  {
    double distance =
        Location.getDistanceBetween(currentLocation, destination);
    return getWeight() * distance *
           Parcel.getShippingMultiplierForPriorityLevel(
               getPickupPriorityLevelForDistance(distance));
  }

  // ---------------------------------------------------------------------------

  /**
   * Note: Automatically assigns the next available ID<br>
   */
  Parcel( Location inStartLocation, Location inDestination )
  {
    currentLocation = inStartLocation;
    destination = inDestination;

    parcelID = nextID++;

    shippingCost = calculateShippingCost();
    totalShippingCost += shippingCost;
  }

  // ---------------------------------------------------------------------------
}