package uk.co.blackpepper.common.i18n.spring;

import java.io.IOException;
import java.net.URL;

import org.junit.Test;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class ClasspathScanningBaseNameProviderTest {

	@Test
	public void getResourceLocationWithClasspathResourceReturnsPrefixedPath() {
		ClasspathScanningBaseNameProvider provider = new ClasspathScanningBaseNameProvider(null);
		
		String actual = provider.getResourceLocation(new ClassPathResource("x"));
		
		assertThat(actual, is("classpath:x"));
	}

	@Test
	public void getResourceLocationWithFilesystemResourceReturnsPath() {
		ClasspathScanningBaseNameProvider provider = new ClasspathScanningBaseNameProvider(null);
		
		String actual = provider.getResourceLocation(new FileSystemResource("/x"));
		
		assertThat(actual, is("/x"));
	}

	@Test
	public void getResourceLocationWithOtherResourceReturnsUrl() throws IOException {
		ClasspathScanningBaseNameProvider provider = new ClasspathScanningBaseNameProvider(null);
		Resource resource = mock(Resource.class);
		when(resource.getURL()).thenReturn(new URL("http://x"));
		
		String actual = provider.getResourceLocation(resource);
		
		assertThat(actual, is("http://x"));
	}
}
