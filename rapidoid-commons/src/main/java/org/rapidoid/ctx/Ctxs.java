package org.rapidoid.ctx;

import org.rapidoid.annotation.Authors;
import org.rapidoid.annotation.Since;
import org.rapidoid.log.Log;

/*
 * #%L
 * rapidoid-commons
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

@Authors("Nikolche Mihajlovski")
@Since("4.1.0")
public class Ctxs {

	private static final ThreadLocal<Ctx> CTXS = new ThreadLocal<Ctx>();

	private static volatile PersisterProvider persisterProvider = null;

	private Ctxs() {}

	public static Ctx get() {
		return CTXS.get();
	}

	public static Ctx ctx() {
		Ctx ctx = get();

		if (ctx == null) {
			throw new IllegalStateException("App ctx wasn't set!");
		}

		return ctx;
	}

	public static boolean hasContext() {
		return get() != null;
	}

	public static void attach(Ctx ctx) {
		if (!hasContext()) {
			if (ctx != null) {
				CTXS.set(ctx);
			}
		} else {
			throw new IllegalStateException("The context was already opened: " + ctx());
		}
	}

	public static Ctx open(String tag) {
		Ctx ctx = new Ctx(tag);
		Log.debug("Opening context", "ctx", ctx);
		attach(ctx);
		return ctx;
	}

	public static void close() {
		try {
			Ctx ctx = get();

			if (ctx != null) {
				ctx.close();
			} else {
				throw new RuntimeException("There is no opened context!");
			}
		} finally {
			CTXS.remove();
		}
	}

	public static PersisterProvider getPersisterProvider() {
		return persisterProvider;
	}

	public static void setPersisterProvider(PersisterProvider persisterProvider) {
		Ctxs.persisterProvider = persisterProvider;
	}

	public static Object createPersister() {
		return persisterProvider.openPersister();
	}

	public static void closePersister(Object persister) {
		persisterProvider.closePersister(persister);
	}

}
