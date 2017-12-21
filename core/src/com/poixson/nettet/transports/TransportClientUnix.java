package com.poixson.nettet.transports;

import java.io.File;
import java.io.IOException;

import org.newsclub.net.unix.AFUNIXSocket;
import org.newsclub.net.unix.AFUNIXSocketAddress;

import com.poixson.utils.Utils;
import com.poixson.utils.exceptions.RequiredArgumentException;


public class TransportClientUnix extends TransportClient {

	protected final AFUNIXSocket afSocket;

	protected final String socketPath;
	protected final File   socketFile;



	public TransportClientUnix(final String socketPath)
			throws IOException {
		super();
		if (Utils.isEmpty(socketPath)) throw RequiredArgumentException.getNew("socketPath");
		this.socketPath = socketPath;
		this.socketFile = new File(socketPath);
		this.afSocket = AFUNIXSocket.newInstance();
	}



	public AFUNIXSocket getSocket() {
		return this.afSocket;
	}



	@Override
	public void connect() throws IOException {
		final AFUNIXSocketAddress addr = new AFUNIXSocketAddress(this.socketFile);
		this.afSocket.connect(addr, 1000);
	}
	@Override
	public void close() throws IOException {
		this.afSocket.close();
	}



	@Override
	public boolean isConnected() {
		return this.afSocket.isConnected();
	}
	@Override
	public boolean isClosed() {
		return this.afSocket.isClosed();
	}



}
