/*
 * Copyright 2014 Black Pepper Software
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
