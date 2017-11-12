package com.poixson.nettet.transports;

import java.io.IOException;

import com.poixson.nettet.Transport;
import com.poixson.utils.xCloseableMany;


public interface TransportServer extends Transport, xCloseableMany {


	public void Bind() throws IOException;

	@Override
	public void close() throws IOException;
	@Override
	public boolean isClosed();
	@Override
	public void CloseAll();


}
