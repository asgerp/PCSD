package pcsd.basic;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import pcsd.GraphService;
import pcsd.Result;

/**
 * @author You!
 */
public class BasicGraphService implements GraphService {
	private Map<Integer, List<Integer>> graphMap = null;

  public BasicGraphService(){}
  /**
   * Add comment.
   */
  @Override
  public int bulkload(String filename) {
	  // TODO: MAKE PRETTIER PARSER
    // check for instantiation of graphMap
	  if(graphMap != null) {
		  return -45;
	  }
	  graphMap = new HashMap<Integer, List<Integer>>();
    try {
      BufferedReader in
         = new BufferedReader(new FileReader(filename));
      String strline;
      while ((strline = in.readLine()) != null) {
    	  // separate key value
    	  String delims = "[\t ]+";
    	  String[] tokens = strline.split(delims);
    	  int k = Integer.parseInt(tokens[0]);
    	  int v = Integer.parseInt(tokens[1]);
    	  if(graphMap.containsKey(k)) {
    		  graphMap.get(k).add(v);
    	  } else {
    		  List<Integer> l = new ArrayList<Integer>();
    		  l.add(v);
    		  graphMap.put(k, l);  
    	  }
      }
    } catch (FileNotFoundException e) {
    	//e.printStackTrace();
    	return -42;
    } catch (IOException e) {
      //e.printStackTrace();
      return -43;
    } catch (OutOfMemoryError e) {
    	//e.printStackTrace();
		return -44;
	} catch (NumberFormatException e) {
		//e.printStackTrace();
		return -43;
	}
    return 0;  
  }

  /**
   * Add comment.
   */
  @Override
  public Result getConnections(Integer key) {
	  Result r = new Result();
	  r.values = new ArrayList<Integer>();
	  if(graphMap != null) {
		  if(graphMap.containsKey(key)) {
			  r.status = 0;
			  r.values = graphMap.get(key);
			  return r;
		  } else{
			  // no key
			  r.status = -46;
			  return r;
		  }
	  } else {
		  // data not loaded
		  r.status = -45;
		  return r;  
	  }
  }
}
