package com.poixson.nettet.transports;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.SocketException;
import java.nio.channels.DatagramChannel;

import com.poixson.utils.Utils;
import com.poixson.utils.exceptions.RequiredArgumentException;


public class TransportServerUDP extends TransportServer {

	protected final DatagramChannel serverChannel;
	protected final DatagramSocket  serverSocket;

	protected final SocketAddress listenAddress;



	public TransportServerUDP(final String listenAddressStr,
			final int listenPort) throws IOException {
		this(
			new InetSocketAddress(listenAddressStr, listenPort)
		);
	}
	public TransportServerUDP(final SocketAddress listenAddress)
			throws IOException {
		super();
		if (listenAddress == null) throw new RequiredArgumentException("listenAddress");
		this.listenAddress = listenAddress;
		this.serverChannel = DatagramChannel.open();
		this.serverSocket  = this.serverChannel.socket();
		this.serverChannel.configureBlocking(false);
	}



	@Override
	public void Bind() throws IOException {
		final DatagramSocket socket = this.serverChannel.socket();
		final SocketAddress listenAddress = this.listenAddress;
		final int timeout = this.getTimeout();
		socket.setSoTimeout(timeout);
		socket.bind(listenAddress);
	}



	@Override
	public void close() throws IOException {
		this.serverChannel.close();
	}
	@Override
	public void closeAll() {
		Utils.safeClose(this);
//TODO:
	}



	@Override
	public boolean isClosed() {
		return ! this.serverChannel.isOpen();
	}
	@Override
	public boolean isListening() {
		return this.serverChannel.isOpen();
	}
	@Override
	public int countConnected() {
//TODO:
return -1;
	}



	@Override
	public int getBacklog() {
		throw new UnsupportedOperationException();
	}
	@Override
	public TransportServerUDP setBacklog(final int backlog) {
		throw new UnsupportedOperationException();
	}
	@Override
	public TransportServerUDP setTimeout(final int timeout) {
		if (super.setTimeout(timeout) == null)
			return null;
		if (this.isListening()) {
			try {
				this.serverChannel.socket()
					.setSoTimeout(timeout);
			} catch (SocketException ignore) {}
		}
		return this;
	}



}
