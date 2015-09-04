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
