/*
 * Copyright 2015 Akyruu (akyruu@hotmail.com)
 * 
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * 
 *     http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.avaryuon.fwk.javafx.concurrent;

import javafx.concurrent.Service;
import javafx.concurrent.Task;

/**
 * Wraps existing task in service.
 * 
 * @author Akyruu (akyruu@hotmail.com)
 * @version 0.1
 */
public class WrapService< T > extends Service< T > {
	/* STATIC FIELDS ======================================================= */
	// Nothing here

	/* FIELDS ============================================================== */
	private Task< T > wrapped;

	/* CONSTRUCTORS ======================================================== */
	public WrapService( Task< T > task ) {
		wrapped = task;
	}

	/* METHODS ============================================================= */
	/**
	 * @see javafx.concurrent.Service#createTask()
	 */
	@Override
	protected Task< T > createTask() {
		return wrapped;
	}
}
