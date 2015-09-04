package uk.co.blackpepper.common.i18n.spring.taglib;

import java.util.Locale;

import javax.servlet.jsp.PageContext;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.MessageSource;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockPageContext;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.support.RequestContext;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.web.servlet.support.RequestContext.WEB_APPLICATION_CONTEXT_ATTRIBUTE;
import static org.springframework.web.servlet.tags.RequestContextAwareTag.REQUEST_CONTEXT_PAGE_ATTRIBUTE;

public class HtmlArgumentWrappingMessageTagTest {

	private HtmlArgumentWrappingMessageTag tag;
	
	private MessageSource messageSource;

	@Before
	public void setUp() throws Exception {
		tag = new HtmlArgumentWrappingMessageTag();
		
		MockHttpServletRequest request = new MockHttpServletRequest();
		messageSource = mock(WebApplicationContext.class);
		request.setAttribute(WEB_APPLICATION_CONTEXT_ATTRIBUTE, messageSource);
		
		PageContext pageContext = new MockPageContext();
		pageContext.setAttribute(REQUEST_CONTEXT_PAGE_ATTRIBUTE, new RequestContext(request));
		tag.setPageContext(pageContext);
		
		tag.doStartTag();
	}

	@Test
	public void getMessageSourceWrapsArgument() {
		when(messageSource.getMessage(anyString(), any(Object[].class), any(Locale.class))).thenReturn("{0:x}");
		
		String message = tag.getMessageSource().getMessage(null, new Object[] {"y"}, null);
		
		assertThat(message, is("<span class=\"x\">y</span>"));
	}

	@Test
	public void getMessageSourceWhenElementNameWrapsArgument() {
		tag.setElementName("div");
		when(messageSource.getMessage(anyString(), any(Object[].class), any(Locale.class))).thenReturn("{0:x}");
		
		String message = tag.getMessageSource().getMessage(null, new Object[] {"y"}, null);
		
		assertThat(message, is("<div class=\"x\">y</div>"));
	}
	
	@Test
	public void isHtmlEscapeReturnsFalse() {
		assertThat(tag.isHtmlEscape(), is(false));
	}
}
