package com.github.enforcer32.cyber.http;

import java.util.HashMap;
import java.util.Map;

public class HttpRequest {
	private HttpMethod method;
	private String uri;
	private String httpVersion;
	private float httpVersionFloat;

	private Map<String, String> headers;
	private String body;

	public HttpRequest() {
		this.headers = new HashMap<>();
	}

	public HttpMethod getMethod() {
		return method;
	}

	public void setMethod(HttpMethod method) {
		this.method = method;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public float getHttpVersionFloat() {
		return httpVersionFloat;
	}

	public void setHttpVersionFloat(float version) {
		this.httpVersionFloat = version;
	}

	public String getHttpVersion() {
		return httpVersion;
	}

	public void setHttpVersion(String httpVersion) {
		this.httpVersion = httpVersion;
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
	}

	public void dump() {
		System.out.println("<----- REQUEST-LINE ----->");
		System.out.println("Method: " + method);
		System.out.println("Uri: " + uri);
		System.out.println("Http Version: " + httpVersion);
		System.out.println("Http Version Float: " + httpVersionFloat);
		System.out.println("<-----  ----->");

		System.out.println("<----- HEADERS ----->");
		for(var entry: headers.entrySet())
			System.out.println(entry.getKey() + ": " + entry.getValue());
		System.out.println("<-----  ----->");

		System.out.println("<----- BODY ----->");
		System.out.println(body);
		System.out.println("<-----  ----->");
	}

	@Override
	public String toString() {
		return "HttpRequest{" +
				"method=" + method +
				", uri='" + uri + '\'' +
				", httpVersion='" + httpVersion + '\'' +
				", httpVersionFloat=" + httpVersionFloat +
				", headers=" + headers +
				", body='" + body + '\'' +
				'}';
	}
}
