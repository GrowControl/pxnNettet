package com.poixson.nettet.transports;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.channels.SocketChannel;

import com.poixson.exceptions.RequiredArgumentException;
import com.poixson.nettet.NettetDefines;
import com.poixson.utils.Utils;


public class TransportClientTCP extends TransportClient {

	protected final SocketChannel nioChannel;

	protected final InetSocketAddress localAddr;
	protected final InetSocketAddress remoteAddr;



	public TransportClientTCP(
			final String remoteAddrStr, final int remotePort)
			throws IOException {
		this(
			null, // localAddrStr
			NettetDefines.RandomPort(),
			remoteAddrStr,
			remotePort
		);
	}
	public TransportClientTCP(
			final String localAddrStr, final int localPort,
			final String remoteAddrStr, final int remotePort)
			throws IOException {
		super();
		if (Utils.isEmpty(remoteAddrStr)) throw new RequiredArgumentException("remoteAddrStr");
		if (localPort  <= 0)              throw new RequiredArgumentException("localPort");
		if (remotePort <= 0)              throw new RequiredArgumentException("remotePort");
		this.localAddr = (
			Utils.isEmpty(localAddrStr)
			? new InetSocketAddress(localPort)
			: new InetSocketAddress(localAddrStr, localPort)
		);
		this.remoteAddr = new InetSocketAddress(remoteAddrStr, remotePort);
		this.nioChannel = SocketChannel.open();
		this.nioChannel.configureBlocking(false);
	}
	// inet
	public TransportClientTCP(
			final InetSocketAddress remoteAddr)
			throws IOException {
		this(
			null,
			remoteAddr
		);
	}
	public TransportClientTCP(
			final InetSocketAddress localAddr,
			final InetSocketAddress remoteAddr)
			throws IOException {
		super();
		if (remoteAddr == null) throw new RequiredArgumentException("remoteAddr");
		this.localAddr = (
			localAddr == null
			? new InetSocketAddress(NettetDefines.RandomPort())
			: localAddr
		);
		this.remoteAddr = remoteAddr;
		this.nioChannel = SocketChannel.open();
		this.nioChannel.configureBlocking(false);
	}



	public SocketChannel getChannel() {
		return this.nioChannel;
	}
	public Socket getSocket() {
		return this.nioChannel.socket();
	}



	@Override
	public void connect() throws IOException {
		final InetSocketAddress localAddr  = this.localAddr;
		final InetSocketAddress remoteAddr = this.remoteAddr;
		if (localAddr != null) {
			this.nioChannel.bind(localAddr);
		}
		this.nioChannel.connect(remoteAddr);
	}
	@Override
	public void close() throws IOException {
		this.nioChannel.close();
	}



	@Override
	public boolean isConnected() {
		return this.nioChannel.isConnected();
	}
	@Override
	public boolean isClosed() {
		return ! this.nioChannel.isOpen();
	}



}
