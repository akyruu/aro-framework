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
package com.avaryuon.fwk.javafx;

import javafx.scene.Parent;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

/**
 * View used for load FXML.
 * 
 * @author Akyruu (akyruu@hotmail.com)
 * @version 0.1
 */
public class View {
	/* STATIC FIELDS ======================================================= */
	// Nothing here

	/* FIELDS ============================================================== */
	@Getter
	@Setter(AccessLevel.PACKAGE)
	private Parent root;
	@Setter(AccessLevel.PACKAGE)
	private Object controller;

	/* CONSTRUCTORS ======================================================== */
	// Nothing here

	/* METHODS ============================================================= */
	@SuppressWarnings("unchecked")
	public < T > T getController() {
		return (T) controller;
	}

}
