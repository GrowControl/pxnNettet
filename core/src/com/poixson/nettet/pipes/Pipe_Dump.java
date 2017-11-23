package com.poixson.nettet.pipes;

import com.poixson.nettet.Pipe;
import com.poixson.utils.Dumper;


public class Pipe_Dump implements Pipe<Object, Boolean> {

	protected Pipe<?, Object>  parent = null;



	public Pipe_Dump() {
	}



	@SuppressWarnings("unchecked")
	@Override
	public void setParentChild(
			final Pipe<?, ?> parent,
			final Pipe<?, ?> child) {
		this.parent = (Pipe<?, Object>) parent;
		if (child != null) throw new UnsupportedOperationException("Cannot set a child pipe on an exit point.");
	}
	@Override
	public Class<Object> getEncodedType() {
		return Object.class;
	}
	@Override
	public Class<Boolean> getDecodedType() {
		return Boolean.class;
	}



	@Override
	public void readMessage(final Object obj) {
		Dumper.print(obj);
	}



	@Override
	public void writeMessage(final Boolean ignored) {
		throw new UnsupportedOperationException("Cannot write to a dump pipe.");
	}



	@Override
	public boolean canRead() {
		return true;
	}
	@Override
	public boolean canWrite() {
		return false;
	}



}
