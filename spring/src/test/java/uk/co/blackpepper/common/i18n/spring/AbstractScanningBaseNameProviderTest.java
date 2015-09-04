package uk.co.blackpepper.common.i18n.spring;

import java.io.IOException;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.ResourcePatternResolver;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.contains;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class AbstractScanningBaseNameProviderTest {

	private ResourcePatternResolver resourcePatternResolver;
	
	private AbstractScanningBaseNameProvider provider;
	
	@Before
	public void setUp() {
		resourcePatternResolver = mock(ResourcePatternResolver.class);
		provider = newAbstractScanningBaseNameProvider(resourcePatternResolver);
	}

	@Test
	public void getBaseNamesReturnsNames() throws IOException {
		when(resourcePatternResolver.getResources("/dir/**/*.properties"))
			.thenReturn(new Resource[] {new FileSystemResource("/dir/x.properties")});
		
		assertThat(provider.getBaseNames("/dir/"), contains("/dir/x"));
	}
	
	@Test
	public void getBaseNamesFiltersLocaleSpecificNames() throws IOException {
		when(resourcePatternResolver.getResources("/dir/**/*.properties"))
			.thenReturn(new Resource[] {new FileSystemResource("/dir/x_en.properties")});
		
		assertThat(provider.getBaseNames("/dir/"), is(Matchers.<String>empty()));
	}

	private static AbstractScanningBaseNameProvider newAbstractScanningBaseNameProvider(
		ResourcePatternResolver resourcePatternResolver) {
		return new AbstractScanningBaseNameProvider(resourcePatternResolver) {
			@Override
			protected String getResourceLocation(Resource resource) {
				return ((FileSystemResource) resource).getPath();
			}
		};
	}
}
