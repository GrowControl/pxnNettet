package com.poixson.nettet.demo;

import com.poixson.nettet.NettetLibraryLoader;
import com.poixson.utils.Keeper;


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
			// load unix socket library
			NettetLibraryLoader.get()
				.loadUnixSocketLibrary();
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
