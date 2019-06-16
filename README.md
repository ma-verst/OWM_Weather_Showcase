# OWM Weather Showcase (Heatmap of Germany)

This application serves as a showcase of the OpenWeatherMap (OWM) API and also serves as a private training project.
The idea of this project is to use the OWM API to get weather information about all locations available for Germany. Therefore OWM provides a list of all available locations which is used to send requests to the API.

![Alt text](OWM_Weather_Storager/images/map.png?raw=true "Heatmap of Germany")

## Overview of Functionality
  - Retrieve weather information using OWM API
  - Store location and weather information in MySQL database
  - Request Location + Temperature information from all entries of the database
  - Create HeapMap of Germany using the data provided by OWM
 
### 1) Retrieve weather information
A URL request is sent to the OWM API using their definition of requesting weather information.
Example request:

> https://api.openweathermap.org/data/2.5/weather?q=Darmstadt,de&appid=myKey

The response of this request comes as a JSON file which contains all available information about the location Darmstadt in Germany. 

As OWM provides a list of all available locations including the country code it is possible to retrieve weather information of any location, in this project we use all locations having the country code of Germany (DE).
A parser finally extracts relevant information for further usage.

### 2) Store weather information in MySQL DB
After retrieving the weather information of a location we store this information within a database. The database consists of two tables: "location" and "weatherconditions".
"location" contains fields like the coordinates and the name of a location.
"weatherconditions" contains fields like the temperature, humidity, pressure of a location.

A DBHandler uses a SqlConnector to connect to a local database. The handler provides methods for storing location and weather entries. In order to create such an entry, container classes are provided which can be fitted with all information from the JSON file.

After the weather and location information of all locations in Germany are stored in the database we can plot a heatmap of Germany.

### 3) Retrieving data from MySQL database
As proposed before the DBHandler not only serves as a helper to store entries, it also provides methods to retrieve data from the database. The handler is able to request the full database table contents in order to extract relevant information for the heatmap.

### 4) Plotting a heatmap of Germany
For plotting a heatmap, JFreeChart (http://www.jfree.org/jfreechart/) is used. From the database we first need the longitude, latitude and temperature of all available locations. We then fit these informations within a XYZDataset which is used in the HeatMapPainter class to create the heatmap. Here we use the following convention:
> X = Longitude
> Y = Latitude
> Z = Temperature

Finally to correspond a temperature to an appropriate color for the map, a conversion table is provided which converts temperatures in the range of -30 to +40 degree celsius to a RGB spectrum. The conversion finally allows us to plot the locations using X and Y values for the position and the Z value for the color.

### Installation
- Pull project into your workspace
- Execute weatherSQL.sql file located in sql_files folder to create the database and the tables
- Dependencies should be downloaded automatically by Maven

### Todos
- Currently random points are used for the heatmap
- Extract data from DB and use it for XYZDataset
- Check license
- Create runnable project from command line
- Read in OWM API key using command line instead of hard coding

License
----
?
