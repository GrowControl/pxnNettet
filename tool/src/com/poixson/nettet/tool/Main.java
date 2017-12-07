package com.poixson.nettet.tool;

import com.poixson.nettet.tool.gui.ToolWindow;
import com.poixson.utils.ShellArgsTool;


public class Main {



	public static void main(final String[] argsArray) {
		// process shell arguments
		@SuppressWarnings("unused")
		final ShellArgsTool argsTool = ShellArgsTool.Init(argsArray);
		// load serial library
//TODO:
//		LibraryLoader.get()
//			.load();
		final ToolWindow toolWin = ToolWindow.getNew();
		toolWin.showFocused();
	}



}
