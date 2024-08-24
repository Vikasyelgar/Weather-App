<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Weather Information</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            background: #f0f0f0;
            color: #333;
            text-align: center;
            margin: 0;
            padding: 20px;
        }
        .container {
            max-width: 800px;
            margin: auto;
            background: #fff;
            padding: 20px;
            border-radius: 10px;
            box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
        }
        h1 {
            font-size: 2em;
            margin-bottom: 20px;
        }
        .weather-info {
            font-size: 1.2em;
            margin-bottom: 20px;
        }
        .weather-info span {
            display: block;
            margin: 5px 0;
        }
        .error {
            color: red;
            font-weight: bold;
        }
    </style>
</head>
<body>
    <div class="container">
        <h1>Weather Information</h1>
        <%
            // Assuming weatherData is a JSON string set in the servlet
            String weatherData = (String) request.getAttribute("weatherData");
            if (weatherData != null) {
                // Parse JSON using the org.json library
                org.json.JSONObject jsonObject = new org.json.JSONObject(weatherData);
                String cityName = jsonObject.getString("name");
                double temperature = jsonObject.getJSONObject("main").getDouble("temp") - 273.15; // Convert from Kelvin to Celsius
                String weatherDescription = jsonObject.getJSONArray("weather").getJSONObject(0).getString("description");
                String icon = jsonObject.getJSONArray("weather").getJSONObject(0).getString("icon");
                
                // Example URL for the weather icon
                String iconUrl = "http://openweathermap.org/img/wn/" + icon + "@2x.png";
        %>
        <div class="weather-info">
            <h2>City: <%= cityName %></h2>
            <img src="<%= iconUrl %>" alt="Weather Icon" />
            <span>Temperature: <%= String.format("%.1f", temperature) %>°C</span>
            <span>Weather: <%= weatherDescription %></span>
        </div>
        <%
            } else {
        %>
        <div class="error">Error: Unable to fetch weather data. Please try again.</div>
        <%
            }
        %>
    </div>
</body>
</html>
