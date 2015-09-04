package uk.co.blackpepper.common.i18n.spring.taglib;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;

public class HtmlArgumentWrappingMessageSource implements MessageSource {

	private final MessageSource delegate;
	
	private final HtmlStringGenerator htmlStringGenerator;
	
	public HtmlArgumentWrappingMessageSource(MessageSource delegate, HtmlStringGenerator htmlStringGenerator) {
		this.delegate = delegate;
		this.htmlStringGenerator = htmlStringGenerator;
	}

	@Override
	public String getMessage(String code, Object[] args, Locale locale) {
		return htmlStringGenerator.toHtml(delegate.getMessage(code, null, locale), args);
	}
	
	@Override
	public String getMessage(String code, Object[] args, String defaultMessage, Locale locale) {
		return htmlStringGenerator.toHtml(delegate.getMessage(code, null, defaultMessage, locale), args);
	}
	
	@Override
	public String getMessage(MessageSourceResolvable resolvable, Locale locale) {
		String message = delegate.getMessage(new ArgumentIgnoringMessageSourceResolvableWrapper(resolvable), locale);
		return htmlStringGenerator.toHtml(message, resolvable.getArguments());
	}
}
