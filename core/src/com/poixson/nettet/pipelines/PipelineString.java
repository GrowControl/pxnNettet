package com.poixson.nettet.pipelines;

import com.poixson.nettet.Pipeline;
import com.poixson.utils.StringUtils;
import com.poixson.utils.Utils;


public class PipelineString implements Pipeline<byte[], String> {

	protected Pipeline<?, byte[]> parent = null;
	protected Pipeline<String, ?> child  = null;



	public PipelineString() {
	}



	@SuppressWarnings("unchecked")
	@Override
	public void setParentChild(
			final Pipeline<?, ?> parent,
			final Pipeline<?, ?> child) {
		this.parent = (Pipeline<?, byte[]>) parent;
		this.child  = (Pipeline<String, ?>) child;
	}
	@Override
	public Class<byte[]> getEncodedType() {
		return byte[].class;
	}
	@Override
	public Class<String> getDecodedType() {
		return String.class;
	}



	@Override
	public void readMessage(final byte[] bytes) {
		if (Utils.isEmpty(bytes))
			return;
		final String msg =
			new String(
				bytes,
				StringUtils.UTF8
			);
		this.child.readMessage(msg);
	}



	@Override
	public void writeMessage(final String msg) {
		if (Utils.isEmpty(msg))
			return;
		final byte[] bytes = msg.getBytes();
		this.parent.writeMessage(bytes);
	}



}
