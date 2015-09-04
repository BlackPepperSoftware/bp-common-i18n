package uk.co.blackpepper.common.i18n.spring;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import javax.servlet.ServletContext;

import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;

import static java.util.Collections.singleton;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/*
 * Most of this functionality has been moved to AbstractScanningBaseNameProvider. These tests
 * have been retained here as they *could* be useful in the detection of changes to Spring
 * framework behaviour (AbstractScanningBaseNameProviderTest tests only test interaction with
 * ResourcePatternResolver and do not attempt to verify its behaviour.)
 */
public class ServletContextScanningBaseNameProviderTest {

	private ServletContext servletContext;
	
	private ServletContextScanningBaseNameProvider provider;
	
	@Before
	public void setUp() throws MalformedURLException {
		servletContext = mock(ServletContext.class);
		when(servletContext.getResource(any(String.class))).thenReturn(new URL("file:///dummy"));
		
		provider = new ServletContextScanningBaseNameProvider(servletContext);
	}

	@Test
	public void getBaseNamesReturnsNames() throws IOException {
		when(servletContext.getResourcePaths("/dir/")).thenReturn(singleton("/dir/x.properties"));
		
		assertThat(provider.getBaseNames("/dir/"), contains("/dir/x"));
	}

	@Test
	public void getBaseNamesReturnsSubdirectoryNames() throws IOException {
		when(servletContext.getResourcePaths("/dir/")).thenReturn(singleton("/dir/subdir/"));
		when(servletContext.getResourcePaths("/dir/subdir/")).thenReturn(singleton("/dir/subdir/x.properties"));
		
		assertThat(provider.getBaseNames("/dir/"), contains("/dir/subdir/x"));
	}
	
	@Test
	public void getBaseNamesFiltersLocaleSpecificNames() throws IOException {
		when(servletContext.getResourcePaths("/dir/")).thenReturn(singleton("/dir/x_en.properties"));
		
		assertThat(provider.getBaseNames("/dir/"), is(Matchers.<String>empty()));
	}

	@Test
	public void getBaseNamesFiltersNonPropertiesNames() throws IOException {
		when(servletContext.getResourcePaths("/dir/")).thenReturn(singleton("/dir/x.ext"));
		
		assertThat(provider.getBaseNames("/dir/"), is(Matchers.<String>empty()));
	}
}
