package com.poixson.nettet.pipelines;

import com.poixson.nettet.Pipe;
import com.poixson.utils.Utils;


public class PipelineJSONStream implements Pipe<String, Object> {

	protected Pipe<?, String> parent = null;
	protected Pipe<Object, ?> child  = null;

	protected static final int MB = 1024 * 1024; // 1MB

	protected enum STATE {
		CORRUPTED,
		DECODING_NORMAL,
		DECODING_ARRAY_STREAM
	};

	protected String buffer = "";

	protected final int maxObjectLength;
	protected final boolean streamArrayElements;

	protected STATE   state        = null;
	protected boolean insideString = false;
	protected int     openBraces   = 0;



	public PipelineJSONStream() {
		this(MB);
	}
	public PipelineJSONStream(final int maxObjectLength) {
		this(maxObjectLength, false);
	}
	public PipelineJSONStream(final boolean streamArrayElements) {
		this(MB, streamArrayElements);
	}
	/**
	 * @param maxObjectLength Maximum number of bytes a JSON object or array may use (including braces and everything).
	 *     Objects exceeding this length are dropped and an exception is thrown.
	 * @param streamArrayElements If set to true and the top level JSON object is an array, each of its entries will be
	 *     passed through the pipeline individually (and immediately after it's fully received,
	 *     allowing for arrays with infinite elements).
	 */
	public PipelineJSONStream(final int maxObjectLength, final boolean streamArrayElements) {
		if (maxObjectLength < 1)
			throw new IllegalArgumentException("Invalid maxObjectLength value");
		this.maxObjectLength = maxObjectLength;
		this.streamArrayElements = streamArrayElements;
	}



	@SuppressWarnings("unchecked")
	@Override
	public void setParentChild(
			final Pipe<?, ?> parent,
			final Pipe<?, ?> child) {
		this.parent = (Pipe<?, String>) parent;
		this.child  = (Pipe<Object, ?>) child;
	}
	@Override
	public Class<String> getEncodedType() {
		return String.class;
	}
	@Override
	public Class<Object> getDecodedType() {
		return Object.class;
	}



	// ------------------------------------------------------------------------------- //
	// read/decode



	@Override
	public void readMessage(final String msg) {
		if (Utils.isEmpty(msg))
			return;
		if (this.state == STATE.CORRUPTED)
			return;
		if (this.buffer.length() + msg.length() > this.maxObjectLength) {
			throw new RuntimeException(
				(new StringBuilder())
				.append("Object length exceeds ")
				.append(this.maxObjectLength)
				.append(" max length.")
				.toString()
			);
		}
		int indexNew = this.buffer.length();
		this.buffer += msg;
		boolean more = true;
		//outerloop:
		while (more) {
			more = false;
			char lastC = ( indexNew == 0 ? 0 : this.buffer.charAt(indexNew - 1) );
			final int bufSize = this.buffer.length();
			innerloop:
			for (int index=indexNew; index<=bufSize-1; index++) {
				final char c = this.buffer.charAt(index);
				// within normal decoding
				if (this.state == STATE.DECODING_NORMAL) {
					this.doDecodeChar(c, lastC);
					// all braces/brackets have been closed, assume json is finished
					if (this.openBraces == 0) {
						String data = this.buffer.substring(0, index + 1);
						this.buffer = buffer.substring(index + 1);
						final Object json = DecodeJSON(data);
						this.child.readMessage(json);
						data = "";
						// reset
						this.state        = null;
						this.insideString = false;
						this.openBraces   = 0;
						more = true;
						break innerloop;
					}
				} else
				// within array stream
				if (this.state == STATE.DECODING_ARRAY_STREAM) {
					this.doDecodeChar(c, lastC);
					// finished array/entry
					if ( ! this.insideString) {
						if ( (this.openBraces == 1 && c == ',')
						||   (this.openBraces == 0 && c == ']') ) {
							String data = this.buffer.substring(0, index);
							this.buffer = buffer.substring(index + 1);
							final Object json = DecodeJSON(data);
							this.child.readMessage(json);
							data = "";
							// reset
							if (c == ']') {
								this.state        = null;
								this.insideString = false;
								this.openBraces   = 0;
							}
							more = true;
							break innerloop;
						}
					}
				} else
				if (c == '{' || c == '[') {
					this.openBraces = 1;
					if (c == '[' && this.streamArrayElements) {
						this.state = STATE.DECODING_ARRAY_STREAM;
						this.buffer = this.buffer.substring(1);
					} else {
						this.state = STATE.DECODING_NORMAL;
					}
				} else
				if (Character.isWhitespace(c)) {
				} else
				if (c == ',' && this.streamArrayElements) {
					this.buffer = this.buffer.substring(1);
				} else {
					this.state = STATE.CORRUPTED;
					throw new RuntimeException(
						(new StringBuilder())
						.append("Corrupted JSON received: ").append(c)
						.append(" (").append(Integer.valueOf(c)).append(')')
						.toString()
					);
				} // /if state
				lastC = c;
			} // /for
			indexNew = 0;
		} // /while
	}



	protected void doDecodeChar(final char c, final char lastC) {
		if ( ! this.insideString) {
			if (c == '{' || c == '[') {
				this.openBraces++;
				return;
			} else
			if (c == '}' || c == ']') {
				this.openBraces--;
				return;
			}
		}
		if (c == '"') {
			if (this.insideString) {
				if (lastC != '\\') {
					this.insideString = false;
				}
			} else {
				this.insideString = true;
			}
		}
	}



	public static Object DecodeJSON(final String data) {
//TODO:
return data;
	}



	// ------------------------------------------------------------------------------- //
	// write/encode



	@Override
	public void writeMessage(final Object msg) {
//TODO:
	}



	public static String EncodeJSON(final Object json) {
//TODO:
return json.toString();
	}



}
