package com.poixson.nettet.tool;

import com.poixson.nettet.NettetLibraryLoader;
import com.poixson.nettet.tool.gui.ToolWindow;
import com.poixson.utils.ShellArgsTool;
import com.poixson.serial.SerialLibraryLoader;


public class Main {



	public static void main(final String[] argsArray) {
		// load libraries
		{
			// load unix socket library
			NettetLibraryLoader.get()
				.loadUnixSocketLibrary();
			// load serial library
			final SerialLibraryLoader loader =
				SerialLibraryLoader.get();
			loader.loadSerialLibrary();
			// load d2xx open library
			loader.loadD2xxOpenLibrary();
			// load d2xx prop library
			loader.loadD2xxPropLibrary();
		}
		// process shell arguments
		@SuppressWarnings("unused")
		final ShellArgsTool argsTool = ShellArgsTool.Init(argsArray);
		final ToolWindow toolWin = ToolWindow.getNew();
		toolWin.showFocused();
	}



}
