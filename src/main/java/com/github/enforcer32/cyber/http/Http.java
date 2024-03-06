package com.github.enforcer32.cyber.http;

public class Http {
	public static HttpServer createServer(HttpHandleCallback callback) {
		HttpServer server = new HttpServer();
		server.setHandleCallback(callback);
		return server;
	}
}
