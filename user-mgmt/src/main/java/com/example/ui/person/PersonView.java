package com.example.ui.person;

import com.example.abstraction.CrudFormObserver;
import com.example.abstraction.CrudGridObservable;
import com.example.abstraction.HasKeyBindings;
import com.example.abstraction.RowSelectionChangeEvent;
import com.example.model.Person;
import com.example.model.PersonDao;
import com.example.ui.person.renderer.BoldHeaderRenderer;
import com.example.ui.person.renderer.CenterAlignedCellRenderer;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public class PersonView extends JPanel implements ListSelectionListener, CrudGridObservable<Person>, HasKeyBindings {

	private static final int WIDTH = 800;
	private static final int HEIGHT = 450;
	
	private final List<CrudFormObserver<Person>> crudFormObservers = new ArrayList<>();
	
	private JTable table;
	private PersonTableModel tableModel;
	private JButton deleteButton;
	private JButton newButton;
	
	private Person selectedPerson;
	
	// TODO: Uncomment for Swing version of Edit dialog:
	private PersonViewEditDialog personForm;
	
	enum State {
		NO_ROW_SELECTED, VIEW_EDIT, NEW
	}
	
	private State state;
	
	public PersonView() {
		init();
	}
	
	private void init() {
		setLayout(new BorderLayout());
		setSize(new Dimension(WIDTH, HEIGHT));
		
		deleteButton = new JButton("Delete (F4)");
		deleteButton.addActionListener(e -> deleteSelectedRow());
		deleteButton.setEnabled(false);
		
		newButton = new JButton("New");
		newButton.setToolTipText("Press 'Ctrl + N'");
		newButton.addActionListener(e -> prepareNewRecord());
		
		JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.TRAILING, 10, 10));
		buttonPanel.add(newButton);
		buttonPanel.add(deleteButton);
		add(buttonPanel, BorderLayout.NORTH);
		
		tableModel = new PersonTableModel(PersonDao.getPeople());
		table = new JTable(tableModel) {
			@Override
			public void changeSelection(int rowIndex, int columnIndex, boolean toggle, boolean extend) {
				// combination of toggle=true and extend=false means to toggle selection
				super.changeSelection(rowIndex, columnIndex, true, false);
			}
		};
		table.setRowHeight(30);
		//table.addMouseListener(new TableRightClickListener(table));
		table.setComponentPopupMenu(createTablePopupMenu());
		
		TableCellRenderer originalHeaderRenderer = table.getTableHeader().getDefaultRenderer();
		for (int i = 0; i < table.getColumnCount(); i++) {
			table.getColumnModel().getColumn(i).setHeaderRenderer(new BoldHeaderRenderer(originalHeaderRenderer));
		}
		
		table.getColumnModel().getColumn(2).setMinWidth(200);
		
		table.setDefaultRenderer(Integer.class, new CenterAlignedCellRenderer());
		
		table.setAutoCreateRowSorter(true);
		
		table.getSelectionModel().addListSelectionListener(this);
		table.getSelectionModel().setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		
		JScrollPane content = new JScrollPane(table);
		content.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		add(content);
		
		// TODO: Uncomment for Swing version of Edit dialog:
		if (crudFormObservers.isEmpty()) {
			personForm = new PersonViewEditDialog();
			this.addCrudFormObserver(personForm);	
		}
		
		setFormState(State.NO_ROW_SELECTED);
	}
	
	public void initKeyBindings() {
		JRootPane root = getRootPane();
        InputMap  inputMap = root.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = root.getActionMap();
		setupNewRecordShortcut(inputMap, actionMap);
		setupDeleteRecordShortcut(inputMap, actionMap);
	}

	private void setupNewRecordShortcut(InputMap inputMap, ActionMap actionMap) {
		KeyStroke ctrlN = KeyStroke.getKeyStroke(KeyEvent.VK_N, InputEvent.CTRL_DOWN_MASK);
		inputMap.put(ctrlN, "newRow");
		actionMap.put("newRow", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				prepareNewRecord();
			}
		});
	}

	private void setupDeleteRecordShortcut(InputMap inputMap, ActionMap actionMap) {
		KeyStroke f4 = KeyStroke.getKeyStroke(KeyEvent.VK_F4, 0);
		inputMap.put(f4, "deleteRow");
		actionMap.put("deleteRow", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				deleteSelectedRow();
			}
		});
	}
	
	private void prepareNewRecord() {
		if (this.state == State.NEW) {
			return;
		}
		refreshGrid();
		setFormState(State.NEW);
		Person newPerson = new Person(PersonDao.getNextId(), null, null, null, 0, null, false);
		fireSelectedRowChanged(selectedPerson, newPerson);
	}
	
	private void deleteSelectedRow() {
		if (this.state != State.VIEW_EDIT) {
			return;
		}
		JOptionPane.showConfirmDialog(this, 
				String.format("Are you sure about about removing the following person? %n %s", 
						getSelectedPerson()), "Warning...", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
	}

	@Override
	public void valueChanged(ListSelectionEvent e) {
		if (e.getValueIsAdjusting()) {
			return;
		}
		setFormState(table.getSelectionModel().isSelectionEmpty() ? State.NO_ROW_SELECTED : State.VIEW_EDIT);
		fireSelectedRowChanged(selectedPerson, getSelectedPerson());
	};

	private Person getSelectedPerson() {
		if (table.getSelectionModel().isSelectionEmpty()) {
			return null;
		}
		int row = table.convertRowIndexToModel(table.getSelectedRow());
		Integer id = ((PersonTableModel) table.getModel()).getRowId(row);
		return PersonDao.getById(id);
	}

	@Override
	public void addCrudFormObserver(CrudFormObserver<Person> observer) {
		if (!crudFormObservers.contains(observer)) {
			crudFormObservers.add(observer);
			observer.setGridObservable(this);
		}
	}

	protected void fireSelectedRowChanged(Person oldSelected, Person newSelected) {		
		this.selectedPerson = newSelected;
		crudFormObservers.forEach(observer -> 
			observer.selectedRowChanged(new RowSelectionChangeEvent<>(oldSelected, newSelected)));
	}

	@Override
	public void refreshGrid() {
		tableModel.setPeople(PersonDao.getPeople());
		setFormState(State.NO_ROW_SELECTED);
	}
	
	private void setFormState(State state) {
		this.state = state;
		determineToolbarState();
	}
	
	private void determineToolbarState() {
		switch (this.state) {
		case NEW:
			newButton.setEnabled(false);
			deleteButton.setEnabled(false);
			break;
		case VIEW_EDIT:
			newButton.setEnabled(true);
			deleteButton.setEnabled(true);
			break;
		case NO_ROW_SELECTED:
			newButton.setEnabled(true);
			deleteButton.setEnabled(false);
			break;
		default:
			throw new IllegalArgumentException("Unexpected value: " + this.state);
		}
	}
	
	private JPopupMenu createTablePopupMenu() {
		JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem addMenuItem = new JMenuItem("Add New Row");
        addMenuItem.addActionListener(e -> prepareNewRecord());        
        popupMenu.add(addMenuItem);
        JMenuItem editMenuItem = new JMenuItem("Edit Current Row");        
        popupMenu.add(editMenuItem);
        JMenuItem deleteMenuItem = new JMenuItem("Delete Current Row");
        deleteMenuItem.addActionListener(e -> deleteSelectedRow());
        popupMenu.add(deleteMenuItem);
        return popupMenu;
	}
	
}
