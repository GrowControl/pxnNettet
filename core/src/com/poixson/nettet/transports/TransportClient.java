package com.poixson.nettet.transports;

import java.io.IOException;

import com.poixson.utils.xCloseable;


public abstract class TransportClient implements Transport, xCloseable {



	public TransportClient() {
	}



	public abstract void connect() throws IOException;

	@Override
	public abstract void close() throws IOException;

	@Override
	public abstract boolean isClosed();
	public abstract boolean isConnected();



}
