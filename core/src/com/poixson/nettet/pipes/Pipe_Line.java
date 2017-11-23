package com.poixson.nettet.pipes;

import com.poixson.utils.StringUtils;


public class Pipe_Line extends Pipe_Delim {



	public Pipe_Line() {
		super("\n");
	}



	@Override
	protected String prepareReadMessage(final String msg) {
		if (msg == null)
			return null;
		return msg.replace('\r', '\n');
	}



	@Override
	protected String prepareWriteMessage(final String msg) {
		if (msg == null)
			return null;
		return StringUtils.Trim(msg, "\r", "\n");
	}



}
