package br.ufrj.dcc.thacilima.newsandyou.webservice.model;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

import br.ufrj.dcc.thacilima.newsandyou.webservice.util.DBService;

public class User {
	
	//error, created, existing, updated, deleted, success
	//it's about the transaction done with the user
	private String status;
	
	private int idUser;
	private String email;
	private String name;
	private String fbUri;
	private Date lastUpdateDate;
	
	public User() {
		
	}

	public User(String email, String name, String fbUri) {
		this.email = email;
		this.name = name;
		this.fbUri = fbUri;
	}
	
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getIdUser() {
		return idUser;
	}
	public void setIdUser(int idUser) {
		this.idUser = idUser;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getFbUri() {
		return fbUri;
	}
	public void setFbUri(String fbUri) {
		this.fbUri = fbUri;
	}
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}
	public void setDateLastUpdate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}
	
	@Override
	public String toString() {
		return new StringBuffer("Email: ").append(this.email)
				.append(" Name: ").append(this.name)
				.append(" FbUri: ").append(this.fbUri)
				.append(" LastUpdateDate: ").append(this.lastUpdateDate).toString();
	}
	
	public User userFromResultSet(ResultSet rs) throws SQLException {
		User user = new User();
		
		user.idUser = rs.getInt("id_usuario");
		user.email = rs.getString("email");
		user.name = rs.getString("nome");
		user.fbUri = rs.getString("fb_uri");
		user.lastUpdateDate = rs.getDate("data_ultima_atualizacao");
		
		return user;
	}
	
	public User userFromThis() {
		User user = new User();
		
		user.setIdUser(this.idUser);
		user.setEmail(this.email);
		user.setName(this.name);
		user.setFbUri(this.fbUri);
		user.setDateLastUpdate(this.lastUpdateDate);
		
		return user;
	}
	
	public User loginCreatingUserIfNeeded() {
		DBService db = new DBService();
		
		String sqlCheckUser = "select * from usuario where email = '" + this.email + "'";
		ResultSet rs;
		User gottenUser = new User();
		gottenUser.setStatus("error");
		try {
			rs = db.runSql(sqlCheckUser);
			
			if (rs.next()) {
				gottenUser = this.userFromResultSet(rs);
				gottenUser.setStatus("existing");
			}
			else {
				String sqlCreateUser = "insert into usuario(email, nome, fb_uri, data_ultima_atualizacao) "
								+ "values ('" + this.email + "', "
									+ "'" + this.name + "', "
									+ "'" + this.fbUri + "', "
									+ "null);";
				
				rs = db.runInsertSql(sqlCreateUser);
				if (rs.next())
				{
					//TODO Tentar tirar esse método abaixo
					gottenUser = userFromThis();
					gottenUser.setIdUser(rs.getInt(1));
					gottenUser.setStatus("created");
				}
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return gottenUser;
	}
}
