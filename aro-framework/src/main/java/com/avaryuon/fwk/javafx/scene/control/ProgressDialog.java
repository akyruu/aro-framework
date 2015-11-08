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
package com.avaryuon.fwk.javafx.scene.control;

import java.util.Optional;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.concurrent.Service;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DialogPane;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import lombok.Getter;
import lombok.Setter;

import com.avaryuon.fwk.AroMsgKeys;
import com.avaryuon.fwk.view.util.DialogUtils;

/**
 * Dialog with a label and a progress bar.
 * 
 * @author Akyruu (akyruu@hotmail.com)
 * @version 0.1
 */
public class ProgressDialog extends AroDialog< ButtonType > {
	/* STATIC FIELDS ======================================================= */
	public static final ButtonType SEE = new ButtonType( "see",
			ButtonData.OTHER );

	/* I18n ---------------------------------------------------------------- */
	private static final String MSG_TITLE = "dialog.progress.title";
	private static final String MSG_AUTOCLOSE = "dialog.progress.autoclose";

	private static final String MSG_STATE_STARTED = "dialog.progress.started";
	private static final String MSG_STATE_FAILED = "dialog.progress.failed";
	private static final String MSG_STATE_SUCCEEDED = "dialog.progress.succeeded";
	private static final String MSG_STATE_CANCELLED = "dialog.progress.cancelled";

	/* FIELDS ============================================================== */
	/* Model --------------------------------------------------------------- */
	private Service< ? > service;
	private BooleanProperty cancelled;

	/* Options ------------------------------------------------------------- */
	private BooleanProperty autoClose;
	@Getter
	@Setter
	private boolean seeButtonVisible;

	/* View ---------------------------------------------------------------- */
	private ProgressBar progressBar;
	private CheckBox autoCloseCheck;

	private Button seeButton;

	/* CONSTRUCTORS ======================================================== */
	public ProgressDialog( Service< ? > service ) {
		this( service, null );
	}

	public ProgressDialog( Service< ? > service, String title ) {
		this( service, title, Modality.NONE );
	}

	public ProgressDialog( Service< ? > service, String title, Modality modality ) {
		super( title, modality, false );
		setTitleIfBlank( getBundleMessage( MSG_TITLE ) );

		this.service = service;
		createModel();
		createUI();
	}

	/* METHODS ============================================================= */
	/* Access -------------------------------------------------------------- */
	public boolean isAutoClose() {
		return autoClose.get();
	}

	public void setAutoClose( boolean autoClose ) {
		this.autoClose.set( autoClose );
	}

	/* Create -------------------------------------------------------------- */
	@Override
	protected void createModel() {
		service.messageProperty()
				.addListener(
						( observable, oldValue, newValue ) -> setHeaderText( newValue ) );
		service.exceptionProperty().addListener(
				( observable, oldValue, newValue ) -> onError() );
		service.setOnCancelled( e -> onCancel() );
		service.setOnSucceeded( e -> onSuccess() );
		cancelled = new SimpleBooleanProperty( false );

		/* Options */
		autoClose = new SimpleBooleanProperty( false );
	}

	@Override
	protected void createUI() {
		DialogPane mainPane = getDialogPane();

		/* Header */
		mainPane.setHeaderText( getBundleMessage( MSG_STATE_STARTED ) );

		/* Content */
		VBox content = new VBox( 20.0 );
		progressBar = new ProgressBar();
		progressBar.setMaxWidth( Double.MAX_VALUE );
		progressBar.progressProperty().bind( service.progressProperty() );
		content.getChildren().add( progressBar );

		autoCloseCheck = new CheckBox( getBundleMessage( MSG_AUTOCLOSE ) );
		autoCloseCheck.selectedProperty().bindBidirectional( autoClose );
		content.getChildren().add( autoCloseCheck );

		mainPane.getButtonTypes().add( ButtonType.CANCEL );
		Button cancelButton = (Button) mainPane
				.lookupButton( ButtonType.CANCEL );
		cancelButton.setOnAction( e -> {
			cancelled.set( true );
			service.cancel();
		} );

		mainPane.setContent( content );
	}

	/* Showing ------------------------------------------------------------- */
	public Optional< ButtonType > showAndStart() {
		service.start();
		return showAndWait();
	}

	/* Actions ------------------------------------------------------------- */
	private void onSuccess() {
		if( autoClose.get() ) {
			close();
		} else {
			onFinish( MSG_STATE_SUCCEEDED, seeButtonVisible );
		}
	}

	private void onCancel() {
		if( autoClose.get() ) {
			close();
		} else {
			onFinish( MSG_STATE_CANCELLED, false );
			showAndWait();
		}
	}

	private void onError() {
		// Check no cancel case
		Throwable t = service.getException();
		if( cancelled.get() && (t instanceof InterruptedException) ) {
			return;
		}

		onFinish( MSG_STATE_FAILED, false );
		Node expansion = DialogUtils.buildExpansion( t );
		getDialogPane().setExpandableContent( expansion );
		showAndWait();
	}

	private void onFinish( String msgKey, boolean seeButtonVisible ) {
		setHeaderText( getBundleMessage( msgKey ) );
		progressBar.progressProperty().unbind();
		progressBar.setProgress( 100 );

		autoCloseCheck.setDisable( true );

		DialogPane mainPane = getDialogPane();
		mainPane.getButtonTypes().remove( ButtonType.CANCEL );
		if( seeButtonVisible ) {
			mainPane.getButtonTypes().add( SEE );
			seeButton = (Button) mainPane.lookupButton( SEE );
			seeButton
					.setText( getBundleMessage( AroMsgKeys.COMMON_ACTION_SEE ) );
		}
		mainPane.getButtonTypes().add( ButtonType.CLOSE );
	}
}
