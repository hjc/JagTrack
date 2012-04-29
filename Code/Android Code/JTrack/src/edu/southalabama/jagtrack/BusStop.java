package edu.southalabama.jagtrack;

public class BusStop {

	private String location;
	private int id;
	private String routes;
	
	public BusStop(String location, String routes, int id){
		this.id = id;
		this.location = location;
		this.routes = routes;
	}
	
	public String getLocation(){
		return this.location;
	}
	
	public int getId(){
		return this.id;
	}
	
	public String getRoutes(){
		return this.routes;
	}
}
