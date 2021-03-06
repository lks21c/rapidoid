package org.rapidoid.plugins.email;

import org.rapidoid.annotation.Authors;
import org.rapidoid.annotation.Since;
import org.rapidoid.concurrent.Callback;
import org.rapidoid.plugins.Plugins;
import org.rapidoid.u.U;

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
public class Email {

	public static EmailPlugin instance(String name) {
		return Plugins.email(name);
	}

	public static void send(String to, String subject, String body, Callback<Void> callback) {
		send(U.list(to), null, null, subject, body, callback);
	}

	public static void send(Iterable<String> to, String subject, String body, Callback<Void> callback) {
		send(to, null, null, subject, body, callback);
	}

	public static void send(Iterable<String> to, Iterable<String> cc, String subject, String body,
			Callback<Void> callback) {
		send(to, cc, null, subject, body, callback);
	}

	public static void send(Iterable<String> to, Iterable<String> cc, Iterable<String> bcc, String subject,
			String body, Callback<Void> callback) {
		Plugins.email().send(to, cc, bcc, subject, body, callback);
	}

}
