/*
package com.poixson.nettet;

import java.util.concurrent.atomic.AtomicReference;

import com.poixson.utils.ErrorMode;
import com.poixson.utils.NativeAutoLoader;


public class NettetLibraryLoader {

	private static final AtomicReference<NettetLibraryLoader> instance =
			new AtomicReference<NettetLibraryLoader>(null);

	protected final NativeAutoLoader autoLoader;



	public static NettetLibraryLoader get() {
		{
			final NettetLibraryLoader loader = instance.get();
			if (loader != null)
				return loader;
		}
		{
			final NettetLibraryLoader loader = new NettetLibraryLoader();
			if (!instance.compareAndSet(null, loader)) {
				return instance.get();
			}
			return loader;
		}
	}
	protected NettetLibraryLoader() {
		this.autoLoader = NativeAutoLoader.getNew()
			.addDefaultSearchPaths()
			.setLocalLibPath("lib")
			.setResourcesPath("lib/linux64")
			.enableExtract()
			.enableReplace()
			.setClassRef(NettetLibraryLoader.class)
			.setErrorMode(ErrorMode.EXCEPTION);
	}



	public NativeAutoLoader getAutoLoader() {
		return this.autoLoader.clone();
	}



	// load unix socket library
	public void loadUnixSocketLibrary() {
		this.loadUnixSocketLibrary(
			this.getAutoLoader()
		);
	}
	public void loadUnixSocketLibrary(final NativeAutoLoader loader) {
		loader
//TODO: need to fix this path
			.setResourcesPath("lib/amd64-Linux-gpp/jni")
			.setFileNameIfNull("libjunixsocket-native-1.0.2.so")
			.load();
	}



}
*/
