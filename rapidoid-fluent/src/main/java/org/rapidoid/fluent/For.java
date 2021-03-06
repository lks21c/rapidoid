package org.rapidoid.fluent;

import java.util.stream.Stream;

import org.rapidoid.fluent.forx.ForEach;

/*
 * #%L
 * rapidoid-fluent
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

/**
 * @author Nikolche Mihajlovski
 * @since 5.0.0
 */
public class For {

	public static <T> ForEach<T> each(Iterable<T> items) {
		return new ForEach<T>(Do.stream(items));
	}

	public static <T> ForEach<T> each(Stream<T> stream) {
		return new ForEach<T>(Do.stream(stream));
	}

}
