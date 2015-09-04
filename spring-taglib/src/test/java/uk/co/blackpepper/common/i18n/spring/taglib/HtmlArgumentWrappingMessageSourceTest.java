package uk.co.blackpepper.common.i18n.spring.taglib;

import java.util.Locale;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertThat;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class HtmlArgumentWrappingMessageSourceTest {

	private MessageSource delegate;
	
	private HtmlStringGenerator htmlStringGenerator;
	
	private HtmlArgumentWrappingMessageSource messageSource;
	
	@Before
	public void setUp() {
		delegate = mock(MessageSource.class);
		htmlStringGenerator = mock(HtmlStringGenerator.class);
		
		messageSource = new HtmlArgumentWrappingMessageSource(delegate, htmlStringGenerator);
	}
	
	@Test
	public void getMessageWithDefaultInvokesDelegateWithNoArgs() {
		when(delegate.getMessage(any(String.class), any(Object[].class), any(String.class), any(Locale.class)))
			.thenReturn("");
		
		messageSource.getMessage("x", new Object[] {"arg"}, "default", Locale.JAPAN);
		
		verify(delegate).getMessage("x", null, "default", Locale.JAPAN);
	}

	@Test
	public void getMessageWithDefaultInvokesGenerator() {
		when(delegate.getMessage(any(String.class), any(Object[].class), any(String.class), any(Locale.class)))
			.thenReturn("x");
		
		messageSource.getMessage("x", new Object[] {"arg"}, "default", Locale.JAPAN);
		
		verify(htmlStringGenerator).toHtml("x", new Object[] {"arg"});
	}

	@Test
	public void getMessageWithNoDefaultInvokesDelegateWithNoArgs() {
		when(delegate.getMessage(any(String.class), any(Object[].class), any(Locale.class))).thenReturn("");
		
		messageSource.getMessage("x", new Object[] {"arg"}, Locale.JAPAN);
		
		verify(delegate).getMessage("x", null, Locale.JAPAN);
	}
	
	@Test
	public void getMessageWithNoDefaultInvokesGenerator() {
		when(delegate.getMessage(any(String.class), any(Object[].class), any(Locale.class))).thenReturn("x");
		
		messageSource.getMessage("x", new Object[] {"arg"}, Locale.JAPAN);
		
		verify(htmlStringGenerator).toHtml("x", new Object[] {"arg"});
	}

	@Test
	public void getMessageWithMessageSourceResolvableInvokesDelegateWithNoArgs() {
		when(delegate.getMessage(any(MessageSourceResolvable.class), any(Locale.class))).thenReturn("");
		MessageSourceResolvable resolvable = mock(MessageSourceResolvable.class);
		when(resolvable.getArguments()).thenReturn(new Object[] {"arg"});
		
		messageSource.getMessage(resolvable, Locale.JAPAN);
		
		ArgumentCaptor<MessageSourceResolvable> captor = ArgumentCaptor.forClass(MessageSourceResolvable.class);
		verify(delegate).getMessage(captor.capture(), eq(Locale.JAPAN));
		assertThat(captor.getValue().getArguments(), is(nullValue()));
	}
	
	@Test
	public void getMessageWithMessageSourceResolvableInvokesGenerator() {
		when(delegate.getMessage(any(MessageSourceResolvable.class), any(Locale.class))).thenReturn("x");
		MessageSourceResolvable resolvable = mock(MessageSourceResolvable.class);
		when(resolvable.getArguments()).thenReturn(new Object[] {"arg"});
		
		messageSource.getMessage(resolvable, Locale.JAPAN);
		
		verify(htmlStringGenerator).toHtml("x", new Object[] {"arg"});
	}
}
