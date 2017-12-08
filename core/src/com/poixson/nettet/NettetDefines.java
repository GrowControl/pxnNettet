package com.poixson.nettet;

import com.poixson.utils.HistoryRND;


public final class NettetDefines {
	private NettetDefines() {}



	public static final int MAX_PORT_NUMBER = 65534;
	public static final int MAX_BACKLOG     = 100;
	public static final int DEFAULT_BACKLOG = 10;
	public static final int DEFAULT_TIMEOUT = 5000;

	public static final int RANDOM_PORT_MIN = 49152;
	public static final int RANDOM_PORT_MAX = MAX_PORT_NUMBER;
	public static final int RANDOM_PORT_HISTORY = 10;



	private static final HistoryRND rnd =
		new HistoryRND(
			RANDOM_PORT_MIN,
			RANDOM_PORT_MAX,
			RANDOM_PORT_HISTORY
		);

	public static int RandomPort() {
		return rnd.rnd();
	}



}
