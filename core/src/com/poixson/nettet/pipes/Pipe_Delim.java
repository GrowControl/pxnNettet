package com.poixson.nettet.pipes;

import com.poixson.nettet.Pipe;
import com.poixson.utils.Utils;
import com.poixson.utils.xString;


public class Pipe_Delim implements Pipe<String, String> {

	protected Pipe<?, String> parent = null;
	protected Pipe<String, ?> child  = null;

	protected final xString buffer = xString.get();



	public Pipe_Delim(final String delim) {
		this.buffer.delim(delim);
	}



	@SuppressWarnings("unchecked")
	@Override
	public void setParentChild(
			final Pipe<?, ?> parent,
			final Pipe<?, ?> child) {
		this.parent = (Pipe<?, String>) parent;
		this.child  = (Pipe<String, ?>) child;
	}
	@Override
	public Class<String> getEncodedType() {
		return String.class;
	}
	@Override
	public Class<String> getDecodedType() {
		return String.class;
	}



	@Override
	public void readMessage(final String msg) {
		if (Utils.isEmpty(msg))
			return;
		this.buffer.append(
			this.prepareReadMessage(msg)
		);
		while (this.buffer.hasNext()) {
			final String line = this.buffer.getNext();
			if (Utils.notEmpty(line)) {
				this.child.readMessage(line);
			}
		}
	}



	@Override
	public void writeMessage(final String msg) {
		if (Utils.isEmpty(msg))
			return;
		this.parent.writeMessage(
			this.prepareWriteMessage(msg)
			+this.buffer.delim()
		);
	}



	protected String prepareReadMessage(final String msg) {
		return msg;
	}
	protected String prepareWriteMessage(final String msg) {
		return msg;
	}



}
