package com.poixson.nettet.transports;

import java.io.IOException;

import com.poixson.abstractions.xCloseableMany;
import com.poixson.nettet.NettetDefines;


public abstract class TransportServer implements Transport, xCloseableMany {

	private volatile int backlog = -1;
	private volatile int timeout = -1;



	public TransportServer() {
	}



	public abstract void bind() throws IOException;
	@Override
	public void open() throws IOException {
		this.bind();
	}



	public abstract boolean isListening();
	public boolean isOpen() {
		return this.isListening();
	}



	public abstract int countConnected();



	public int getBacklog() {
		final int backlog = this.backlog;
		return (
			backlog < 0
			? NettetDefines.DEFAULT_BACKLOG
			: backlog
		);
	}
	public TransportServer setBacklog(final int backlog) {
		this.backlog = backlog;
		return this;
	}



	public int getTimeout() {
		final int timeout = this.timeout;
		return (
			timeout < 0
			? NettetDefines.DEFAULT_TIMEOUT
			: timeout
		);
	}
	public TransportServer setTimeout(final int timeout) {
		this.timeout = timeout;
		return this;
	}



}
