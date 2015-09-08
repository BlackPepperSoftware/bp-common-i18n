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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.web.util.HtmlUtils;

public class HtmlStringGenerator {

	private static final String PART_IDENTIFIER_ATTRIBUTE_NAME = "class";
	
	private static final Pattern ARGUMENT_PATTERN = Pattern.compile("\\{(\\d+)(?::([^\\}]+))?\\}");

	private static final String ARGUMENT_PART_IDENTIFIER_PATTERN_STRING = "[0-9a-z\\-]+";
	
	private final String elementName;
	
	private final String encoding;
	
	public HtmlStringGenerator(String elementName, String encoding) {
		this.elementName = elementName;
		this.encoding = encoding;
	}

	public String toHtml(String message, Object[] args) {
		Matcher matcher = ARGUMENT_PATTERN.matcher(message);
		StringBuilder builder = new StringBuilder();
		int cursor = 0;
		
		while (matcher.find()) {
			int argumentIndex = Integer.parseInt(matcher.group(1));
			String partIdentifier = matcher.group(2);
			
			validatePartIdentifier(partIdentifier);
			
			builder.append(escape(message.substring(cursor, matcher.start())));
								
			String argumentHtml = escape(String.valueOf(args[argumentIndex]));
			
			if (partIdentifier != null) {
				builder.append(createTag(partIdentifier, argumentHtml));
			}
			else {
				builder.append(argumentHtml);
			}
			
			cursor = matcher.end();
		}
		
		builder.append(escape(message.substring(cursor)));
		
		return builder.toString();
	}

	private String createTag(String partIdentifier, String html) {
		return String.format("<%1$s %2$s=\"%3$s\">%4$s</%1$s>", elementName, PART_IDENTIFIER_ATTRIBUTE_NAME,
			partIdentifier, html);
	}

	private String escape(String input) {
		return HtmlUtils.htmlEscape(input, encoding);
	}
	
	private static void validatePartIdentifier(String partIdentifier) {
		if (partIdentifier != null && !partIdentifier.matches(ARGUMENT_PART_IDENTIFIER_PATTERN_STRING)) {
			throw new InvalidPartIdentifierException(String.format("%s: name must match %s", partIdentifier,
				ARGUMENT_PART_IDENTIFIER_PATTERN_STRING));
		}
	}
}
