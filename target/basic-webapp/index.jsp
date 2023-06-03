<jsp:include page="header.jsp"></jsp:include>    


<%@page import="java.util.*"%>
<%@page import="java.io.*"%>
<%@page import="com.mycompany.jee.controlers.*"%>
<%@page import="com.mycompany.jee.dao.*"%>
<%@page import="com.mycompany.jee.tools.DBconnection"%>

<%@page import="com.mycompany.jee.entities.*"%>
<%@ page import="org.json.JSONArray" %>
<%@ page import="org.json.JSONObject" %>
<%@ page import="java.io.BufferedReader" %>
<%@ page import="java.io.InputStreamReader" %>
<%@ page import="java.net.HttpURLConnection" %>
<%@ page import="java.net.URL" %>
<%@ page import="java.lang.StringBuilder" %>


<%!
private String sendGetRequest(String urlStr) {
    StringBuilder jsonResponse = new StringBuilder();
    try {
        URL url = new URL(urlStr);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");

        int responseCode = connection.getResponseCode();
        if (responseCode == HttpURLConnection.HTTP_OK) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                jsonResponse.append(line);
            }
            reader.close();
        } else {
            // Handle the error case
            jsonResponse.append("Error retrieving data. Response code: ").append(responseCode);
        }
        connection.disconnect();
    } catch (IOException e) {
        e.printStackTrace();
        // Handle the exception
        jsonResponse.append("Exception occurred: ").append(e.getMessage());
    }
    return jsonResponse.toString();
}

%>
<%
    
    
    
    
    
    
    
    
    categorieDAO cdao = new categorieDAO();
    ArrayList<categorie> categos = cdao.getcategories();
    ArrayList<phone> phones = null; // Declare phones variable outside of the if block

    String urlStr = "http://localhost:8080/JEE/webresources/phones";
    URL url = new URL(urlStr);
    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
    connection.setRequestMethod("GET");

    // Get the response from the server
    int responseCode = connection.getResponseCode();
    if (responseCode == HttpURLConnection.HTTP_OK) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder jsonResponse = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            jsonResponse.append(line);
        }
        reader.close();

        // Parse the JSON response
        JSONArray jsonPhones = new JSONArray(jsonResponse.toString());

        // Convert JSON objects to Phone objects
        phones = new ArrayList<>();
        for (int i = 0; i < jsonPhones.length(); i++) {
            JSONObject jsonPhone = jsonPhones.getJSONObject(i);
            phone p = new phone();
            p.setId(jsonPhone.getInt("id"));
            p.setName(jsonPhone.getString("name"));
            p.setPrice(jsonPhone.getDouble("price"));
            p.setImage(jsonPhone.getString("image"));
            // Set other properties if needed
            phones.add(p);
        }
    }
        connection.disconnect();

    // Process the phones ArrayList outside of the if block
    // ...
%>






                    <div class="hero__item set-bg " data-setbg="img/hero/lop.jpg">
                        <div class="hero__text">
                            <span>SAMSUNG</span>
                            <h2>Galaxy <br />S22 Ultra 5G</h2>
                            <p>Free Pickup and Delivery Available</p>
                            <a href="#" class="primary-btn">SHOP NOW</a>
                        </div>
                    </div>
              	</div>
            </div>
    </section>
    <!-- Hero Section End -->
    
        <!-- Categories Section Begin -->
    <section class="categories">
        <div class="container">
            <div class="row">
             <div class="categories__slider owl-carousel">
    <%
    try {
        URL url1 = new URL("http://localhost:8080/JEE/webresources/categories");
        HttpURLConnection connection1 = (HttpURLConnection) url1.openConnection();
        connection1.setRequestMethod("GET");

        // Get the response from the server
        int responseCode1 = connection1.getResponseCode();
        if (responseCode1  == HttpURLConnection.HTTP_OK) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection1.getInputStream()));
            StringBuilder jsonResponse = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                jsonResponse.append(line);
            }
            reader.close();

            // Parse the JSON response
            JSONArray jsonArray = new JSONArray(jsonResponse.toString());
            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonCategorie = jsonArray.getJSONObject(i);
                categorie c = new categorie();
                c.setId(jsonCategorie.getInt("id"));
                c.setName(jsonCategorie.getString("name"));
    %>
                <div class="col-lg-3">
                    <div class="categories__item set-bg" data-setbg="categories/cat<%= i %>.jpg">
                        <h5><a href="categorie.jsp?id=<%= c.getId() %>"><%= c.getName() %></a></h5>
                    </div>
                </div>
    <%
            }
        } else {
            // Handle the error case
            %>
            <p style="color:red;"><i style="margin-right:10px;" class="fa-solid fa-triangle-exclamation"></i>UNE ERREUR EST SURVENUE</p>
            <%
        }
        connection1.disconnect();


    } catch (IOException e) {
        e.printStackTrace();
        // Handle the exception
        // ...
}

    %>
</div>

            </div>
        </div>
    </section>
    <!-- Categories Section End -->
    
    
      <!-- Featured Section Begin -->
    <section class="featured spad">
        <div class="container">
            <div class="row">
                <div class="col-lg-12">
                    <div class="section-title">
                        <h2>Featured Product</h2>
                    </div>
                    <div class="featured__controls">
                        <ul>
                        <li class="active" data-filter="*">All</li>
                        <% 
		                       if(categos != null){	
			
		                    	   for(categorie c : categos){
													
						%>
                            
                            <li data-filter="<%=c.getName() %>"><%=c.getName() %></li>
                        
                        <%}} %>
                        </ul>
                    </div>
                </div>
            </div>
            <div class="row featured__filter">
            	<% 
		                       if(phones != null){	
			
									for(phone p : phones){
										
									categorie c1 = cdao.getcategorieById(p.getCategorie());
										
				%>
                <div style="margin-bottom:50px" class="col-lg-3 col-md-4 col-sm-6 mix <%=c1.getName() %>">
                    <div class="featured__item">
                        <div class="featured__item__pic set-bg" data-setbg="phones/<%=p.getImage()%>">
                            <ul class="featured__item__pic__hover">
                                <li><a href="phone.jsp?id=<%=p.getId() %>"><i class="fa fa-eye"></i></a></li>
                            </ul>
                        </div>
                        <div class="featured__item__text">
                            <h6><a href="phone.jsp?id=<%=p.getId() %>"><%=p.getName() %></a></h6>
                            <h5>$<%=p.getPrice() %></h5>
                        </div>
                    </div>
                </div>
                <%}}else{ %>
									<p style="color:red;"><i style="margin-right:10px;" class="fa-solid fa-triangle-exclamation"></i>UNE ERREUR EST SURVENUE</p>
				<%} %>
            </div>
        </div>
    </section>
    <!-- Featured Section End -->
    
    


<jsp:include page="footer.jsp"></jsp:include>    


