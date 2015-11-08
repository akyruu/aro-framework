package com.avaryuon.commons.io.file;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_DELETE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.WatchEvent;
import java.nio.file.WatchEvent.Kind;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.avaryuon.commons.LogUtils;

/**
 * <b>Utility class for watcher threads.<b>
 * <p>
 * Extends this class for simplify usage of watch thread.
 * <p>
 * 
 * @author Akyruu (akyruu@hotmail.com)
 * @version 0.1
 */
public abstract class WatchThread extends Thread {
	/* STATIC FIELDS ======================================================= */
	private static final Logger LOGGER = LoggerFactory
			.getLogger( WatchThread.class );

	/* FIELDS ============================================================== */
	private WatchService watcher;

	/* CONSTRUCTORS ======================================================== */
	public WatchThread() throws IOException {
		this( FileSystems.getDefault().newWatchService() );
	}

	public WatchThread( WatchService watcher ) {
		this.watcher = watcher;
	}

	/* METHODS ============================================================= */
	/* Watching ------------------------------------------------------------ */
	public boolean register( Path folder ) {
		return register( folder, ENTRY_CREATE, ENTRY_MODIFY, ENTRY_DELETE );
	}

	public final boolean register( Path folder, Kind< ? >... events ) {
		boolean success = false;
		try {
			folder.register( watcher, events );
			success = true;
		} catch( IOException ex ) {
			LogUtils.logError( LOGGER, "Failed to register {}", ex, folder );
		}
		return success;
	}

	/* Overriding - Thread.class ------------------------------------------ */
	@Override
	public final void run() {
		try {
			WatchKey key = null;
			while( true ) {
				try {
					key = watcher.take();
				} catch( InterruptedException ex ) {
					Thread.currentThread().interrupt();
					break;
				}
				process( key );
				key.reset();
			}
		} finally {
			try {
				watcher.close();
			} catch( IOException ex ) {
				LOGGER.error( "Failed to close watcher", ex );
			}
		}
	}

	private void process( WatchKey key ) {
		if( processChange( key ) ) {
			for( WatchEvent< ? > event : key.pollEvents() ) {
				processEvent( key, event );
			}
		}
	}

	/**
	 * Processes a one or more changes. Called after a take and before
	 * processEvent().
	 * 
	 * @param key
	 *            Key took.
	 * @return True for process events, false for skip.
	 */
	protected abstract boolean processChange( WatchKey key );

	/**
	 * Processes an event. Called for all event took in key.
	 * 
	 * @param key
	 *            Key took.
	 * @param event
	 *            Event handled.
	 */
	protected abstract void processEvent( WatchKey key, WatchEvent< ? > event );
}
