package com.example.ui.person;

import com.example.model.Person;

import javax.swing.table.AbstractTableModel;
import java.util.Arrays;
import java.util.Vector;

public class PersonTableModel extends AbstractTableModel {
	
	private static final Vector<String> COLUMN_NAMES = new Vector<>(
			Arrays.asList("First name", "Last name", "Email", "Age", "Part time?"));	
	
	private static final Class<?>[] COLUMN_CLASSES = new Class[] {
		String.class, // firstName
		String.class, // lastName 
		String.class, // email
		Integer.class, // age
		Boolean.class, // partTime
		Integer.class, // id
	};
	
    private Vector<Person> people;

    public PersonTableModel(Vector<Person> people) {
        this.people = people;
    }
    
    public Vector<Person> getPeople() {
		return people;
	}

	public void setPeople(Vector<Person> people) {
		this.people = people;
		fireTableDataChanged();
	}

	@Override
    public int getRowCount() {
        return people.size();
    }

    @Override
    public int getColumnCount() {
        return COLUMN_NAMES.size();
    }

    @Override
    public String getColumnName(int column) {
        return COLUMN_NAMES.get(column);
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
    	return COLUMN_CLASSES[columnIndex];
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Person p = people.get(rowIndex);
        switch (columnIndex) {
            case 0: return p.firstName();
            case 1: return p.lastName();
            case 2: return p.email();
            case 3: return p.age();
            case 4: return p.partTime();
            case 5: return p.id();
            default: throw new IllegalArgumentException("Invalid column");
        }
    }
    
    public Integer getRowId(int row) {
    	return (Integer) getValueAt(row, 5);
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return false;  // or true if you want editing
    }
}

