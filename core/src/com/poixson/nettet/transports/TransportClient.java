package com.poixson.nettet.transports;

import java.io.IOException;


public abstract class TransportClient implements Transport {



	public TransportClient() {
	}



	public void connect() throws IOException {
		this.open();
	}



	public abstract boolean isConnected();
	public boolean isOpen() {
		return this.isConnected();
	}



}
