package com.poixson.nettet.transports;

import java.io.IOException;

import com.poixson.utils.xCloseable;


public abstract class TransportClient implements Transport, xCloseable {



	public abstract void Connect() throws IOException;

	@Override
	public abstract void close() throws IOException;

	@Override
	public abstract boolean isClosed();
	public abstract boolean isConnected();



}
