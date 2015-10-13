package eecs285.proj3.simplee.Simulate.Parcel;

import eecs285.proj3.util.Constants;
import eecs285.proj3.util.Location;

/**
 * This is the MediumParcel class it extends parcel and represents everything
 * for a medium parcel.
 */
public class MediumParcel extends Parcel implements Cloneable
{
  /**
   * The Medium Parcel constructor.
   *
   * @param inStartLocation
   *     The start location of the parcel.
   * @param inDestination
   *     The end location of the parcel.
   */
  public MediumParcel( Location inStartLocation, Location inDestination )
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
    return "Medium";
  }

  // ---------------------------------------------------------------------------

  @Override
  public double getWeight()
  {
    return Constants.WEIGHT__MEDIUM_PARCEL;
  }

  // ---------------------------------------------------------------------------
}
