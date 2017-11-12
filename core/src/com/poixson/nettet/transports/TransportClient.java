package com.poixson.nettet.transports;

import java.io.IOException;

import com.poixson.nettet.Transport;
import com.poixson.utils.xCloseable;


public interface TransportClient extends Transport, xCloseable {


	public void Connect() throws IOException;

	@Override
	public void close();
	@Override
	public boolean isClosed();


}
