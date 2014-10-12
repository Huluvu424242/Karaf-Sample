package de.inovex.javamagazin.ui.command;

import org.apache.karaf.shell.commands.Argument;
import org.apache.karaf.shell.commands.Command;
import org.apache.karaf.shell.commands.Option;
import org.apache.karaf.shell.console.OsgiCommandSupport;

import de.inovex.javamagazin.jpa.InventoryRepository;

@Command(scope = "ube", name = "DeleteCategory", description = "CRUD commands for the JavaMagazin samples")
public class DeleteCategory extends OsgiCommandSupport {

	@Argument(name = "name", description = "Name of Category", required = false, multiValued = false)
	private String name;
	
	//name = "-d", aliases = { "--description" }
	@Option(name = "-id", description = "ID of the Category", required = false, multiValued = false)
	private String id; 

	private InventoryRepository broker;

	public void setBroker(InventoryRepository broker) {
		this.broker = broker;
	}

	protected Object doExecute() throws Exception {

		if (name != null) {
			broker.deleteCategoryByName(name);
		} else if (id != null) {
			broker.deleteCategory(Integer.parseInt(id));
		}

		return null;
	}
}
