package org.rapidoid.plugins.cache.guava;

/*
 * #%L
 * rapidoid-cache-guava
 * %%
 * Copyright (C) 2014 - 2015 Nikolche Mihajlovski and contributors
 * %%
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *      http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * #L%
 */

import java.util.concurrent.TimeoutException;

import org.junit.Before;
import org.junit.Test;
import org.rapidoid.annotation.Authors;
import org.rapidoid.annotation.Since;
import org.rapidoid.concurrent.Callback;
import org.rapidoid.concurrent.Future;
import org.rapidoid.plugins.Plugins;
import org.rapidoid.plugins.cache.Cache;
import org.rapidoid.plugins.cache.ICache;
import org.rapidoid.test.TestCommons;
import org.rapidoid.u.U;

@Authors("Nikolche Mihajlovski")
@Since("4.1.0")
public class CacheTest extends TestCommons {

	@Before
	public void setup() {
		Plugins.register(new GuavaCachePlugin());
	}

	@Test
	public void testGetSet() {

		ICache<String, Object> cache = Cache.create("testcache", 10000, true);

		cache.set("key1", U.set(1, 2, 3), 10000).get();

		cache.set("key2", "abc", new Callback<Void>() {
			@Override
			public void onDone(Void result, Throwable error) throws Exception {}
		});

		cache.get("key1", new Callback<Object>() {
			@Override
			public void onDone(Object result, Throwable error) throws Exception {
				eq(result, U.set(1, 2, 3));
			}
		});

		Future<Object> s = cache.get("key2");
		eq(s.get(), "abc");

		try {
			eq(s.get(100), "abc");
		} catch (TimeoutException e) {
			throw U.rte(e);
		}
	}

	@Test
	public void testTimeout() {
		ICache<String, Object> cache = Cache.create("testcache", 1000, false);

		cache.set("key", "x").get();
		eq(cache.get("key").get(), "x");

		U.sleep(1500);

		// already expired
		isNull(cache.get("key").get());
	}

	@Test
	public void testNonexistingIsNull() {
		ICache<String, Object> cache = Cache.create("testcache", 1000, false);
		isNull(cache.get("key").get());
	}

	@Test(expected = IllegalArgumentException.class)
	public void testDifferentTimeoutNotSupported() {
		ICache<String, Integer> cache = Cache.create("testcache", 1000, true);
		cache.set("key", 123, 123);
	}

}
