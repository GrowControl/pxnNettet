package com.poixson.nettet.transports;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.channels.SocketChannel;

import com.poixson.nettet.NettetDefines;
import com.poixson.utils.Utils;
import com.poixson.utils.exceptions.RequiredArgumentException;


public class TransportClientTCP extends TransportClient {

	protected final SocketChannel channel;
	protected final Socket        socket;

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
		if (Utils.isEmpty(remoteAddrStr)) throw RequiredArgumentException.getNew("remoteAddrStr");
		if (localPort  <= 0) throw RequiredArgumentException.getNew("localPort");
		if (remotePort <= 0) throw RequiredArgumentException.getNew("remotePort");
		this.localAddr = (
			Utils.isEmpty(localAddrStr)
			? new InetSocketAddress(localPort)
			: new InetSocketAddress(localAddrStr, localPort)
		);
		this.remoteAddr = new InetSocketAddress(remoteAddrStr, remotePort);
		this.channel = SocketChannel.open();
		this.socket  = this.channel.socket();
		this.channel.configureBlocking(false);
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
		if (remoteAddr == null) throw RequiredArgumentException.getNew("remoteAddr");
		this.localAddr  = (
			localAddr == null
			? new InetSocketAddress(NettetDefines.RandomPort())
			: localAddr
		);
		this.remoteAddr = remoteAddr;
		this.channel = SocketChannel.open();
		this.socket  = this.channel.socket();
		this.channel.configureBlocking(false);
	}



	@Override
	public void Connect() throws IOException {
		final InetSocketAddress localAddr  = this.localAddr;
		final InetSocketAddress remoteAddr = this.remoteAddr;
		if (localAddr != null) {
			this.channel.bind(localAddr);
		}
		this.channel.connect(remoteAddr);
	}



	@Override
	public void close() throws IOException {
		this.channel.close();
	}



	@Override
	public boolean isClosed() {
		return ! this.channel.isOpen();
	}
	@Override
	public boolean isConnected() {
		return this.channel.isConnected();
	}



}
