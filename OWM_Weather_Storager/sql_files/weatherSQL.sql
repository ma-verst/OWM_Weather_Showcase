CREATE DATABASE weather;
CREATE TABLE location(cityID INT NOT NULL, lon DOUBLE, lat DOUBLE, cityName VARCHAR(255), PRIMARY KEY (cityID));
CREATE TABLE weatherConditions(id INT NOT NULL AUTO_INCREMENT, recordedTime INT, temperature DOUBLE, pressure INT, humidity INT, min_temperature DOUBLE, max_temperature DOUBLE, cityID INT, PRIMARY KEY (id), FOREIGN KEY(cityID) REFERENCES location(cityID));