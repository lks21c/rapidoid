package org.rapidoid.quick;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.rapidoid.annotation.Authors;
import org.rapidoid.annotation.Since;
import org.rapidoid.app.DollarPage;
import org.rapidoid.app.IOTool;
import org.rapidoid.app.IOToolImpl;
import org.rapidoid.concurrent.Callback;
import org.rapidoid.http.HTTP;
import org.rapidoid.http.HttpClient;
import org.rapidoid.http.REST;
import org.rapidoid.http.RESTClient;
import org.rapidoid.http.Req;
import org.rapidoid.plugins.Plugins;
import org.rapidoid.plugins.cache.CachePlugin;
import org.rapidoid.plugins.db.DBPlugin;
import org.rapidoid.plugins.email.EmailPlugin;
import org.rapidoid.plugins.entities.EntitiesPlugin;
import org.rapidoid.plugins.sms.SMSPlugin;
import org.rapidoid.plugins.templates.TemplatesPlugin;
import org.rapidoid.sql.SQL;
import org.rapidoid.sql.SQLAPI;
import org.rapidoid.u.U;

import redis.clients.jedis.Jedis;

/*
 * #%L
 * rapidoid-quick
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
public class Dollar {

	/************ DATA CONTAINERS *************/

	public final Map<?, ?> extra = U.map();

	public final Map<Object, Map<Object, Object>> maps = U.mapOfMaps();

	public final Map<Object, List<Object>> lists = U.mapOfLists();

	public final Map<Object, Set<Object>> sets = U.mapOfSets();

	/************ SERVICES *************/

	public final HttpClient http = HTTP.DEFAULT_CLIENT;

	public final RESTClient services = REST.DEFAULT_CLIENT;

	public final DBPlugin db = Plugins.db();

	public final DBPlugin hibernate = Plugins.db("hibernate");

	public final DBPlugin cassandra = Plugins.db("cassandra");

	public final EntitiesPlugin entities = Plugins.entities();

	public final EmailPlugin email = Plugins.email();

	public final SMSPlugin sms = Plugins.sms();

	public final CachePlugin cache = Plugins.cache();

	public final CachePlugin memcached = Plugins.cache("memcached");

	public final TemplatesPlugin templates = Plugins.templates();

	public final SQLAPI mysql = SQL.defaultInstance();

	public final IOTool io = new IOToolImpl();

	public final SQLAPI jdbc = SQL.defaultInstance();

	public final Req req;

	public final Map<String, Object> bindings;

	private volatile boolean hasResult = false;

	public Dollar(Req x, Map<String, Object> bindings) {
		this.req = x;
		this.bindings = bindings;
	}

	@Override
	public String toString() {
		return "$";
	}

	public List<Map<String, Object>> sql(String sql, Object[] args) {
		if (sql.trim().toLowerCase().startsWith("select ")) {
			return jdbc.query(sql, args);
		} else {
			jdbc.execute(sql, args);
			return null;
		}
	}

	public List<Map<String, Object>> sql(String sql) {
		return sql(sql, new Object[0]);
	}

	public List<Map<String, Object>> cql(String cql, Object[] args) {
		return cassandra.query(cql, args);
	}

	public void cql(String cql, Object[] args, Callback<List<Map<String, Object>>> callback) {
		cassandra.queryAsync(cql, callback, args);
	}

	public List<Map<String, Object>> cql(String cql) {
		return cql(cql, new Object[0]);
	}

	public DollarPage page(Object value, Map<String, Object> config) {
		return new DollarPage(value, config);
	}

	@SuppressWarnings("unchecked")
	public DollarPage page(Object value) {
		return page(value, Collections.EMPTY_MAP);
	}

	public Jedis redis() {
		return JedisTool.get();
	}

	public void result(Object result) {
		hasResult = true;
		Scripting.onScriptResult(req, result);
	}

	public boolean hasResult() {
		return hasResult;
	}

}
