package com.poixson.nettet.pipes;

import com.poixson.nettet.Pipe;
import com.poixson.tools.Dumper;


public class Pipe_Dump implements Pipe<Object, Object> {

	protected Pipe<?, Object> parent = null;
	protected Pipe<Object, ?> child = null;

	private long totalRead  = 0;
	private long totalWrote = 0;



	public Pipe_Dump() {
	}



	@SuppressWarnings("unchecked")
	@Override
	public void setParentChild(
			final Pipe<?, ?> parent,
			final Pipe<?, ?> child) {
		this.parent = (Pipe<?, Object>) parent;
		this.child  = (Pipe<Object, ?>) child;
	}
	@Override
	public Class<Object> getEncodedType() {
		return Object.class;
	}
	@Override
	public Class<Object> getDecodedType() {
		return Object.class;
	}



	@Override
	public void readMessage(final Object obj) {
		this.totalRead++;
		Dumper.print(obj);
	}



	@Override
	public void writeMessage(final Object obj) {
		this.totalWrote++;
		Dumper.print(obj);
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
