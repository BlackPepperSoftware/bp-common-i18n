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

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class HtmlStringGeneratorTest {

	private HtmlStringGenerator generator;

	private ExpectedException thrown = ExpectedException.none();
	
	@Rule
	public ExpectedException getThrown() {
		return thrown;
	}
	
	@Before
	public void setUp() {
		generator = new HtmlStringGenerator("span", "UTF-8");
	}

	@Test
	public void toHtmlWithLeadingTextWrapsArgument() {
		String actual = generator.toHtml("text {0:x}", new Object[] {"arg"});
		
		assertThat(actual, is("text <span class=\"x\">arg</span>"));
	}
	
	@Test
	public void toHtmlWithTrailingTextWrapsArgument() {
		String actual = generator.toHtml("{0:x} text", new Object[] {"arg"});
		
		assertThat(actual, is("<span class=\"x\">arg</span> text"));
	}
	
	@Test
	public void toHtmlWithIntermediateTextWrapsArguments() {
		String actual = generator.toHtml("{0:x} text {1:y}", new Object[] {"arg1", "arg2"});
		
		assertThat(actual, is("<span class=\"x\">arg1</span> text <span class=\"y\">arg2</span>"));
	}
	
	@Test
	public void toHtmlWithUndiscriminatedArgumentDoesNotWrap() {
		String actual = generator.toHtml("{0}", new Object[] {"arg"});
		
		assertThat(actual, is("arg"));
	}
	
	@Test
	public void toHtmlWithInvalidPartIdentifierThrowsException() {
		thrown.expect(InvalidPartIdentifierException.class);
		thrown.expectMessage("A");

		generator.toHtml("{0:A}", new Object[] {"arg"});
	}
	
	@Test
	public void toHtmlWithArgumentEscapes() {
		String actual = generator.toHtml("{0}", new Object[] {"&"});
		
		assertThat(actual, is("&amp;"));
	}
	
	@Test
	public void toHtmlWithTextEscapes() {
		String actual = generator.toHtml("&", new Object[0]);
		
		assertThat(actual, is("&amp;"));
	}
}
