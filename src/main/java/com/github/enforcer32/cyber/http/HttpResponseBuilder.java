package com.github.enforcer32.cyber.http;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

public class HttpResponseBuilder {
	private String status;
	private Map<String, String> headers;
	private String body;

	public HttpResponseBuilder() {
		this.headers = new HashMap<>();
		this.setHeader("Content-Type", "text/html");
	}

	public HttpResponseBuilder(String status, String body) {
		this();
		this.setBody(body);
		this.status = status;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Map<String, String> getHeaders() {
		return headers;
	}

	public void setHeaders(Map<String, String> headers) {
		this.headers = headers;
	}

	public void setHeader(String key, String value) {
		this.headers.put(key, value);
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
		this.setHeader("Content-Length", Integer.toString(body.length()));
	}

	@Override
	public String toString() {
		String formattedHeaders = headers.entrySet().stream().map(e -> e.getKey() + ": " + e.getValue()).collect(Collectors.joining("\n"));
		return String.format("HTTP/1.1 %s\n%s\n\n%s", this.status, formattedHeaders,this.body);
	}
}
