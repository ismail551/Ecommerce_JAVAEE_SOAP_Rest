package com.mycompany.jee.controlers;

import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;

import com.mycompany.jee.dao.clientDAO;
import com.mycompany.jee.entities.*;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.*;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.*;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

/**
 * Servlet implementation class accountClient
 */

public class accountClient extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public accountClient() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		int res = 0;
		HttpSession ss = request.getSession();
		
		String source = (String) ss.getAttribute("source");
		
		        client credentials = new client();
                        client cc = new client();
                       clientDAO cdao = new clientDAO();

                        
		
		if(request.getParameter("action") != null) {
			
			if(request.getParameter("action").equals("login")) {
				
				String email = request.getParameter("email");
				
				String password = org.apache.commons.codec.digest.DigestUtils.sha256Hex(request.getParameter("password")); 
				
                                credentials.setEmail(email);
                                credentials.setPassword(password);
                                // Send a POST request to the login endpoint of the RESTful service
                                HttpClient httpClient = HttpClientBuilder.create().build();
                                HttpPost httpPost = new HttpPost("http://localhost:8080/JEE/webresources/account/login");
                                httpPost.setHeader("Content-Type", "application/json");
                                httpPost.setEntity(new StringEntity(new Gson().toJson(credentials)));

                                HttpResponse httpResponse = httpClient.execute(httpPost);
                                 String responseBody = EntityUtils.toString(httpResponse.getEntity());

                                                // Process the HTTP response
                                    int statusCode = httpResponse.getStatusLine().getStatusCode();
                                    if (statusCode == HttpStatus.SC_OK) {
                        // Successful login
                        client c = new Gson().fromJson(responseBody, client.class);
                        System.out.println(c.getEmail());
                        HttpSession session = request.getSession();
                        session.setAttribute("client", c);
                        request.getRequestDispatcher("index.jsp").forward(request, response);
                    } else {
                        // Invalid credentials
                        request.getRequestDispatcher("login.jsp?err=-1").forward(request, response);
                    }
                        }
	            else if(request.getParameter("action").equals("register")) {
				
				cc.setFirstName(request.getParameter("firstName"));
				cc.setLastName(request.getParameter("lastName"));
				cc.setEmail(request.getParameter("email"));
				cc.setPhone(request.getParameter("phone"));
				cc.setPassword(org.apache.commons.codec.digest.DigestUtils.sha256Hex(request.getParameter("password")));
				
				
				
				if(cdao.login(cc.getEmail(), cc.getPassword()) == null) {
					
					client c2 = cdao.register(cc);
					
					if(c2 != null) {
						
						ss.setAttribute("client", c2);
						request.getRequestDispatcher("index.jsp").forward(request, response);
						
					}else {
						
						request.getRequestDispatcher("register.jsp?err="+-1).forward(request, response);
					}
				}else {
					
					cc = cdao.login(cc.getEmail(), cc.getPassword());

					
					if(cc != null) {
						
						ss.setAttribute("client", cc);
						request.getRequestDispatcher("index.jsp").forward(request, response);
						
					}else {
						
						request.getRequestDispatcher("register.jsp?err="+-1).forward(request, response);
						
					}
					
				}
				

			}else {
				
				response.sendRedirect(source+"?err="+-1);
			}
			
		}else{
			
			response.sendRedirect(source+"?err="+-1);
		}
		
	}

}
