package com.poixson.nettet;


public interface Pipe<E, D> { // encoded, decoded


	public Class<? extends E> getEncodedType();
	public Class<? extends D> getDecodedType();

	public void setParentChild(
			final Pipe<?, ?> parent,
			final Pipe<?, ?> child);

	public void readMessage (final E msg);
	public void writeMessage(final D msg);


}
