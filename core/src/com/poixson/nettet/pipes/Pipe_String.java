package com.poixson.nettet.pipes;

import com.poixson.nettet.Pipe;
import com.poixson.utils.StringUtils;
import com.poixson.utils.Utils;


public class Pipe_String implements Pipe<byte[], String> {

	protected Pipe<?, byte[]> parent = null;
	protected Pipe<String, ?> child  = null;

	private long totalRead  = 0;
	private long totalWrote = 0;



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
				StringUtils.CHARSET_UTF8
			);
		this.totalRead += bytes.length;
		this.child.readMessage(msg);
	}



	@Override
	public void writeMessage(final String msg) {
		if (Utils.isEmpty(msg))
			return;
		final byte[] bytes = msg.getBytes();
		this.totalWrote += bytes.length;
		this.parent.writeMessage(bytes);
	}



	@Override
	public boolean canRead() {
		return true;
	}
	@Override
	public boolean canWrite() {
		return true;
	}



	// total messages
	@Override
	public long getTotalRead() {
		return this.totalRead;
	}
	@Override
	public long getTotalWrote() {
		return this.totalWrote;
	}



}
