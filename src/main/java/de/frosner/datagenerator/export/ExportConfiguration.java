package de.frosner.datagenerator.export;

/**
 * Interface representing a configuration that can be used to create an {@link ExportConnection}. Configurations are
 * unfinished connections created by the front end and handed to a service. The service then creates and opens the
 * corresponding {@link ExportConnection}. This avoids connection handling in the front end thread.
 */
public interface ExportConfiguration {

	/**
	 * Create the corresponding {@link ExportConnection} from the current {@link ExportConfiguration}.
	 * 
	 * @return {@link ExportConnection} created
	 */
	public ExportConnection createExportConnection();

}
