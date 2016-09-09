package br.ufrj.dcc.thacilima.newsandyou.webservice.rest.testingREST;

import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("/hello")
public class HelloService {
	
	@GET
	@Path("/")
	public String sayHello() {
		return "Hello World from REST !!!!!";
	}
	
	@GET
	@Path("/{message}")
	public String sayMessage(@PathParam("message") String message, 
			@DefaultValue("Nothing to say") @QueryParam("value") String value)
	{
		return message + " with value " + value;
	}
	
	@GET
	@Path("/objMessage")
	@Produces(MediaType.APPLICATION_JSON)
	public HelloMessage saveHelloMessage() {
		HelloMessage obj = new HelloMessage();
		obj.setMessage("Hello world from obejct instance");
		return obj;
	}
}


