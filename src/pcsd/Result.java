package pcsd;

import java.io.Serializable;
import java.util.List;

/**
 * Represents the result to a getConnections service call.
 * 
 * @author vmarcos, vdeurzen
 */
@SuppressWarnings("serial")
public class Result implements Serializable {

  /**
   * The status (see assignment description).
   */
  public int status;

  /**
   * The values (empty on error).
   */
  public List<Integer> values;
}
