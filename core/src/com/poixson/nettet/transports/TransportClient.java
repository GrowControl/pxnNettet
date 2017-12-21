package com.poixson.nettet.transports;

import java.io.IOException;


public abstract class TransportClient implements Transport {



	public TransportClient() {
	}



	public abstract void connect() throws IOException;
	@Override
	public void open() throws IOException {
		this.connect();
	}



	public abstract boolean isConnected();
	public boolean isOpen() {
		return this.isConnected();
	}



}
