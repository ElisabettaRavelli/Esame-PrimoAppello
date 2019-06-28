package it.polito.tdp.db;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import it.polito.tdp.model.Event;


public class EventsDao {
	
	public List<Event> listAllEvents(){
		String sql = "SELECT * FROM events" ;
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Event> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(new Event(res.getLong("incident_id"),
							res.getInt("offense_code"),
							res.getInt("offense_code_extension"), 
							res.getString("offense_type_id"), 
							res.getString("offense_category_id"),
							res.getTimestamp("reported_date").toLocalDateTime(),
							res.getString("incident_address"),
							res.getDouble("geo_lon"),
							res.getDouble("geo_lat"),
							res.getInt("district_id"),
							res.getInt("precinct_id"), 
							res.getString("neighborhood_id"),
							res.getInt("is_crime"),
							res.getInt("is_traffic")));
				} catch (Throwable t) {
					t.printStackTrace();
					System.out.println(res.getInt("id"));
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}
	
	public List<String> listCategorie(){
		String sql = "select distinct offense_category_id from events ";
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<String> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(res.getString("offense_category_id"));
							
				} catch (Throwable t) {
					t.printStackTrace();
					System.out.println("Errore nel db");
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}
	
	public List<Integer> listMesi(){
		String sql = "select distinct month(reported_date) as mesi from events ";
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			
			List<Integer> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(res.getInt("mesi"));
							
				} catch (Throwable t) {
					t.printStackTrace();
					System.out.println("Errore nel db");
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}
	
	public List<String> listVertici(String categoria, Integer mese){
		String sql = "select distinct offense_type_id from events " + 
				"where offense_category_id = ? and month(reported_date) = ? ";
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setString(1, categoria);
			st.setInt(2, mese);
			List<String> list = new ArrayList<>() ;
			
			ResultSet res = st.executeQuery() ;
			
			while(res.next()) {
				try {
					list.add(res.getString("offense_type_id"));
							
				} catch (Throwable t) {
					t.printStackTrace();
					System.out.println("Errore nel db");
				}
			}
			
			conn.close();
			return list ;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}
	
	public Integer getPeso(String categoria, Integer mese, String v1, String v2){
		String sql = "select count(distinct(e1.neighborhood_id)) as peso " + 
				"from events e1, events e2 " + 
				"where e1.neighborhood_id = e2.neighborhood_id " + 
				"and e1.offense_category_id = ? " + 
				"and month(e1.reported_date) = ? " + 
				"and e2.offense_category_id = ? " + 
				"and month(e2.reported_date) = ? " + 
				"and e1.offense_type_id = ? " + 
				"and e2.offense_type_id = ? ";
		try {
			Connection conn = DBConnect.getConnection() ;

			PreparedStatement st = conn.prepareStatement(sql) ;
			st.setString(1, categoria);
			st.setInt(2, mese);
			st.setString(3, categoria);
			st.setInt(4, mese);
			st.setString(5, v1);
			st.setString(6, v2);
			
			ResultSet res = st.executeQuery() ;
			Integer peso;
			
			if(res.next()) {
				try {
					peso = res.getInt("peso");
					conn.close();
					return peso;
							
				} catch (Throwable t) {
					t.printStackTrace();
					System.out.println("Errore nel db");
				}
			}
			
			conn.close();
			return 0 ;

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null ;
		}
	}
	
	
}
