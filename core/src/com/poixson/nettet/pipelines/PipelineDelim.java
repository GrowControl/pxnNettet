package com.poixson.nettet.pipelines;

import com.poixson.nettet.Pipeline;
import com.poixson.utils.Utils;
import com.poixson.utils.xString;


public class PipelineDelim implements Pipeline<String, String> {

	protected Pipeline<?, String> parent = null;
	protected Pipeline<String, ?> child  = null;

	protected final xString buffer = xString.get();



	public PipelineDelim(final String delim) {
		this.buffer.delim(delim);
	}



	@SuppressWarnings("unchecked")
	@Override
	public void setParentChild(
			final Pipeline<?, ?> parent,
			final Pipeline<?, ?> child) {
		this.parent = (Pipeline<?, String>) parent;
		this.child  = (Pipeline<String, ?>) child;
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
