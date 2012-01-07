/*
 *      Copyright (C) 2005-2015 Team XBMC
 *      http://xbmc.org
 *
 *  This Program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 2, or (at your option)
 *  any later version.
 *
 *  This Program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with XBMC Remote; see the file license.  If not, write to
 *  the Free Software Foundation, 675 Mass Ave, Cambridge, MA 02139, USA.
 *  http://www.gnu.org/copyleft/gpl.html
 *
 */

package org.xbmc.android.jsonrpc.client;

import org.json.JSONObject;
import org.xbmc.android.jsonrpc.io.ApiException;
import org.xbmc.android.jsonrpc.io.JsonApiRequest;
import org.xbmc.android.jsonrpc.service.AudioSyncService;
import org.xbmc.android.zeroconf.XBMCHost;

/**
 * Parent class for all "standalone" clients. Contains network logic
 * inclusively error handling.
 *
 * @author freezy <freezy@xbmc.org>
 */
public abstract class AbstractClient {

	private final static String TAG = AbstractClient.class.getSimpleName();

	private final static String URL_SUFFIX = "/jsonrpc";

	private final XBMCHost mHost;

	/**
	 * Empty constructor
	 */
	protected AbstractClient() {
		mHost = null;
	}

	/**
	 * Sometimes we don't want the standard host to be used, but another one,
	 * for example when we're adding a new account and probing for version.
	 * @param host
	 */
	protected AbstractClient(XBMCHost host) {
		mHost = host;
	}

	/**
	 * Synchronously posts the request object to XBMC's JSONRPC API and returns
	 * the unserialized response as {@link JSONObject}.
	 *
	 * @param entity Request object generated by API class
	 * @param errorHandler Error handler in case something goes wrong
	 * @return Response as JSONObject
	 */
	protected JSONObject execute(JSONObject entity, ErrorHandler errorHandler) {
		JSONObject result = null;

		try {
			result = JsonApiRequest.execute(AudioSyncService.URL, entity);
		} catch(ApiException e) {
			if (errorHandler != null) {
				errorHandler.handleError(e);
			}
		}

		return result;
	}

	private String getUrl() {
		if (mHost == null) {
			return AudioSyncService.URL;
		} else {
			return "http://" + mHost.getAddress() + ":" + mHost.getPort() + URL_SUFFIX;
		}
	}

	/**
	 * Defines error codes and an action that is executed on error. Is generally
	 * executed on the UI thread.
	 */
	public interface ErrorHandler {
		/**
		 * Implement your error logic here.
		 * @param code Error code as defined above
		 * @param message Error message in english. For translations, refer to the error code.
		 */
		void handleError(ApiException e);
	}

	/**
	 * Handles errors, even if the callback is null.
	 * @param handler Error handler which can be null.
	 * @param code
	 * @param message
	 */
	protected void handleError(ErrorHandler handler, ApiException e) {
		if (handler != null) {
			handler.handleError(e);
		}
	}
}
