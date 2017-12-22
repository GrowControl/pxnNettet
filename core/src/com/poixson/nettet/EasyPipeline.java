package com.poixson.nettet;

import com.poixson.nettet.pipes.Pipe_JSONStream;
import com.poixson.nettet.pipes.Pipe_String;


public class EasyPipeline extends Pipeline {
	private static final long serialVersionUID = 1L;

	public enum PipelineType {
		RAW,
		HTTP,
		HTTPS,
		JSON,
		MQTT
	}
	private volatile PipelineType type = null;

	private volatile boolean locked = false;



	public EasyPipeline() {
	}
	public EasyPipeline(final PipelineType type) {
		this();
		this.setType(type);
	}
	public EasyPipeline(final EasyPipeline pipeline) {
		this(pipeline.getType());
	}



	@Override
	public EasyPipeline clone() {
		return new EasyPipeline(this);
	}



	public EasyPipeline setLocked() {
		this.locked = true;
		return this;
	}



	@Override
	public void setup() {
		final PipelineType type = this.getType();
		switch (type) {
		case RAW:
			this.addLast(
				new Pipe_String()
			);
		case HTTP:
//TODO:
			throw new UnsupportedOperationException("Unfinished protocol pipeline!");
		case HTTPS:
//TODO:
			throw new UnsupportedOperationException("Unfinished protocol pipeline!");
		case JSON:
			this.addLast(
				new Pipe_String(),
				new Pipe_JSONStream()
			);
		case MQTT:
			throw new UnsupportedOperationException("Unfinished protocol pipeline!");
//TODO:
//			this.addLast(
//				new Pipe_MQTT()
//			);
		default:
			throw new RuntimeException("Unknown pipeline type: "+type.toString());
		}
	}



	public PipelineType getType() {
		return this.type;
	}
	public EasyPipeline setType(final PipelineType type) {
		if (this.locked) throw new RuntimeException("Pipeline is already locked");
		this.type = type;
		return this;
	}



}
