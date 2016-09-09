package br.ufrj.dcc.thacilima.newsandyou.webservice.model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;

import br.ufrj.dcc.thacilima.newsandyou.webservice.util.DBService;

/**
 * @author thacilima
 *
 */
public class Characteristic {
	
	//error, created, existing, updated, deleted, success
	//it's about the transaction done with the characteristic
	private String status;
	
	private int idUser;
	private int idAttribute;
	private Calendar gottenDateCalendar;
	private double possibility;

	private ArrayList<String> attributes;
	
	public Characteristic() {
		
	}
	
	public Characteristic(int idUser, String[] attributes) {
		
		this.idUser = idUser;
		this.attributes = (ArrayList<String>)Arrays.asList(attributes);	
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
	public int getIdAttribute() {
		return idAttribute;
	}
	public void setIdAttribute(int idAttribute) {
		this.idAttribute = idAttribute;
	}
	public Calendar getgottenDateCalendar() {
		return gottenDateCalendar;
	}
	public void setGottenDateCalendar(Calendar gottenDateCalendar) {
		this.gottenDateCalendar = gottenDateCalendar;
	}
	public double getPossibility() {
		return possibility;
	}
	public void setPossibility(double possibility) {
		this.possibility = possibility;
	}
	public ArrayList<String> getAttributes() {
		return attributes;
	}
	public void setAttributes(ArrayList<String> attributes) {
		this.attributes = attributes;
	}
	
	@Override
	public String toString() {
		return new StringBuffer("IdUser: ").append(this.idUser)
				.append(" IdAttribute: ").append(this.idAttribute)
				.append(" GottenDate: ").append(this.gottenDateCalendar)
				.append(" Possibility: ").append(this.possibility).toString();
	}
	
	public Characteristic characteristicFromResultSet(ResultSet rs) throws SQLException {
		Characteristic characteristic = new Characteristic();
		
		characteristic.idUser = rs.getInt("id_usuario");
		characteristic.idAttribute = rs.getInt("id_atributo");
		characteristic.gottenDateCalendar.setTime(rs.getDate("data_coletagem"));
		characteristic.possibility = rs.getDouble("possibilidade");
		
		return characteristic;
	}

	public Characteristic createCharacteristicsFromList() {
		DBService db = new DBService();
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String gottenDateString = dateFormat.format(this.gottenDateCalendar.getTime());
		
		String allAttributesString = this.attributes.toString().replace("[", "'").replace("]", "'")
	            .replace(", ", "','").replace("https", "").replace("http", "").replace(":", "").replace("/", "")
	            .replace("www", "").replace(".", "");
		
		String sqlCreateCharacteristic = "insert ignore into "
											+ "caracteristica_usuario (id_usuario, id_atributo, data_coletagem, possibilidade) "
										+ "select "
											+ this.idUser + ", "
											+ "a.id_atributo, "
											+ "'" + gottenDateString + "', "
											+ this.possibility + " "
										+ "from "
											+ "atributo a "
										+ "where "
											+ "a.fb_uri in (" + allAttributesString + ")";
		
		int insertResult;
		this.status = "error";
		try {
			insertResult = db.runInsertUpdateDeleteSql(sqlCreateCharacteristic);
			
			if (insertResult > 0)
			{		
				String updateUserLastUpdateDate = "update "
													+ "usuario "
												+ "set "
													+ "data_ultima_atualizacao = '" + gottenDateString + "' "
												+ "where "
													+ "id_usuario = " + this.idUser + "";
				
				int updateResult = db.runInsertUpdateDeleteSql(updateUserLastUpdateDate);
				
				if (updateResult > 0)
				{
					this.status = "success";
				}
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return this;
	}
}
