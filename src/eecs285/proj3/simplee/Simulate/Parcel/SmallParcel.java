package eecs285.proj3.simplee.Simulate.Parcel;

import eecs285.proj3.util.Constants;
import eecs285.proj3.util.Location;

/**
 * This is the SmallParcel class it extends parcel and represents everything for
 * a small parcel
 */
public class SmallParcel extends Parcel implements Cloneable
{
  /**
   * The Small Parcel constructor.
   *
   * @param inStartLocation
   *     The start location of the parcel
   * @param inDestination
   *     The end location of the parcel
   */
  public SmallParcel( Location inStartLocation, Location inDestination )
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
    return "Small";
  }

  // ---------------------------------------------------------------------------

  @Override
  public double getWeight()
  {
    return Constants.WEIGHT__SMALL_PARCEL;
  }

  // ---------------------------------------------------------------------------
}
