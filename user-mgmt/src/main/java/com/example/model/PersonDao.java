package com.example.model;

import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

public class PersonDao {
	private static final Map<Integer, Person> ID_TO_PERSON = new HashMap<>(20);
	
	static {
		ID_TO_PERSON.put(1,  new Person(1,  "Jane",   "Doe",      "jane.doe@gmail.com",        20, CityDao.getById(1), true));
	    ID_TO_PERSON.put(2,  new Person(2,  "Emma",   "Powerful", "emma.powerful@hotmail.com", 18, CityDao.getById(3), false));
	    ID_TO_PERSON.put(3,  new Person(3,  "John",   "Smith",    "john.smith@yahoo.com",      26, CityDao.getById(3), false));
	    ID_TO_PERSON.put(4,  new Person(4,  "Jake",   "User",     "jake.user@live.com",        15, CityDao.getById(2), false));
	    ID_TO_PERSON.put(5,  new Person(5,  "Alice",  "Johnson",  "alice.johnson@outlook.com", 22, CityDao.getById(1), true));
	    ID_TO_PERSON.put(6,  new Person(6,  "Bob",    "Brown",    "bob.brown@gmail.com",       30, CityDao.getById(1), true));
	    ID_TO_PERSON.put(7,  new Person(7,  "Carol",  "Davis",    "carol.davis@yahoo.com",     27, CityDao.getById(1), false));
	    ID_TO_PERSON.put(8,  new Person(8,  "David",  "Miller",   "david.miller@aol.com",      24, CityDao.getById(2), false));
	    ID_TO_PERSON.put(9,  new Person(9,  "Eve",    "Wilson",   "eve.wilson@hotmail.com",    19, CityDao.getById(3), false));
	    ID_TO_PERSON.put(10, new Person(10, "Frank",  "Moore",    "frank.moore@gmail.com",     23, CityDao.getById(3), true));
	    ID_TO_PERSON.put(11, new Person(11, "Grace",  "Taylor",   "grace.taylor@yahoo.com",    21, CityDao.getById(2), false));
	    ID_TO_PERSON.put(12, new Person(12, "Hank",   "Anderson", "hank.anderson@live.com",    28, CityDao.getById(3), true));
	    ID_TO_PERSON.put(13, new Person(13, "Ivy",    "Thomas",   "ivy.thomas@outlook.com",    17, CityDao.getById(1), true));
	    ID_TO_PERSON.put(14, new Person(14, "Jack",   "Jackson",  "jack.jackson@gmail.com",    29, CityDao.getById(1), true));
	    ID_TO_PERSON.put(15, new Person(15, "Kara",   "White",    "kara.white@hotmail.com",    16, CityDao.getById(1), true));
	    ID_TO_PERSON.put(16, new Person(16, "Liam",   "Harris",   "liam.harris@yahoo.com",     25, CityDao.getById(2), false));
	    ID_TO_PERSON.put(17, new Person(17, "Mia",    "Martin",   "mia.martin@mail.com",       20, CityDao.getById(3), false));
	    ID_TO_PERSON.put(18, new Person(18, "Noah",   "Thompson", "noah.thompson@aol.com",     18, CityDao.getById(3), false));
	    ID_TO_PERSON.put(19, new Person(19, "Olivia", "Garcia",   "olivia.garcia@gmail.com",   22, CityDao.getById(1), true));
	    ID_TO_PERSON.put(20, new Person(20, "Pete",   "Martinez", "pete.martinez@outlook.com", 24, CityDao.getById(2), false));
	    
	    ID_TO_PERSON.put(21, new Person(21,  "Jane2",   "Doe",      "jane.doe@gmail.com",        20, CityDao.getById(1), true));
	    ID_TO_PERSON.put(22, new Person(22,  "Emma2",   "Powerful", "emma.powerful@hotmail.com", 18, CityDao.getById(3), false));
	    ID_TO_PERSON.put(23, new Person(23,  "John2",   "Smith",    "john.smith@yahoo.com",      26, CityDao.getById(3), false));
	    ID_TO_PERSON.put(24, new Person(24,  "Jake2",   "User",     "jake.user@live.com",        15, CityDao.getById(2), false));
	    ID_TO_PERSON.put(25, new Person(25,  "Alice2",  "Johnson",  "alice.johnson@outlook.com", 22, CityDao.getById(1), true));
	    ID_TO_PERSON.put(26, new Person(26,  "Bob2",    "Brown",    "bob.brown@gmail.com",       30, CityDao.getById(1), true));
	    ID_TO_PERSON.put(27, new Person(27,  "Carol2",  "Davis",    "carol.davis@yahoo.com",     27, CityDao.getById(1), false));
	    ID_TO_PERSON.put(28, new Person(28,  "David2",  "Miller",   "david.miller@aol.com",      24, CityDao.getById(2), false));
	    ID_TO_PERSON.put(29, new Person(29,  "Eve2",    "Wilson",   "eve.wilson@hotmail.com",    19, CityDao.getById(3), false));
	    ID_TO_PERSON.put(30, new Person(30, "Frank2",  "Moore",    "frank.moore@gmail.com",     23, CityDao.getById(3), true));
	    ID_TO_PERSON.put(31, new Person(31, "Grace2",  "Taylor",   "grace.taylor@yahoo.com",    21, CityDao.getById(2), false));
	    ID_TO_PERSON.put(32, new Person(32, "Hank2",   "Anderson", "hank.anderson@live.com",    28, CityDao.getById(3), true));
	    ID_TO_PERSON.put(33, new Person(33, "Ivy2",    "Thomas",   "ivy.thomas@outlook.com",    17, CityDao.getById(1), true));
	    ID_TO_PERSON.put(34, new Person(34, "Jack2",   "Jackson",  "jack.jackson@gmail.com",    29, CityDao.getById(1), true));
	    ID_TO_PERSON.put(35, new Person(35, "Kara2",   "White",    "kara.white@hotmail.com",    16, CityDao.getById(1), true));
	    ID_TO_PERSON.put(36, new Person(36, "Liam2",   "Harris",   "liam.harris@yahoo.com",     25, CityDao.getById(2), false));
	    ID_TO_PERSON.put(37, new Person(37, "Mia2",    "Martin",   "mia.martin@mail.com",       20, CityDao.getById(3), false));
	    ID_TO_PERSON.put(38, new Person(38, "Noah2",   "Thompson", "noah.thompson@aol.com",     18, CityDao.getById(3), false));
	    ID_TO_PERSON.put(39, new Person(39, "Olivia2", "Garcia",   "olivia.garcia@gmail.com",   22, CityDao.getById(1), true));
	    ID_TO_PERSON.put(40, new Person(40, "Pete2",   "Martinez", "pete.martinez@outlook.com", 24, CityDao.getById(2), false));
	}
	
	public static synchronized Vector<Person> getPeople() {
		return new Vector<Person>(ID_TO_PERSON.values());
	}
	
	public static synchronized Person getById(Integer id) {
		return ID_TO_PERSON.get(id);
	}
	
	public static synchronized void save(Person person) {
		ID_TO_PERSON.put(person.id(), person);
	}
	
	public static synchronized Integer getNextId() {
		return ID_TO_PERSON.keySet().stream().max(Comparator.naturalOrder()).get() + 1;
	}
	
	public static synchronized boolean isExistingRecord(Person p) {
		return ID_TO_PERSON.containsKey(p.id());
	}
}
