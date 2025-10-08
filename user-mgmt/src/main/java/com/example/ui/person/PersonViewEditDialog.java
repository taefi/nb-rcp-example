package com.example.ui.person;

import com.example.abstraction.CrudFormObserver;
import com.example.abstraction.CrudGridObservable;
import com.example.abstraction.RowSelectionChangeEvent;
import com.example.utility.SpringUtilities;
import com.example.model.City;
import com.example.model.CityDao;
import com.example.model.Person;
import com.example.model.PersonDao;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedHashMap;
import java.util.Map;


public class PersonViewEditDialog extends JDialog implements CrudFormObserver<Person> {
	
	private static final Map<String, JTextField> TEXT_FIELDS = new LinkedHashMap<>();
	
	static {
		TEXT_FIELDS.put("id:", new JTextField()); 
		TEXT_FIELDS.put("First name:", new JTextField()); 
		TEXT_FIELDS.put("Last name:", new JTextField()); 
		TEXT_FIELDS.put("Email:", new JTextField()); 
		TEXT_FIELDS.put("Age:", new JTextField());		
	}
	
	private CrudGridObservable<Person> gridObservable;
	
	private JComboBox<City> hqComboBox = new JComboBox<>(CityDao.getAll());

	private ButtonGroup radioButtonGroup = new ButtonGroup();
	private JRadioButton yes = new JRadioButton("Yes");
	private JRadioButton no  = new JRadioButton("No");

	public PersonViewEditDialog() {
		setSize(350, 280);
		
		JPanel fieldsPanel = new JPanel(new SpringLayout());
		TEXT_FIELDS.entrySet().forEach(entry -> {
			if (!"id:".equals(entry.getKey())) {
	            JLabel label = new JLabel(entry.getKey(), JLabel.TRAILING);
	            fieldsPanel.add(label);
	            label.setLabelFor(entry.getValue());
	            fieldsPanel.add(entry.getValue());
			}
        });
		
		JLabel hqCityLabel = new JLabel("HQ City:", JLabel.TRAILING);
        fieldsPanel.add(hqCityLabel);
        hqCityLabel.setLabelFor(hqComboBox);
        fieldsPanel.add(hqComboBox);
        hqComboBox.setSelectedItem(null);
		
		radioButtonGroup.add(yes);
		radioButtonGroup.add(no);
		
		JPanel radioButtonPanel = new JPanel(new FlowLayout(FlowLayout.LEADING, 10, 0));
		radioButtonPanel.setMaximumSize(new Dimension(radioButtonPanel.getSize().width, 30));
		radioButtonPanel.add(yes);
		radioButtonPanel.add(no);
		
		JLabel partTimeLabel = new JLabel("Part time:", JLabel.TRAILING);
        fieldsPanel.add(partTimeLabel);
        partTimeLabel.setLabelFor(radioButtonPanel);
        fieldsPanel.add(radioButtonPanel);
        
		SpringUtilities.makeCompactGrid(fieldsPanel,
                6, 2,  //rows, cols
                6, 6,  //initX, initY
                6, 6); //xPad, yPad

		add(fieldsPanel, BorderLayout.CENTER);
		
		JPanel buttonsPanel = new JPanel(new FlowLayout(FlowLayout.LEADING, 10, 10));
		JButton save = new JButton("Save");
		save.addActionListener(e -> {
			PersonDao.save(writeRecord());
			gridObservable.refreshGrid();
			dispose();
		});
		JButton clear = new JButton("Clear");
		clear.addActionListener(e -> clearFields());
		JButton cancel = new JButton("Cancel");
		cancel.setToolTipText("Press 'Esc'");
		cancel.addActionListener(e -> dispose());
		buttonsPanel.add(save);
		buttonsPanel.add(clear);
		buttonsPanel.add(cancel);
		add(buttonsPanel, BorderLayout.SOUTH);
	}
	
	@Override
	public void selectedRowChanged(RowSelectionChangeEvent<Person> event) {
		if (event.newSelected() == null) {
			setVisible(false);
			return;
		}
		if (!isVisible()) {
			setVisible(true);
		}
		readRecord(event.newSelected());
	}
	
	private void readRecord(Person person) {
		if (person == null) {
			clearFields();
			return;
		}
		TEXT_FIELDS.get("id:").setText(String.valueOf(person.id()));
		TEXT_FIELDS.get("First name:").setText(person.firstName());
		TEXT_FIELDS.get("Last name:").setText(person.lastName());
		TEXT_FIELDS.get("Email:").setText(person.email());
		TEXT_FIELDS.get("Age:").setText(String.valueOf(person.age()));
		
		hqComboBox.setSelectedItem(person.hqCity());
		
		if (person.partTime()) {
			yes.setSelected(true);
		} else {
			no.setSelected(true);
		}
		
		if (PersonDao.isExistingRecord(person)) {
			setTitle(String.format("Editing: %s %s", person.firstName(), person.lastName()));
		} else {
			setTitle("Add New Customer");
		}
	}
	
	private Person writeRecord() {
		Integer id = Integer.parseInt(TEXT_FIELDS.get("id:").getText());
		String firstName = TEXT_FIELDS.get("First name:").getText();
		String lastName = TEXT_FIELDS.get("Last name:").getText();
		String email = TEXT_FIELDS.get("Email:").getText();
		Integer age = Integer.parseInt(TEXT_FIELDS.get("Age:").getText());
		City city = (City) hqComboBox.getSelectedItem();
		Boolean partTime = yes.getModel().equals(radioButtonGroup.getSelection());
		return new Person(id, firstName, lastName, email, age, city, partTime);
	}

	private void clearFields() {
		TEXT_FIELDS.values().forEach(field -> field.setText(null));
		hqComboBox.setSelectedItem(null);
		radioButtonGroup.clearSelection();
	}

	@Override
	public void setGridObservable(CrudGridObservable<Person> gridObservable) {
		this.gridObservable = gridObservable;
	}
}
