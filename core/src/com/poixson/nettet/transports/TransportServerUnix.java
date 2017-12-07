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

	protected final AFUNIXServerSocket  serverSocket;
//	protected final ServerSocketChannel serverChannel;
//	protected ServerSocketChannel serverChannel = null;

	protected final String listenPath;
	protected final File   listenFile;



	public TransportServerUnix(final String listenPath)
			throws IOException {
		super();
		if (Utils.isEmpty(listenPath)) throw RequiredArgumentException.getNew("listenPath");
		this.listenPath = listenPath;
		this.listenFile = new File(listenPath);
		this.serverSocket  = AFUNIXServerSocket.newInstance();
	}



	@Override
	public void Bind() throws IOException {
		final AFUNIXSocketAddress addr = new AFUNIXSocketAddress(this.listenFile);
		final int backlog = this.getBacklog();
//		final int timeout = this.getTimeout();
//		this.serverSocket.setSoTimeout(timeout);
		this.serverSocket.bind(addr, backlog);
//TODO: remove this?
//		this.serverChannel = this.serverSocket.getChannel();
//		this.serverChannel.configureBlocking(false);
	}



	@Override
	public void close() throws IOException {
		this.serverSocket.close();
	}
	@Override
	public void closeAll() {
		Utils.safeClose(this);
//TODO:
	}



	@Override
	public boolean isClosed() {
		return this.serverSocket.isClosed();
	}
	@Override
	public boolean isListening() {
//TODO: is this right?
		return this.serverSocket.isBound();
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
				this.serverSocket.setSoTimeout(timeout);
			} catch (SocketException ignore) {}
		}
		return this;
	}



}
