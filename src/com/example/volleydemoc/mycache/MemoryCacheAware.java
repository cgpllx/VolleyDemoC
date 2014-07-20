package com.example.volleydemoc.mycache;

import java.util.Collection;

import com.android.volley.toolbox.ImageLoader.ImageCache;

public interface MemoryCacheAware extends ImageCache {

	/** Removes item by key */
	void remove(String key);

	/** Returns all keys of cache */
	Collection<String> keys();

	/** Remove all items from cache */
	void clear();
}
