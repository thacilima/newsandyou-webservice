package br.ufrj.dcc.thacilima.newsandyou.webservice.model;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.ufrj.dcc.thacilima.newsandyou.webservice.util.DBService;

public class News {
	
	private int idNews;
	private String uriSourceNews;
	private String uri;
	private String title;
	private String subtitle;
	private String imageUrl;
	private Date gottenDate;
	
	public News() {
		
	}
	
	public News(String uri) {
		this.uri = uri;
	}

	public int getIdNews() {
		return idNews;
	}

	public void setIdNews(int idNews) {
		this.idNews = idNews;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public Date getGottenDate() {
		return gottenDate;
	}

	public void setGottenDate(Date gottenDate) {
		this.gottenDate = gottenDate;
	}
	
	public String getUriSourceNews() {
		return uriSourceNews;
	}

	public void setUriSourceNews(String uriSourceNews) {
		this.uriSourceNews = uriSourceNews;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getSubtitle() {
		return subtitle;
	}

	public void setSubtitle(String subtitle) {
		this.subtitle = subtitle;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("News [idNews=").append(idNews).append(", uriSourceNews=").append(uriSourceNews).append(", uri=")
				.append(uri).append(", title=").append(title).append(", subtitle=").append(subtitle)
				.append(", imageUrl=").append(imageUrl).append(", gottenDate=").append(gottenDate).append("]");
		return builder.toString();
	}

	static public News newsFromResultSet(ResultSet rs) throws SQLException {
		News news = new News();
		news.setIdNews(rs.getInt("id_noticia"));
		news.setUriSourceNews(rs.getString("uri_origem_noticia"));
		news.setUri(rs.getString("uri"));
		news.setTitle(rs.getString("titulo"));
		news.setSubtitle(rs.getString("subtitulo"));
		news.setImageUrl(rs.getString("imagem_url"));
		news.setGottenDate(rs.getDate("data_coletagem"));
		
		return news;
	}
	
	static private List<News> newsListFromResultSet(ResultSet rs) throws SQLException {
		List<News> all = new ArrayList<News>();
		while (rs.next()) {
			News news = newsFromResultSet(rs);
			all.add(news);
		}
		return all;
	}
	
	static public List<News> getAll(Integer indexOffset, Integer loggedUserId) throws SQLException {
		DBService db = new DBService();
		
		Integer limit = 12;
		Integer offset = limit*indexOffset;
		
		String sql = "select "
					+ "count(n.id_noticia) userShouldLikeTimes, "
					+ "n.*, "
					+ "orin.uri as uri_origem_noticia "
				+ "from "
					+ "noticia n "
					+ "inner join origem_noticia orin on n.id_origem_noticia = orin.id_origem_noticia "
					+ "inner join caracteristica_noticia cn on  n.id_noticia = cn.id_noticia "
					+ "inner join caracteristica_usuario cu on (cn.id_atributo = cu.id_atributo and cu.id_usuario = "+loggedUserId+") "
				+ "group by n.id_noticia "
				+ "order by n.data_coletagem desc, userShouldLikeTimes desc, n.id_noticia desc "
				+ "limit "+limit+" offset "+offset+";";
			
		ResultSet rs = db.runSql(sql);
		
		List<News> all = newsListFromResultSet(rs);
		
		return all;
	}
}

