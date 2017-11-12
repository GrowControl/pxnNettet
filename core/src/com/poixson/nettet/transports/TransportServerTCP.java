package com.poixson.nettet.transports;

import java.io.IOException;
import com.poixson.utils.Utils;


public class TransportServerTCP implements TransportServer {



	public TransportServerTCP() throws IOException {

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



	public boolean isListening() {
		return this.serverChannel.isOpen();
	}
	public boolean isOpen() {
		return this.isListening();
	}
	public boolean isClosed() {
		return ! this.isListening();
	}



}
