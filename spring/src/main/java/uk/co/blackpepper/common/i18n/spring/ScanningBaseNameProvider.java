package uk.co.blackpepper.common.i18n.spring;

import java.io.IOException;
import java.util.List;

public interface ScanningBaseNameProvider {
	
	List<String> getBaseNames(String locationPrefix) throws IOException;
}
