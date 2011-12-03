package pcsd.basic;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import pcsd.GraphService;
import pcsd.Result;

/**
 * @author You!
 */
public class BasicGraphService implements GraphService {

  public BasicGraphService(){}
  /**
   * Add comment.
   */
  @Override
  public int bulkload(String filename) {
    // read file
    try {
      BufferedReader in
         = new BufferedReader(new FileReader(filename));
      String strline;
      while ((strline = in.readLine()) != null){
        System.out.println(strline);
        
      }
    } catch (FileNotFoundException e) {
      // return 42
      e.printStackTrace();
    } catch (IOException e) {
      // TODO Auto-generated catch block
      e.printStackTrace();
    }
    return 0;
    
    // put content in map
    //throw new UnsupportedOperationException();
  }

  /**
   * Add comment.
   */
  @Override
  public Result getConnections(Integer key) {
    throw new UnsupportedOperationException();
  }
}
