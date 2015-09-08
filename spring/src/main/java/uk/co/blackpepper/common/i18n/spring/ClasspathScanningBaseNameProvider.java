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
