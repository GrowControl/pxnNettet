package com.poixson.nettet;


public interface Pipeline<E, D> { // encoded, decoded


	public Class<? extends E> getEncodedType();
	public Class<? extends D> getDecodedType();

	public void setParentChild(
			final Pipeline<?, ?> parent,
			final Pipeline<?, ?> child);

	public void readMessage (final E msg);
	public void writeMessage(final D msg);


}
