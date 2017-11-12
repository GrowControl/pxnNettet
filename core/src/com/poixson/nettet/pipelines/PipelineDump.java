package com.poixson.nettet.pipelines;

import com.poixson.nettet.Pipeline;
import com.poixson.utils.Dumper;


public class PipelineDump implements Pipeline<Object, Boolean> {

	protected Pipeline<?, Object>  parent = null;



	public PipelineDump() {
	}



	@SuppressWarnings("unchecked")
	@Override
	public void setParentChild(
			final Pipeline<?, ?> parent,
			final Pipeline<?, ?> child) {
		this.parent = (Pipeline<?, Object>) parent;
		if (child != null) throw new UnsupportedOperationException("Cannot set a child pipeline on a pipe exit point.");
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
		throw new UnsupportedOperationException("Cannot write to a dump pipeline.");
	}



}
