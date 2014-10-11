
package de.inovex.javamagazin.ui.command;

import org.apache.karaf.shell.commands.Argument;
import org.apache.karaf.shell.commands.Command;
import org.apache.karaf.shell.commands.Option;
import org.apache.karaf.shell.console.OsgiCommandSupport;

import de.inovex.javamagazin.domain.InventoryCategory;
import de.inovex.javamagazin.jpa.InventoryDAO;

@Command(scope = "ube", name = "CreateItem", description = "CRUD commands for the JavaMagazin samples")
public class CreateItem extends OsgiCommandSupport {

    @Option(name = "-d", aliases = { "--description" }, description = "A description to the item", required = false, multiValued = false)
    private String description;
    
    @Option(name = "-p", aliases = { "--price"}, description = "The item price", required = true, multiValued = false)
    private Float price;

    @Option(name = "-cat", aliases = { "--category" }, description = "The Category this item belongs to", required = true, multiValued = false)
    private String category;
    
    @Argument(name = "name", description = "Name of Category", required = true, multiValued = false)
    private String name;

	private InventoryDAO broker;
	
	public void setBroker(InventoryDAO broker) {
		this.broker = broker;
	}
    
    protected Object doExecute() throws Exception {
    	
    	InventoryCategory inventoryCategory = broker.getCategoryByName(category);
    	
		broker.addItem(name, description, price, inventoryCategory.getId());
    	
    	return null;
    }
}
