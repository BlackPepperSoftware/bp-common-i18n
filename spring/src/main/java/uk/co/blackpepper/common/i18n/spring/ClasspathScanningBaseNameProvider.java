package uk.co.blackpepper.common.i18n.spring;

import java.io.IOException;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.util.ResourceUtils;

public class ClasspathScanningBaseNameProvider extends AbstractScanningBaseNameProvider {

	public ClasspathScanningBaseNameProvider(ResourcePatternResolver resourcePatternResolver) {
		super(resourcePatternResolver);
	}

	@Override
	protected String getResourceLocation(Resource resource) {
		String location = null;
		
		if (resource instanceof ClassPathResource) {
			// Should be a ClassPathResource if in a JAR
			location = ResourceUtils.CLASSPATH_URL_PREFIX + ((ClassPathResource) resource).getPath();
		}
		else if (resource instanceof FileSystemResource) {
			// Should be a FileSystemResource otherwise - unless it's in JBoss VFS.
			location = ((FileSystemResource) resource).getPath();
		}
		else {
			// Fallback to URL if we can't determine location otherwise.
			try {
				location = resource.getURL().toString();
			}
			catch (IOException exception) {
				throw new IllegalArgumentException("Couldn't determine URL from resource", exception);
			}
		}
		
		return location;
	}
}
