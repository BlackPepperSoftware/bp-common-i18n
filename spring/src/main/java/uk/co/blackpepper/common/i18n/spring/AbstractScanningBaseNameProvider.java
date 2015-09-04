package uk.co.blackpepper.common.i18n.spring;

import java.io.IOException;
import java.util.List;

import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;

import com.google.common.base.Function;
import com.google.common.base.Predicate;
import com.google.common.collect.FluentIterable;

import static java.util.Arrays.asList;

public abstract class AbstractScanningBaseNameProvider implements ScanningBaseNameProvider {
	
	private final ResourcePatternResolver resourcePatternResolver;
	
	protected AbstractScanningBaseNameProvider(ResourcePatternResolver resourcePatternResolver) {
		this.resourcePatternResolver = resourcePatternResolver;
	}
	
	@Override
	public List<String> getBaseNames(String locationPrefix) throws IOException {
		Resource[] propertiesResources = resourcePatternResolver.getResources(locationPrefix + "**/*.properties");
		
		return FluentIterable.from(asList(propertiesResources))
			.filter(baseNameResources())
			.transform(toResourceLocation())
			.transform(removeFileExtension())
			.toList();
	}

	private static Predicate<Resource> baseNameResources() {
		return new Predicate<Resource>() {
			@Override
			public boolean apply(Resource resource) {
				return !resource.getFilename().contains("_");
			}
		};
	}

	private Function<Resource, String> toResourceLocation() {
		return new Function<Resource, String>() {
			@Override
			public String apply(Resource resource) {
				return getResourceLocation(resource);
			}
		};
	}

	private static Function<String, String> removeFileExtension() {
		return new Function<String, String>() {
			@Override
			public String apply(String input) {
				return input.substring(0, input.length() - ".properties".length());
			}
		};
	}

	protected abstract String getResourceLocation(Resource resource);
}
