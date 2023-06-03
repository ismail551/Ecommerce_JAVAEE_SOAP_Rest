package com.mycompany.jee.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


import com.mycompany.jee.entities.*;
import com.mycompany.jee.tools.DBconnection;

public class clientDAO {

	public clientDAO() {
		// TODO Auto-generated constructor stub
	}
	
	ResultSet rs;
	
	public ArrayList<client> getClients(){
						DBconnection db = DBconnection.getInstance();

		ArrayList<client> clients = new ArrayList<client>();
		
			if(db != null) {
				
				rs = db.get("select * from client");
				
				try {
					while(rs.next()) {
						
						client c = new client();
						
						c.setId(rs.getInt(1));
						c.setFirstName(rs.getString(2));
						c.setLastName(rs.getString(3));
						c.setEmail(rs.getString(4));
						c.setPhone(rs.getString(5));
						c.setPassword(rs.getString(6));
						
						clients.add(c);
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		
		return clients;
	}
	
	
public client getClientById(int id){
										DBconnection db = DBconnection.getInstance();

			client c = null;
			
			if(db != null) {
				
				c = new client();

				rs = db.get("select * from client where id = "+ id);
				
				try {
					while(rs.next()) {
						
						
						c.setId(rs.getInt(1));
						c.setFirstName(rs.getString(2));
						c.setLastName(rs.getString(3));
						c.setEmail(rs.getString(4));
						c.setPhone(rs.getString(5));
						c.setPassword(rs.getString(6));
						
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		
		return c;
	}

public client login(String email, String password) {
	client c = null;
							DBconnection db = DBconnection.getInstance();

	if(db!=null) {
		
		
		rs = db.get("select * from client where email ='"+email+"' and password ='"+password+"'");
		
		try {
			while(rs.next()) {
				c = new client();
				c.setId(rs.getInt(1));
				c.setFirstName(rs.getString(2));
				c.setLastName(rs.getString(3));
				c.setEmail(rs.getString(4));
				c.setPhone(rs.getString(5));;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	return c;
}

public client register(client c) {
							DBconnection db = DBconnection.getInstance();

	client c1= null;
	
	if(db!=null) {
		
		
		int res = db.update("insert into client(firstName, lastName, email, phone, password) "
				+ "values('"+c.getFirstName()+"','"+c.getLastName()+"','"+c.getEmail()+"','"+c.getPhone()+"','"+c.getPassword()+"')");
		
		rs = db.get("select * from client where email ='"+c.getEmail()+"' and password ='"+c.getPassword()+"'");
		
		try {
			while(rs.next()) {
				
				c1 = new client();
				
				c1.setId(rs.getInt(1));
				c1.setFirstName(rs.getString(2));
				c1.setLastName(rs.getString(3));
				c1.setEmail(rs.getString(4));
				c1.setPhone(rs.getString(5));;
				
			} 
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	return c1;
	
}


public admin adminLogin(String email, String password) {
	admin a = null;
							DBconnection db = DBconnection.getInstance();

	if(db!=null) {
		
		
		rs = db.get("select * from admin where email ='"+email+"' and password ='"+password+"'");
		
		try {
			while(rs.next()) {
				a = new admin();
				a.setId(rs.getInt(1));
				a.setName(rs.getString(2));
				a.setEmail(rs.getString(3));
				a.setPassword(rs.getString(4));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	return a;
}
	
}
