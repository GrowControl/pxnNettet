package com.poixson.nettet;

import java.io.IOException;

import com.poixson.utils.xCloseable;


public interface Transport extends xCloseable {


	@Override
	public void close() throws IOException;
	@Override
	public boolean isClosed();


}
