package com.poixson.nettet.pipelines;

import com.poixson.utils.StringUtils;


public class PipelineLine extends PipelineDelim {



	public PipelineLine() {
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
