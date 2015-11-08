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
package com.avaryuon.fwk.view.util;

import javafx.scene.Node;

/**
 * Contains utility methods for Java FX nodes. Don't instantiate.
 * 
 * @author Akyruu (akyruu@hotmail.com)
 * @version 0.1
 */
public final class NodeUtils {
	/* STATIC FIELDS ======================================================= */
	// Nothing here

	/* FIELDS ============================================================== */
	// Nothing here

	/* CONSTRUCTORS ======================================================== */
	private NodeUtils() {
		// Nothing to do
	}

	/* METHODS ============================================================= */
	/* Location ------------------------------------------------------------ */
	public static double getLocalX( Node node ) {
		return node.localToScene( node.getBoundsInLocal() ).getMinX()
				+ node.getScene().getX() + node.getScene().getWindow().getX();
	}

	public static double getLocalY( Node node ) {
		return node.localToScene( node.getBoundsInLocal() ).getMinY()
				+ node.getScene().getY() + node.getScene().getWindow().getY();
	}
}
