package eecs285.proj3.util;

/**
 * Created by Colin on 10/18/2014.
 * <p/>
 * This is a utility class with commonly used methods
 */
public class Utility
{
  /**
   * Creates a dividing line.
   *
   * @return The dividing line.
   */
  public static String createFence()
  {
    String fence = "";
    for ( int i = 0; i < 80; i++ )
    {
      fence += '-';
    }

    return fence;
  }

  // ---------------------------------------------------------------------------
}
