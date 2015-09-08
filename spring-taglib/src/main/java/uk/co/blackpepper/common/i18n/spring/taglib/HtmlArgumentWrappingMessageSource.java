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
