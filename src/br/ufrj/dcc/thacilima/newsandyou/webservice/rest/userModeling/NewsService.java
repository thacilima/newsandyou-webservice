package br.ufrj.dcc.thacilima.newsandyou.webservice.rest.userModeling;

import java.sql.SQLException;
import java.util.List;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import br.ufrj.dcc.thacilima.newsandyou.webservice.model.News;

@Path("/news")
public class NewsService {
	
	@GET
	@Path("/getAll")
	@Produces(MediaType.APPLICATION_JSON)
	public List<News> getAll(@QueryParam("indexOffset") Integer indexOffset, @QueryParam("loggedUserId") Integer loggedUserId) {

		if (indexOffset == null) {
			indexOffset = 0;
		}
		
		try {
			List<News> all = News.getAll(indexOffset, loggedUserId);
			return all;
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
}
