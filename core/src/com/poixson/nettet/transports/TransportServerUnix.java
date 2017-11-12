package com.poixson.nettet.transports;

import java.io.IOException;

import com.poixson.utils.Utils;


public class TransportServerUnix implements TransportServer {



	public TransportServerUnix() throws IOException {
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
