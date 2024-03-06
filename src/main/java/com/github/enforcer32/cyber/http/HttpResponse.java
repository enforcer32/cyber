package com.github.enforcer32.cyber.http;

import com.github.enforcer32.cyber.core.Logger;

import java.io.IOException;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class HttpResponse {
	Socket socket;
	HttpResponseBuilder respb;

	public HttpResponse() {
		socket = null;
		respb = new HttpResponseBuilder();
	}

	public HttpResponse(Socket socket) {
		this.socket = socket;
		respb = new HttpResponseBuilder();
	}

	public HttpResponse status(String status) {
		respb.setStatus(status);
		return this;
	}

	public HttpResponse body(String body) {
		respb.setBody(body);
		return this;
	}

	public HttpResponse send() {
		try {
			socket.getOutputStream().write(respb.toString().getBytes(StandardCharsets.UTF_8));
		} catch (IOException e) {
			Logger.getLogger().error(e.getMessage());
		}
		return this;
	}

	public HttpResponse send(String status, String data) {
		try {
			status(status);
			body(data);
			socket.getOutputStream().write(respb.toString().getBytes(StandardCharsets.UTF_8));
		} catch (IOException e) {
			Logger.getLogger().error(e.getMessage());
		}
		return this;
	}

	public void end() {
		try {
			socket.close();
		} catch (IOException e) {
			Logger.getLogger().error(e.getMessage());
		}
	}
}
