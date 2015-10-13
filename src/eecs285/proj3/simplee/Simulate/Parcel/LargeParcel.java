package eecs285.proj3.simplee.Simulate.Parcel;

import eecs285.proj3.util.Constants;
import eecs285.proj3.util.Location;

/**
 * This is the LargeParcel class it extends parcel and represents everything for
 * a large parcel.
 */
public class LargeParcel extends Parcel implements Cloneable
{
  /**
   * The Large Parcel constructor.
   *
   * @param inStartLocation
   *     The start location of the parcel.
   * @param inDestination
   *     The end location of the parcel.
   */
  public LargeParcel( Location inStartLocation, Location inDestination )
  {
    super(inStartLocation, inDestination);
  }

  // ---------------------------------------------------------------------------

  @Override
  public Object clone() throws CloneNotSupportedException
  {
    return super.clone();
  }

  // ---------------------------------------------------------------------------

  @Override
  public String getSize()
  {
    return "Large";
  }

  // ---------------------------------------------------------------------------

  @Override
  public double getWeight()
  {
    return Constants.WEIGHT__LARGE_PARCEL;
  }

  // ---------------------------------------------------------------------------
}
