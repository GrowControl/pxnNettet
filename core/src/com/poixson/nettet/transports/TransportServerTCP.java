package com.poixson.nettet.transports;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.nio.channels.ServerSocketChannel;

import com.poixson.utils.Utils;
import com.poixson.utils.exceptions.RequiredArgumentException;


public class TransportServerTCP extends TransportServer {

	protected final ServerSocketChannel nioServerChannel;

	protected final SocketAddress listenAddress;



	public TransportServerTCP(final String listenAddressStr,
			final int listenPort) throws IOException {
		this(
			new InetSocketAddress(listenAddressStr, listenPort)
		);
	}
	public TransportServerTCP(final SocketAddress listenAddress)
			throws IOException {
		super();
		if (listenAddress == null) throw new RequiredArgumentException("listenAddress");
		this.listenAddress = listenAddress;
		this.nioServerChannel = ServerSocketChannel.open();
		this.nioServerChannel.configureBlocking(false);
	}



	public ServerSocketChannel getChannel() {
		return this.nioServerChannel;
	}
	public ServerSocket getSocket() {
		return this.nioServerChannel.socket();
	}



	@Override
	public void bind() throws IOException {
		final SocketAddress listenAddress = this.listenAddress;
		final int backlog = this.getBacklog();
		final int timeout = this.getTimeout();
		final ServerSocket socket = this.getSocket();
		socket.setSoTimeout(timeout);
		socket.bind(listenAddress, backlog);
	}



	@Override
	public void close() throws IOException {
		this.nioServerChannel.close();
	}
	@Override
	public void closeAll() {
		Utils.safeClose(this);
//TODO:
	}



	@Override
	public boolean isListening() {
		return this.nioServerChannel.isOpen();
	}
	@Override
	public boolean isClosed() {
		return ! this.nioServerChannel.isOpen();
	}



	@Override
	public int countConnected() {
//TODO:
return -1;
	}



	@Override
	public TransportServerTCP setBacklog(final int backlog) {
		if (super.setBacklog(backlog) == null)
			return null;
		return this;
	}
	@Override
	public TransportServerTCP setTimeout(final int timeout) {
		if (super.setTimeout(timeout) == null)
			return null;
		if (this.isListening()) {
			try {
				this.getSocket()
					.setSoTimeout(timeout);
			} catch (SocketException ignore) {}
		}
		return this;
	}



}
