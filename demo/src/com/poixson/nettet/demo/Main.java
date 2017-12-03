package com.poixson.nettet.demo;

import com.poixson.utils.Keeper;
import com.poixson.utils.ShellArgsTool;


public class Main {

	@SuppressWarnings("unused")
	private static final Keeper keeper = Keeper.get();



	public static void main(final String[] argsArray) {
		// process shell arguments
		final ShellArgsTool argsTool = ShellArgsTool.Init(argsArray);

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
