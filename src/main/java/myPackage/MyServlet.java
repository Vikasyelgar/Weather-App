package myPackage;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

/**
 * Servlet implementation class MyServlet
 */
public class MyServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MyServlet() {
        super();
    }

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// API Setup
		String apiKey = "799720740cfd65b57e449a9b2de3e4bf";
		
		// Get the city from the form input
		String city = request.getParameter("city");
		
		// URL-encode the city name to handle spaces and special characters
		String encodedCity = URLEncoder.encode(city, StandardCharsets.UTF_8.toString());
		
		// Create the URL for the OpenWeatherMap API request
		String apiUrl = "https://api.openweathermap.org/data/2.5/weather?q=" + encodedCity + "&appid=" + apiKey;
		
		// API Integration
		URL url = new URL(apiUrl);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();
		connection.setRequestMethod("GET");
		
		// Check if the request was successful (HTTP status code 200)
		int statusCode = connection.getResponseCode();
		if (statusCode == 200) {
			// Reading the data from network
			InputStream inputStream = connection.getInputStream();
			InputStreamReader reader = new InputStreamReader(inputStream);
			
			// Store the API response in a StringBuilder
			Scanner scanner = new Scanner(reader);
			StringBuilder responseContent = new StringBuilder();
			
			while(scanner.hasNext()) {
				responseContent.append(scanner.nextLine());
			}
			
			scanner.close();
			
			// Output the API response to the console (for debugging)
			System.out.println(responseContent);
			
			// Optionally, you can set the response to be displayed in the browser
			response.setContentType("application/json");
			response.getWriter().write(responseContent.toString());
		} else {
			// Handle errors if the API request was not successful
			System.err.println("Error: " + statusCode + " - " + connection.getResponseMessage());
			response.getWriter().write("Error: Unable to fetch weather data. Please try again.");
		}
	}
}
