package com.example.model;

import java.util.HashMap;
import java.util.Map;

public class CityDao {
	
	private static final Map<Integer, City> CITIES = new HashMap<>();
	
	static { 
		CITIES.put(1, new City(1, "Turku"));
		CITIES.put(2, new City(2, "Berlin"));
		CITIES.put(3, new City(3, "San Jose"));
		CITIES.put(4, new City(4, "Frankfurt"));
		CITIES.put(5, new City(5, "New York"));
		CITIES.put(6, new City(6, "Stockholm"));
		CITIES.put(7, new City(7, "Oslo"));
		CITIES.put(8, new City(8, "Paris"));
		CITIES.put(9, new City(9, "Tokyo"));
		CITIES.put(10, new City(10, "Beijing"));
		CITIES.put(11, new City(11, "Shanghai"));
		CITIES.put(12, new City(12, "Jakarta"));
		CITIES.put(13, new City(13, "New Delhi"));
	};
	
	public static synchronized City[] getAll() {
		return CITIES.values().toArray(new City[0]);
	}
	
	public static synchronized City getById(Integer id) {
		return CITIES.get(id);
	}
	
}
