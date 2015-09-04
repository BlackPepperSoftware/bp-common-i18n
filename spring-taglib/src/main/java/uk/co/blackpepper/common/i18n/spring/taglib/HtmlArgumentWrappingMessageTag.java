package uk.co.blackpepper.common.i18n.spring.taglib;

import org.springframework.context.MessageSource;
import org.springframework.web.servlet.tags.MessageTag;

@SuppressWarnings("serial")
public class HtmlArgumentWrappingMessageTag extends MessageTag {

	private String elementName;
	
	public HtmlArgumentWrappingMessageTag() {
		setElementName("span");
	}

	public void setElementName(String elementName) {
		this.elementName = elementName;
	}

	@Override
	protected MessageSource getMessageSource() {
		String characterEncoding = pageContext.getResponse().getCharacterEncoding();
		HtmlStringGenerator htmlStringGenerator = new HtmlStringGenerator(elementName, characterEncoding);
		return new HtmlArgumentWrappingMessageSource(super.getMessageSource(), htmlStringGenerator);
	}
	
	@Override
	protected boolean isHtmlEscape() {
		// HTML escaping is handled in the MessageSource
		return false;
	}
}
