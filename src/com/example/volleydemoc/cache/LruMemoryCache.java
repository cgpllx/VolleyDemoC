package com.example.volleydemoc.cache;

import java.util.Collection;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;

import android.graphics.Bitmap;

/**
 * A cache that holds strong references to a limited number of Bitmaps. Each time a Bitmap is accessed, it is moved to
 * the head of a queue. When a Bitmap is added to a full cache, the Bitmap at the end of that queue is evicted and may
 * become eligible for garbage collection.<br />
 * <br />
 * <b>NOTE:</b> This cache uses only strong references for stored Bitmaps.
 *
 * @author Sergey Tarasevich (nostra13[at]gmail[dot]com)
 * @since 1.8.1
 */
public class LruMemoryCache implements MemoryCacheAware {

	private final LinkedHashMap<String, Bitmap> map;

	private final int maxSize;
	/** Size of this cache in bytes */
	private int size;

	/** @param maxSize Maximum sum of the sizes of the Bitmaps in this cache */
	public LruMemoryCache(int maxSize) {
		if (maxSize <= 0) {
			throw new IllegalArgumentException("maxSize <= 0");
		}
		this.maxSize = maxSize;
		this.map = new LinkedHashMap<String, Bitmap>(0, 0.75f, true);
	}

	/**
	 * Returns the Bitmap for {@code key} if it exists in the cache. If a Bitmap was returned, it is moved to the head
	 * of the queue. This returns null if a Bitmap is not cached.
	 */
	@Override
	public final Bitmap getBitmap(String key) {
		if (key == null) {
			throw new NullPointerException("key == null");
		}

		synchronized (this) {
			return map.get(key);
		}
	}

	/**
	 * 加入到内存缓存
	 */
	@Override
	public final void putBitmap(String key, Bitmap value) {
		if (key == null || value == null) {
			throw new NullPointerException("key == null || value == null");
		}

		synchronized (this) {
			size += sizeOf(key, value);
			Bitmap previous = map.put(key, value);
			if (previous != null) {
				size -= sizeOf(key, previous);
			}
		}
		trimToSize(maxSize);
	}

	/**
	 * 检验大小是不是超过设定的值.
	 * @param maxSize
	 */

	private void trimToSize(int maxSize) {
		while (true) {
			String key;
			Bitmap value;
			synchronized (this) {
				if (size < 0 || (map.isEmpty() && size != 0)) {
					throw new IllegalStateException(getClass().getName() + ".sizeOf() is reporting inconsistent results!");
				}
				if (size <= maxSize || map.isEmpty()) {
					break;
				}
				Map.Entry<String, Bitmap> toEvict = map.entrySet().iterator().next();
				if (toEvict == null) {
					break;
				}
				key = toEvict.getKey();
				value = toEvict.getValue();
				map.remove(key);
				size -= sizeOf(key, value);
			}
		}
	}

	/**
	 * 从内存缓存中移除一个
	 */
	@Override
	public final void remove(String key) {
		if (key == null) {
			throw new NullPointerException("key == null");
		}

		synchronized (this) {
			Bitmap previous = map.remove(key);
			if (previous != null) {
				size -= sizeOf(key, previous);
			}
		}
	}

	/**
	 * 获取所有的健集合
	 */
	@Override
	public Collection<String> keys() {
		synchronized (this) {
			return new HashSet<String>(map.keySet());
		}
	}

	/**
	 * 传一个-1将会情况缓存
	 */
	@Override
	public void clear() {
		trimToSize(-1);
	}

	/**
	 * Returns the size {@code Bitmap} in bytes.
	 * <p/>
	 * An entry's size must not change while it is in the cache.
	 */
	private int sizeOf(String key, Bitmap value) {
		return value.getRowBytes() * value.getHeight();
	}

	@Override
	public synchronized final String toString() {
		return String.format("LruCache[maxSize=%d]", maxSize);
	}

}