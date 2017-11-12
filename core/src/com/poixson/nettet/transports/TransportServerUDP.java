package com.poixson.nettet.transports;

import java.io.IOException;

import com.poixson.utils.Utils;


public class TransportServerUDP implements TransportServer {



	public TransportServerUDP() throws IOException {
	}



	@Override
	public void Bind() throws IOException {
	}



	@Override
	public void close() throws IOException {
	}
	@Override
	public void CloseAll() {
		Utils.safeClose(this);
//TODO:
	}



	@Override
	public boolean isClosed() {
return false;
	}



}
