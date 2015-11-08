package com.avaryuon.fwk.javafx.util;

import javafx.fxml.JavaFXBuilderFactory;
import javafx.util.Builder;
import javafx.util.BuilderFactory;

import javax.enterprise.context.Dependent;

/**
 * Build factory for ARO components.
 * 
 * @author Akyruu (akyruu@hotmail.com)
 * @version 0.1
 */
@Dependent
public class AroBuilderFactory implements BuilderFactory {
	/* FIELDS ============================================================== */
	private BuilderFactory baseFct;

	/* CONSTRUCTORS ======================================================== */
	public AroBuilderFactory() {
		baseFct = new JavaFXBuilderFactory();
	}

	/* METHODS ============================================================= */
	/* Overriding - BuilderFactory.class ----------------------------------- */
	@Override
	public Builder< ? > getBuilder( Class< ? > type ) {
		return baseFct.getBuilder( type );
	}

}
