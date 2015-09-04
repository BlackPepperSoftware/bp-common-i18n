package uk.co.blackpepper.common.i18n.spring.taglib;

import org.springframework.context.MessageSourceResolvable;

class ArgumentIgnoringMessageSourceResolvableWrapper implements MessageSourceResolvable {

	private final MessageSourceResolvable delegate;
	
	public ArgumentIgnoringMessageSourceResolvableWrapper(MessageSourceResolvable delegate) {
		this.delegate = delegate;
	}
	
	@Override
	public String[] getCodes() {
		return delegate.getCodes();
	}

	@Override
	public Object[] getArguments() {
		return null;
	}

	@Override
	public String getDefaultMessage() {
		return delegate.getDefaultMessage();
	}
}
