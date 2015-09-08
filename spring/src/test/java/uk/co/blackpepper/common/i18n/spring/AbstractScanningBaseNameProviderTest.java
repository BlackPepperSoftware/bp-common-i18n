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
