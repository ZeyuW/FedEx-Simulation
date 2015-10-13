/**
 *
 */
package eecs285.proj3.util;

/**
 * YOU MAY NOT MODIFY THIS FILE.<br> This is an enumerated type for specifying
 * the pickup-priority level.
 */
public enum PickupPriorityLevel
{
  LOW_PRIORITY("Low"), MEDIUM_PRIORITY("Medium"), HIGH_PRIORITY("High");

  // ---------------------------------------------------------------------------
  // ---------------------------------------------------------------------------

  private final String str;

  // ---------------------------------------------------------------------------
  // ---------------------------------------------------------------------------

  public String toString()
  {
    return str;
  }

  // ---------------------------------------------------------------------------

  private PickupPriorityLevel( String s )
  {
    str = s;
  }

  // ---------------------------------------------------------------------------
}
