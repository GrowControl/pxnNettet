package com.poixson.nettet.demo;

import com.poixson.utils.ErrorMode;
import com.poixson.utils.Keeper;
import com.poixson.utils.NativeAutoLoader;
import com.poixson.utils.ShellArgsTool;


public class Main {

	@SuppressWarnings("unused")
	private static final Keeper keeper = Keeper.get();



	public static void main(final String[] argsArray) {
		// process shell arguments
		@SuppressWarnings("unused")
		final ShellArgsTool argsTool = ShellArgsTool.Init(argsArray);
		// load unix socket library
		{
			final NativeAutoLoader loader =
				NativeAutoLoader.getNew()
					.addDefaultSearchPaths()
					.enableExtract()
					.enableReplace()
					.setClassRef(Main.class)
					.setErrorMode(ErrorMode.EXCEPTION);
			loader.setFileName("libjunixsocket-native-1.0.2")
				.load();
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
		};
//		pipeFactory.init();
//TODO:



//	}



}
