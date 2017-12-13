package com.poixson.nettet.demo;

import com.poixson.utils.Keeper;
import com.poixson.utils.NativeAutoLoader;
import com.poixson.utils.NativeAutoLoader.AutoMode;


public class Main {

	// keep things in memory
	@SuppressWarnings("unused")
	private static final Keeper keeper = Keeper.get();



	public static void main(final String[] argsArray) {
//TODO:
//		// process shell arguments
//		@SuppressWarnings("unused")
//		final ShellArgsTool argsTool = ShellArgsTool.Init(argsArray);
		// load libraries
		{
			final NativeAutoLoader loader =
					NativeAutoLoader.getNew(AutoMode.AUTO_MODE_SELF_CONTAINED)
						.setDefaults(Main.class)
						.setLocalLibPath("lib")
						.setResourcesPath("lib/linux64");
				// load unix socket library
				loader.clone()
//TODO: fix this path
					.setResourcesPath("lib/amd64-Linux-gpp/jni")
					.load("libjunixsocket-native-1.0.2.so");
		}
	}



//		final Pipeline pipeFactory = new Pipeline() {
//			@Override
//			public void doInitPipes() {
//				this.addLast(
//					new PipelineString(),
//					new PipelineJSONStream(true),
//					new PipelineDump()
//				);
//			}
//		};
//		pipeFactory.init();



}
