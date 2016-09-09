package br.ufrj.dcc.thacilima.newsandyou.webservice.rest.userModeling;

import java.util.Calendar;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import br.ufrj.dcc.thacilima.newsandyou.webservice.model.Characteristic;
import br.ufrj.dcc.thacilima.newsandyou.webservice.model.User;

@Path("/userModeling")
public class UserModelingService {
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/loginCreatingUserIfNeeded")
	public User loginCreatingUserIfNeeded(User user) {
		
		User createdUser = user.loginCreatingUserIfNeeded();
		
		return createdUser;
	}
	
	@POST
	@Consumes(MediaType.APPLICATION_JSON)
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/modelUser")
	public Characteristic modelUser(Characteristic characteristic) {
		
		characteristic.setGottenDateCalendar(Calendar.getInstance()); 
		characteristic.setPossibility(1.0);
		
		return characteristic.createCharacteristicsFromList();
	}
	
	@GET
	@Path("/teste")
	@Produces(MediaType.APPLICATION_JSON)
	public User saveHelloMessage() {
		User usr = new User();
		usr.setEmail("t@t.com");
		usr.setName("t");
		usr.setFbUri("uri");
		return usr;
	}
}
