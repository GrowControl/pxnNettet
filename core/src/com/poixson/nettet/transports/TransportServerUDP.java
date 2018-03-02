package com.poixson.nettet.transports;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.net.SocketException;
import java.nio.channels.DatagramChannel;

import com.poixson.exceptions.RequiredArgumentException;
import com.poixson.utils.Utils;


public class TransportServerUDP extends TransportServer {

	protected final DatagramChannel nioServerChannel;

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
		this.nioServerChannel = DatagramChannel.open();
		this.nioServerChannel.configureBlocking(false);
	}



	public DatagramChannel getChannel() {
		return this.nioServerChannel;
	}
	public DatagramSocket getSocket() {
		return this.nioServerChannel.socket();
	}



	@Override
	public void bind() throws IOException {
		final SocketAddress listenAddress = this.listenAddress;
		final int timeout = this.getTimeout();
		final DatagramSocket socket = this.getSocket();
		socket.setSoTimeout(timeout);
		socket.bind(listenAddress);
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
				this.nioServerChannel.socket()
					.setSoTimeout(timeout);
			} catch (SocketException ignore) {}
		}
		return this;
	}



}
