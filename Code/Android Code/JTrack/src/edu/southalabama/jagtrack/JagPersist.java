package edu.southalabama.jagtrack;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import com.google.gson.Gson;


public class JagPersist
{
//	public static void main(String args[]){
//		JagTran jt = new JagTran();
//		JagPersist test = new JagPersist();
//		String test_time = test.getArrivalTime("red",  jt.busStops[0]).toString();
//		System.out.println(test_time);
//	}
	
    /**
     * JagPersist constructor. No parameters.
     * @see JagPersist
     */
	
		
    public JagPersist()
    {
    }

    /**
     * Returns an ArrivalTime object. This method assumes that the route and
     * location are correct, so any error checking should be done on the side of
     * the calling class.
     * 
     * @param route
     *            a String containing the route, such as Yellow or Green
     * @param loc
     *            a String containing the JagStop the user has selected
     * @return The ArrivalTime for the nearest bus of route color to that
     *         JagStop
     * @see ArrivalTime
     **/
    public ArrivalTime getArrivalTime(String route, BusStop stop)
    {
    	System.out.println("At least this works");
    	BufferedReader in = null;
    	int arrivalMinutes = -1;
    	try{
    	// Make HHTP request to get the information
        HttpClient client = new DefaultHttpClient();
        HttpGet request = new HttpGet();
        String url = "http://jagtrack-amoore913.dotcloud.com/get_avg_time.php?s="
                + stop.getId() + "&r=" + route;
        System.out.println("Url: " + url);
        request.setURI(new URI(url));
        HttpResponse response = client.execute(request);
        in = new BufferedReader(
        		new InputStreamReader(response.getEntity().getContent()));
        StringBuffer sb = new StringBuffer("");
        String line = "";
        line = in.readLine();
        //String NL = System.getProperty("line.separator");
//        while ((line = in.readLine()) != null) {
//            sb.append(line);
//        }
        in.close();
        Gson g = new Gson();
        arrivalMinutes = g.fromJson(line, int.class); //sb.toString();
        
        System.out.println("Trying to do something!");      
    	}catch(Exception e){
    		System.out.println(e);
    	}
    	finally{
    		if (in != null) {
                try {
                	in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }// end catch
    		}// end if			
    	}// end finally	
    return new ArrivalTime(0, arrivalMinutes, 0);
	}// end getArrivalTime()
}// end JagPersist
