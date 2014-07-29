package de.fleischer.gxt2.client.button;

import com.extjs.gxt.ui.client.widget.button.Button;
import com.extjs.gxt.ui.client.widget.menu.MenuItem;

public class ConfigurationButton extends Button {
	/**
	 * Constructor
	 */
	public ConfigurationButton() {
		super();
	}
	
	/**
	 * Constructor
	 * 
	 * @param html
	 * 		  Text as HTML
	 * 		  
	 */
	public ConfigurationButton(String html) {
		super(html);
	}
	
	/**
	 * Corresponding menu entry
	 */
	private MenuItem menuEntry;

	/**
	 * Returns corresponding menu entry.
	 * 
	 * @return Corresponding menu entry
	 */
	public MenuItem getMenuEntry() {
		return menuEntry;
	}

	/**
	 * Sets corresponding menu entry.
	 * 
	 * @param menuEntry
	 * 		  Corresponding menu entry
	 */
	public void setMenuEntry(MenuItem menuEntry) {
		this.menuEntry = menuEntry;
	}
	
	/**
	 * Returns whether the button has a corresponding 
	 * menu entry.
	 * 
	 * @return Result
	 */
	public boolean hasMenuEntry() {
		return menuEntry != null;
	}
}
