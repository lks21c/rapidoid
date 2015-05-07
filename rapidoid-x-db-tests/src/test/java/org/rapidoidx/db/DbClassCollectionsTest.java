package org.rapidoidx.db;

/*
 * #%L
 * rapidoid-x-db-tests
 * %%
 * Copyright (C) 2014 - 2015 Nikolche Mihajlovski
 * %%
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 * #L%
 */

import org.rapidoid.annotation.Authors;
import org.rapidoid.annotation.Since;
import org.rapidoidx.db.model.Person;
import org.rapidoidx.db.model.Post;
import org.rapidoidx.db.model.Profile;
import org.testng.annotations.Test;

@Authors("Nikolche Mihajlovski")
@Since("3.0.0")
public class DbClassCollectionsTest extends DbTestCommons {

	@Test
	public void testCollectionsPersistence() {

		Profile profile = new Profile();

		profile.posts.add(new Post("post 1"));
		DB.persist(profile);
		profile.posts.add(new Post("post 2"));
		profile.posts.add(new Post("post 3"));
		DB.persist(profile);

		int pn = 1;
		for (Post post : profile.posts) {
			post.likes.add(new Person("person " + pn, pn * 10));
			DB.persist(post);
			pn++;
		}

		DB.shutdown();
		DB.start();

		eq(DB.size(), 7);

		Post post1 = DB.get(1);
		eq(post1.content, "post 1");
		eq(post1.likes.size(), 1);
		eq(post1.likes.iterator().next().name, "person 1");

		Profile p = DB.get(2);
		eq(p.posts.size(), 3);

		Post post2 = DB.get(3);
		eq(post2.content, "post 2");
		eq(post2.likes.size(), 1);
		eq(post2.likes.iterator().next().name, "person 2");

		Post post3 = DB.get(4);
		eq(post3.content, "post 3");
		eq(post3.likes.size(), 1);
		eq(post3.likes.iterator().next().name, "person 3");

		DB.shutdown();
	}

}
