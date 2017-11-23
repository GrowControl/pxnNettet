package com.poixson.nettet.pipes;

import com.poixson.nettet.Pipe;
import com.poixson.utils.StringUtils;
import com.poixson.utils.Utils;


public class Pipe_String implements Pipe<byte[], String> {

	protected Pipe<?, byte[]> parent = null;
	protected Pipe<String, ?> child  = null;



	public Pipe_String() {
	}



	@SuppressWarnings("unchecked")
	@Override
	public void setParentChild(
			final Pipe<?, ?> parent,
			final Pipe<?, ?> child) {
		this.parent = (Pipe<?, byte[]>) parent;
		this.child  = (Pipe<String, ?>) child;
	}
	@Override
	public Class<byte[]> getEncodedType() {
		return byte[].class;
	}
	@Override
	public Class<String> getDecodedType() {
		return String.class;
	}



	@Override
	public void readMessage(final byte[] bytes) {
		if (Utils.isEmpty(bytes))
			return;
		final String msg =
			new String(
				bytes,
				StringUtils.UTF8
			);
		this.child.readMessage(msg);
	}



	@Override
	public void writeMessage(final String msg) {
		if (Utils.isEmpty(msg))
			return;
		final byte[] bytes = msg.getBytes();
		this.parent.writeMessage(bytes);
	}



}
