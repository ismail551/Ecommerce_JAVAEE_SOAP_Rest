package com.mycompany.jee.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;


import com.mycompany.jee.entities.*;
import com.mycompany.jee.tools.DBconnection;

public class categorieDAO {

	public categorieDAO() {
		// TODO Auto-generated constructor stub
	}
	
	Statement st;
	ResultSet rs;
	
	
	
	public int addcategorie(categorie c) {
    int statut = 0;
    DBconnection db = DBconnection.getInstance();
    if (db != null) {
        statut = db.update("insert into categorie(name) values ('" + c.getName() + "')");
    } else {
        statut = -1;
    }
    return statut;
}

	
	
	
	
	
	public ArrayList<categorie> getcategories(){
		
		ArrayList<categorie> c = new ArrayList<categorie>();
		DBconnection db = DBconnection.getInstance();

		if(db != null) {		
				try {
					
					rs = db.get("select * from categorie");
					
					while(rs.next()) {
						categorie cn = new categorie();
						
						cn.setId(rs.getInt(1));
						cn.setName(rs.getString(2));
						
						c.add(cn);
					}
					
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}else {
			c = null;
		}
		
		return c;
	}
	
	
	
	public categorie getcategorieById(int id){
		DBconnection db = DBconnection.getInstance();

		categorie c = new categorie();
		if(db != null) {
				try {
					
					rs = db.get("select * from categorie where id = "+id);

					
					if(rs.next()) {
						
						c.setId(rs.getInt(1));
						c.setName(rs.getString(2));
						
						return c;
					}
					
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}else {
			c = null;
		}
		
		
		return c;
	}
	
	
	
	public int countPhonesByCategorie(int id){
		DBconnection db = DBconnection.getInstance();
           
		
		int count = 0;
		if(db != null) {
				try {
					
					rs = db.get("select count(*) from phone where categorie = "+id);

					
					if(rs.next()) {
						
						count = rs.getInt(1);
						
					}
					
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
		return count;
	}
	
	
	
	public int deleteCategorieById(int id) {
		int statut = 0;
                		DBconnection db = DBconnection.getInstance();

				
		if(db != null) {	

			statut = db.update("delete from categorie where id =" + id);

		}else {
			statut = -1;
		}
		
		return statut;
		
	}
	
	public int updateCategorie(int id, String name) {
				DBconnection db = DBconnection.getInstance();

		int res = 0;
		
		if(db != null) {	

			res = db.update("update categorie set name = '"+name+"' where id =" + id);

		}else {
			res = -1;
		}
		
		return res;
	}

	

}
