package com.poixson.nettet;

import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;

import com.poixson.utils.Utils;


public abstract class Pipeline
extends LinkedList<Pipe<?, ?>>
implements Pipe<byte[], Boolean> {
	private static final long serialVersionUID = 1L;

	private boolean plumbed = false;
	private final Object plumbedLock = new Object();



	public Pipeline(final Pipe<?, ?>...pipes) {
		this();
		this.addLast(pipes);
	}
	public Pipeline() {
	}



	public abstract void setup();



	public void init() {
		if (this.plumbed)
			return;
		synchronized(this.plumbedLock) {
			if (this.plumbed)
				return;
			this.plumbed = true;
			this.setup();
			if (this.size() == 0) {
				throw new RuntimeException("No pipes found in pipeline!");
			}
			Pipe<?, ?> lastPipe    = null;
			Pipe<?, ?> currentPipe = null;
			final Iterator<Pipe<?, ?>> it = this.iterator();
			while (it.hasNext()) {
				final Pipe<?, ?> nextPipe = it.next();
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
			this.addFirst(pipe);
		}
		return this;
	}
	public Pipeline addLast(final Pipe<?, ?>...pipes) {
		if (Utils.isEmpty(pipes))
			return this;
		for (final Pipe<?, ?> pipe : pipes) {
			if (pipe == null) continue;
			this.addLast(pipe);
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
		return (Pipe<byte[], ?>) this.getFirst();
	}
	@SuppressWarnings("unchecked")
	public Pipe<?, Boolean> getExitPipe() {
		return (Pipe<?, Boolean>) this.getLast();
	}



	@Override
	public void readMessage(final byte[] bytes) {
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
