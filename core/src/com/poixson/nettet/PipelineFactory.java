package com.poixson.nettet;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;

import com.poixson.utils.Utils;


public abstract class PipelineFactory implements Pipeline<byte[], Boolean> {

	protected final LinkedList<Pipeline<?, ?>> pipes =
			new LinkedList<Pipeline<?, ?>>();
	private boolean inited = false;



	public PipelineFactory(final Pipeline<?, ?>...pipes) {
		this();
		this.addLast(pipes);
	}
	public PipelineFactory() {
	}



	public abstract void doInitPipes();



	public void init() {
		if (this.inited) return;
		synchronized(this.pipes) {
			if (this.inited) return;
			this.inited = true;
			this.doInitPipes();
			if (this.pipes.size() == 0) {
				throw new RuntimeException("No pipes found in pipeline!");
			}
			Pipeline<?, ?> lastPipe    = null;
			Pipeline<?, ?> currentPipe = null;
			for (final Pipeline<?, ?> nextPipe : this.pipes) {
				if (currentPipe != null) {
					currentPipe.setParentChild(
						lastPipe,
						nextPipe
					);
				}
				lastPipe = currentPipe;
				currentPipe = nextPipe;
			}
			currentPipe.setParentChild(
				lastPipe,
				null
			);
		} 
	}



	public PipelineFactory addFirst(final Pipeline<?, ?>...pipes) {
		if (Utils.isEmpty(pipes))
			return this;
		final LinkedList<Pipeline<?, ?>> list =
			new LinkedList<Pipeline<?, ?>>(
				Arrays.asList(pipes)
			);
		final Iterator<Pipeline<?, ?>> it = list.descendingIterator();
		while (it.hasNext()) {
			final Pipeline<?, ?> pipe = it.next();
			if (pipe == null) continue;
			this.pipes.addFirst(pipe);
		}
		return this;
	}
	public PipelineFactory addLast(final Pipeline<?, ?>...pipes) {
		if (Utils.isEmpty(pipes))
			return this;
		for (final Pipeline<?, ?> pipe : pipes) {
			if (pipe == null) continue;
			this.pipes.addLast(pipe);
		}
		return this;
	}



	public static void validate(final Pipeline<?, ?> pipeA, final Pipeline<?, ?> pipeB) {
//TODO:
	}



	@Override
	public void setParentChild(
			final Pipeline<?, ?> parent,
			final Pipeline<?, ?> child) {
		throw new UnsupportedOperationException();
	}
	@Override
	public Class<? extends byte[]> getEncodedType() {
		return byte[].class;
	}
	@Override
	public Class<? extends Boolean> getDecodedType() {
		return Boolean.class;
	}



	@SuppressWarnings("unchecked")
	public Pipeline<byte[], ?> getEntryPipe() {
		return (Pipeline<byte[], ?>) this.pipes.getFirst();
	}
	@SuppressWarnings("unchecked")
	public Pipeline<?, Boolean> getExitPipe() {
		return (Pipeline<?, Boolean>) this.pipes.getLast();
	}



	@Override
	public void readMessage (final byte[] bytes) {
		this.init();
		final Pipeline<byte[], ?> entryPipe =
				this.getEntryPipe();
		if (entryPipe == null) throw new NullPointerException("Failed to get pipeline entry!");
		entryPipe.readMessage(bytes);
	}
	@Override
	public void writeMessage(final Boolean success) {
		this.init();
		final Pipeline<?, Boolean> exitPipe =
				this.getExitPipe();
		if (exitPipe == null) throw new NullPointerException("Failed to get pipeline exit!");
		exitPipe.writeMessage(success);
	}



}
