package com.poixson.nettet;

import static com.poixson.nettet.NettetDefines.RANDOM_PORT_HISTORY;
import static com.poixson.nettet.NettetDefines.RANDOM_PORT_MAX;
import static com.poixson.nettet.NettetDefines.RANDOM_PORT_MIN;

import com.poixson.utils.HistoryRND;
import com.poixson.utils.Keeper;


public final class NettetUtils {

	private NettetUtils() {}
	static {
		Keeper.add(new NettetUtils());
	}



	private static final HistoryRND rnd =
		new HistoryRND(
			RANDOM_PORT_MIN,
			RANDOM_PORT_MAX,
			RANDOM_PORT_HISTORY
		);

	public static int RandomPort() {
		return rnd.RND();
	}



}
