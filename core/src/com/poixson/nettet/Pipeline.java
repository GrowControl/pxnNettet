package com.poixson.nettet;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;

import com.poixson.utils.Utils;


public abstract class Pipeline implements Pipe<byte[], Boolean> {

	protected final LinkedList<Pipe<?, ?>> pipes =
			new LinkedList<Pipe<?, ?>>();
	private boolean inited = false;



	public Pipeline(final Pipe<?, ?>...pipes) {
		this();
		this.addLast(pipes);
	}
	public Pipeline() {
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
			Pipe<?, ?> lastPipe    = null;
			Pipe<?, ?> currentPipe = null;
			for (final Pipe<?, ?> nextPipe : this.pipes) {
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



	public Pipeline addFirst(final Pipe<?, ?>...pipes) {
		if (Utils.isEmpty(pipes))
			return this;
		final LinkedList<Pipe<?, ?>> list =
			new LinkedList<Pipe<?, ?>>(
				Arrays.asList(pipes)
			);
		final Iterator<Pipe<?, ?>> it = list.descendingIterator();
		while (it.hasNext()) {
			final Pipe<?, ?> pipe = it.next();
			if (pipe == null) continue;
			this.pipes.addFirst(pipe);
		}
		return this;
	}
	public Pipeline addLast(final Pipe<?, ?>...pipes) {
		if (Utils.isEmpty(pipes))
			return this;
		for (final Pipe<?, ?> pipe : pipes) {
			if (pipe == null) continue;
			this.pipes.addLast(pipe);
		}
		return this;
	}



	public static void validate(final Pipe<?, ?> pipeA, final Pipe<?, ?> pipeB) {
//TODO:
	}



	@Override
	public void setParentChild(
			final Pipe<?, ?> parent,
			final Pipe<?, ?> child) {
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
	public Pipe<byte[], ?> getEntryPipe() {
		return (Pipe<byte[], ?>) this.pipes.getFirst();
	}
	@SuppressWarnings("unchecked")
	public Pipe<?, Boolean> getExitPipe() {
		return (Pipe<?, Boolean>) this.pipes.getLast();
	}



	@Override
	public void readMessage (final byte[] bytes) {
		this.init();
		final Pipe<byte[], ?> entryPipe =
				this.getEntryPipe();
		if (entryPipe == null) throw new NullPointerException("Failed to get pipe entry point!");
		entryPipe.readMessage(bytes);
	}
	@Override
	public void writeMessage(final Boolean success) {
		this.init();
		final Pipe<?, Boolean> exitPipe =
				this.getExitPipe();
		if (exitPipe == null) throw new NullPointerException("Failed to get pipe exit point!");
		exitPipe.writeMessage(success);
	}



}
