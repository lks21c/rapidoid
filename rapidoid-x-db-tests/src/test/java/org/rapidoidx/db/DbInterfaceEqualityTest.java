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
import org.rapidoidx.db.model.IPost;
import org.testng.annotations.Test;

@Authors("Nikolche Mihajlovski")
@Since("3.0.0")
public class DbInterfaceEqualityTest extends DbTestCommons {

	@Test
	public void testEntityEquality() {
		eq(DB.entity(IPost.class, "id", 1L), DB.entity(IPost.class, "id", 1));
		eq(DB.entity(IPost.class, "id", 123L), DB.entity(IPost.class, "id", 123));
		neq(DB.entity(IPost.class, "id", 12345L), DB.entity(IPost.class));
		neq(DB.entity(IPost.class), DB.entity(IPost.class, "id", 5432));
		neq(DB.entity(IPost.class), DB.entity(IPost.class));
		neq(DB.entity(IPost.class, "id", 0L), DB.entity(IPost.class, "id", 0));
	}

}
