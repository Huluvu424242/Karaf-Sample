
package de.inovex.javamagazin.ui.command;

import java.util.List;

import org.apache.karaf.shell.commands.Command;
import org.apache.karaf.shell.commands.Option;
import org.apache.karaf.shell.console.OsgiCommandSupport;
import org.apache.karaf.shell.table.Col;
import org.apache.karaf.shell.table.ShellTable;

import de.inovex.javamagazin.domain.InventoryCategory;
import de.inovex.javamagazin.jpa.InventoryDAO;

@Command(scope = "ube", name = "ListCategory", description = "CRUD commands for the JavaMagazin samples")
public class ListCategory extends OsgiCommandSupport {

    @Option(name = "--no-format", description = "Disable table rendered output", required = false, multiValued = false)
    boolean noFormat;
    
	private InventoryDAO broker;
	
	public void setBroker(InventoryDAO broker) {
		this.broker = broker;
	}
    
    protected Object doExecute() throws Exception {
    	
    	List<InventoryCategory> allCategories = broker.getAllCategories();
    	ShellTable table = new ShellTable();
        table.column(new Col("ID"));
        table.column(new Col("Name"));
        table.column(new Col("Description"));
        
        for (InventoryCategory category : allCategories) {
        	
        	table.addRow().addContent(
        			category.getId(),
        			category.getCategoryName(),
        			category.getCategoryDescription()
        			);
			
		}
        
        table.print(System.out, !noFormat);
    	
    	return null;
    }
}
