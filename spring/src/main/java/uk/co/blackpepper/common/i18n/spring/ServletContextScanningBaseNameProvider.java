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

import javax.servlet.ServletContext;

import org.springframework.core.io.ContextResource;
import org.springframework.core.io.Resource;
import org.springframework.web.context.support.ServletContextResourcePatternResolver;

public class ServletContextScanningBaseNameProvider extends AbstractScanningBaseNameProvider {

	public ServletContextScanningBaseNameProvider(ServletContext servletContext) {
		super(new ServletContextResourcePatternResolver(servletContext));
	}

	@Override
	protected String getResourceLocation(Resource resource) {
		return ((ContextResource) resource).getPathWithinContext();
	}
}
