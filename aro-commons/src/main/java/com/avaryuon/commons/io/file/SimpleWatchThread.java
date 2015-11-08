package com.avaryuon.commons.io.file;

import java.io.IOException;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

/**
 * Simple watch thread.
 * 
 * @see WatchThread
 * 
 * @author Akyruu (akyruu@hotmail.com)
 * @version 0.1
 */
public class SimpleWatchThread extends WatchThread {
	/* STATIC FIELDS ======================================================= */
	// Nothing here

	/* FIELDS ============================================================== */
	// Nothing here

	/* CONSTRUCTORS ======================================================== */
	public SimpleWatchThread() throws IOException {
		super();
	}

	public SimpleWatchThread( WatchService watcher ) {
		super( watcher );
	}

	/* METHODS ============================================================= */
	/* Overriding - WatchThread.class -------------------------------------- */
	/**
	 * @see WatchThread#processChange(WatchKey)
	 */
	@Override
	protected boolean processChange( WatchKey key ) {
		return true;
	}

	/**
	 * @see WatchThread#processEvent(WatchKey, WatchEvent)
	 */
	@Override
	protected void processEvent( WatchKey key, WatchEvent< ? > event ) {
		// Nothing to do
	}
}
