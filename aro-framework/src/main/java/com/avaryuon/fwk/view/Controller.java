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
package com.avaryuon.fwk.view;

import javafx.fxml.FXML;
import lombok.AccessLevel;
import lombok.Setter;

/**
 * View controller.
 * 
 * @author Akyruu (akyruu@hotmail.com)
 * @version 0.1
 */
public class Controller {
	/* STATIC FIELDS ======================================================= */
	// Nothing here

	/* FIELDS ============================================================== */
	@Setter(AccessLevel.PACKAGE)
	private View view;

	/* CONSTRUCTORS ======================================================== */
	@FXML
	private final void initialize() {
		onInit();
	}

	/**
	 * Override this method for initialize your controller.
	 */
	protected void onInit() {
		// Nothing to do
	}

	/* METHODS ============================================================= */
	@SuppressWarnings("unchecked")
	public < T extends View > T getView() {
		return (T) view;
	}

}
