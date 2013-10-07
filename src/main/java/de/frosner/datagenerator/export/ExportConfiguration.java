package de.frosner.datagenerator.export;

/**
 * Interface representing a configuration that can be used to create an {@linkplain ExportConnection}. Configurations
 * are unfinished connections created by the front end and handed to a service. The service then creates and opens the
 * corresponding {@linkplain ExportConnection}. This avoids connection handling in the front end thread.
 */
public interface ExportConfiguration {

	/**
	 * Create the corresponding {@linkplain ExportConnection} from the current {@linkplain ExportConfiguration}.
	 * 
	 * @return {@linkplain ExportConnection} created
	 */
	public ExportConnection createExportConnection();

}
