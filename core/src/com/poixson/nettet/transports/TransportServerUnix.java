package com.poixson.nettet.transports;

import java.io.File;
import java.io.IOException;
import java.net.SocketException;

import org.newsclub.net.unix.AFUNIXServerSocket;
import org.newsclub.net.unix.AFUNIXSocketAddress;

import com.poixson.utils.Utils;
import com.poixson.utils.exceptions.RequiredArgumentException;


// https://github.com/fiken/junixsocket
public class TransportServerUnix extends TransportServer {

	protected final AFUNIXServerSocket afServerSocket;

	protected final String listenPath;
	protected final File   listenFile;



	public TransportServerUnix(final String listenPath)
			throws IOException {
		super();
		if (Utils.isEmpty(listenPath)) throw new RequiredArgumentException("listenPath");
		this.listenPath = listenPath;
		this.listenFile = new File(listenPath);
		this.afServerSocket = AFUNIXServerSocket.newInstance();
	}



	public AFUNIXServerSocket getSocket() {
		return this.afServerSocket;
	}



	@Override
	public void bind() throws IOException {
		final AFUNIXSocketAddress addr = new AFUNIXSocketAddress(this.listenFile);
		final int backlog = this.getBacklog();
		final int timeout = this.getTimeout();
		this.afServerSocket.setSoTimeout(timeout);
		this.afServerSocket.bind(addr, backlog);
	}



	@Override
	public void close() throws IOException {
		this.afServerSocket.close();
	}
	@Override
	public void closeAll() {
		Utils.safeClose(this);
//TODO:
	}



	@Override
	public boolean isListening() {
//TODO: is this right?
		return this.afServerSocket.isBound();
	}
	@Override
	public boolean isClosed() {
		return this.afServerSocket.isClosed();
	}



	@Override
	public int countConnected() {
//TODO:
return -1;
	}



	@Override
	public TransportServerUnix setBacklog(final int backlog) {
		if (super.setBacklog(backlog) == null)
			return null;
		return this;
	}
	@Override
	public TransportServerUnix setTimeout(final int timeout) {
		if (super.setTimeout(timeout) == null)
			return null;
		if (this.isListening()) {
			try {
				this.afServerSocket.setSoTimeout(timeout);
			} catch (SocketException ignore) {}
		}
		return this;
	}



}
