package de.fleischer.gxt2.client;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.extjs.gxt.ui.client.Style.HorizontalAlignment;
import com.extjs.gxt.ui.client.data.BaseModel;
import com.extjs.gxt.ui.client.event.ButtonEvent;
import com.extjs.gxt.ui.client.event.Events;
import com.extjs.gxt.ui.client.event.MenuEvent;
import com.extjs.gxt.ui.client.event.SelectionListener;
import com.extjs.gxt.ui.client.store.ListStore;
import com.extjs.gxt.ui.client.widget.ContentPanel;
import com.extjs.gxt.ui.client.widget.Info;
import com.extjs.gxt.ui.client.widget.Viewport;
import com.extjs.gxt.ui.client.widget.grid.ColumnConfig;
import com.extjs.gxt.ui.client.widget.grid.ColumnModel;
import com.extjs.gxt.ui.client.widget.grid.Grid;
import com.extjs.gxt.ui.client.widget.menu.Menu;
import com.extjs.gxt.ui.client.widget.menu.MenuItem;
import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.user.client.ui.RootPanel;

import de.fleischer.gxt2.client.button.ConfigurationButton;


public class GXT2EntryPoint implements EntryPoint {

	public void onModuleLoad() {
		// Create new viewport
		Viewport viewport = new Viewport();
		
		// Create content panel and add it to the viewport
		ContentPanel contentPanel = new ContentPanel();
		contentPanel.setHeaderVisible(false);
		contentPanel.setBorders(false);
		contentPanel.setButtonAlign(HorizontalAlignment.LEFT);
		viewport.add(contentPanel);
		
		// Create store without any elements
		ListStore<BaseModel> store = new ListStore<BaseModel>();
		
		// Create a new grid with 15 columns
		Grid<BaseModel> grid = new Grid<BaseModel>(store, new ColumnModel(createColumnConfiguration(15)));
		grid.setAutoHeight(true);
		grid.setAutoWidth(true);
		
		// Add grid to view port
		contentPanel.add(grid);
		
		// Add two buttons to viewport
		addButtons(contentPanel, grid);
		
		// Add viewport to root panel
		RootPanel.get().add(viewport);
	}
	
	/**
	 * Creates column configuration for grid's column model.
	 * 
	 * @param numColumns
	 * 		  Number of columns
	 * 
	 * @return Created columns
	 */
	private List<ColumnConfig> createColumnConfiguration(int numColumns) {
		List<ColumnConfig> configs = new ArrayList<ColumnConfig>();
		
		// Create n columns
		for (int i = 1; i <= numColumns; i++) {
			configs.add(new ColumnConfig(String.valueOf(i), "Column " + i, 100));
		}
		
		return configs;
	}
	
	/**
	 * Add two buttons to the content panel to reconfigure the grid.
	 * 
	 * @param contentPanel
	 * 		  Content panel
	 */
	private void addButtons(final ContentPanel contentPanel, final Grid<BaseModel> grid) {
		List<ConfigurationButton> configurationButtons = new ArrayList<ConfigurationButton>();
		
		// Create button and add selection listener to reconfigure grid columns
		ConfigurationButton button = new ConfigurationButton("Configuration 1");
		button.addSelectionListener(new SelectionListener<ButtonEvent>() {
			
			@Override
			public void componentSelected(ButtonEvent ce) {
				// Reconfigure grid
				reconfigureColumns(grid, Arrays.asList("1", "3", "8", "9", "10", "13"));
			}
		});
		
		// Add button to content panel and configuration button list
		contentPanel.addButton(button);
		configurationButtons.add(button);
		
		// Create button and add selection listener to reconfigure grid columns
		button = new ConfigurationButton("Configuration 2");
		button.addSelectionListener(new SelectionListener<ButtonEvent>() {
			
			@Override
			public void componentSelected(ButtonEvent ce) {
				// Reconfigure grid
				reconfigureColumns(grid, Arrays.asList("2", "5", "6", "7", "11", "12", "15"));
			}
		});
		
		// Add button to content panel and configuration button list
		contentPanel.addButton(button);
		configurationButtons.add(button);
		
		// Create button and add selection listener to reconfigure grid columns
		button = new ConfigurationButton("Reset");
		button.addSelectionListener(new SelectionListener<ButtonEvent>() {
			
			@Override
			public void componentSelected(ButtonEvent ce) {
				// Collect all column ids
				List<String> allColumnIds = new ArrayList<String>();
				for (int i = 1; i <= grid.getColumnModel().getColumnCount(); i++) {
					allColumnIds.add(String.valueOf(i));
				}
				
				// Reset grid
				reconfigureColumns(grid, allColumnIds);
			}
		});
		
		// Add button to content panel
		contentPanel.addButton(button);
		
		// Create button with menu
		button = new ConfigurationButton("Menu Button");
		button.setMenu(new Menu());
		
		// Add button to content panel
		contentPanel.addButton(button);
		
		// Add buttons configuration buttons as menu entries
		addMenuEntires(button.getMenu(), configurationButtons);
	}
	
	/**
	 * Create menu entry for each button and add it to the menu.
	 * 
	 * @param menu
	 * 		  Menu
	 * 
	 * @param buttons
	 * 		  Buttons which shall be added
	 */
	private void addMenuEntires(Menu menu, List<ConfigurationButton> buttons) {
		// Create menu entry for each button
		MenuItem menuItem;
		for (final ConfigurationButton button : buttons) {
			// Create new menu entry
			menuItem = new MenuItem(button.getHtml());
			
			// Add listener to delegate selection to button
			menuItem.addSelectionListener(new SelectionListener<MenuEvent>() {

				@Override
				public void componentSelected(MenuEvent ce) {
					button.fireEvent(Events.Select);
				}
			});
			
			// Add menu entry to button
			button.setMenuEntry(menuItem);
			
			// Add menu entry to menu
			menu.add(menuItem);
		}
	}
	
	/**
	 * Reconfigures the grid and shows only the specific columns. All other columns 
	 * are hided.
	 *   
	 * @param grid
	 * 		  Grid
	 * 
	 * @param columnIds
	 * 		  Ids of columns
	 */
	public void reconfigureColumns(Grid<BaseModel> grid, List<String> columnIds) {
		// Get current time as start time
		long start = new Date().getTime();
		
		// Get current column model
		ColumnModel currentColumnModel = grid.getColumnModel();
		
		List<ColumnConfig> columns = currentColumnModel.getColumns();
		for (ColumnConfig columnConfig : columns) {
			// Get index of column
			int columnIndex = currentColumnModel.findColumnIndex(columnConfig.getId());
			
			// Change visibility of column depending on given column id list
			if(columnIds.contains(columnConfig.getId())) {
				currentColumnModel.setHidden(columnIndex, false);
			} else {
				currentColumnModel.setHidden(columnIndex, true);
			}
		}
		
		Info.display("Reconfiguration duration", "Duration to reconfigurate grid: " + (new Date().getTime() - start) + " milliseconds...");
	}
}
