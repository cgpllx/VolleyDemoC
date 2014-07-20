package com.example.volleydemoc.cache;

import com.example.volleydemoc.cache.memory.LruMemoryCache;

public class Simplecache extends LruMemoryCache{

	public Simplecache(int maxSize) {
		super(maxSize);
	}

}
