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
package com.avaryuon.fwk.javafx.scene.image;

import javafx.beans.NamedArg;
import javafx.scene.image.ImageView;

/**
 * Specific view for icons.
 * 
 * @author Akyruu (akyruu@hotmail.com)
 * @version 0.1
 */
public class IconView extends ImageView {
	/* STATIC FIELDS ======================================================= */
	// Nothing here

	/* FIELDS ============================================================== */
	// Nothing here

	/* CONSTRUCTORS ======================================================== */
	public IconView() {
		this( null );
	}

	public IconView( @NamedArg("name") String name, @NamedArg("size") int size ) {
		this( new Icon( name, size ) );
	}

	public IconView( @NamedArg("icon") Icon icon ) {
		super();
		if( icon != null ) {
			setImage( icon );
			setFitWidth( icon.getWidth() );
			setFitHeight( icon.getHeight() );
			setPreserveRatio( icon.isPreserveRatio() );
		}
	}

	/* METHODS ============================================================= */
	public void setIcon( Icon icon ) {
		setImage( icon );
		setFitWidth( icon.getWidth() );
		setFitHeight( icon.getHeight() );
		setPreserveRatio( icon.isPreserveRatio() );
	}

}
