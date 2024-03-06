package com.github.enforcer32.cyber.http;

import com.github.enforcer32.cyber.core.Logger;

public class HttpParser {
	public static HttpRequest parseRequest(String data) {
		HttpRequest httpRequest = new HttpRequest();

		try {
			String requestLine = data.substring(0, data.indexOf("\r\n"));
			parseRequestLine(httpRequest, requestLine);

			int headerStartIdx = requestLine.length() + 2;
			String headers = data.substring(headerStartIdx, data.indexOf("\r\n\r\n"));
			parseHeaders(httpRequest, headers);

			String body = data.substring(data.indexOf("\r\n\r\n") + 4);
			httpRequest.setBody(body.trim());
		} catch (Exception e) {
			Logger.getLogger().debug(e.getMessage());
			return null;
		}

		return httpRequest;
	}

	private static void parseRequestLine(HttpRequest request, String data) {
		String method = data.substring(0, data.indexOf(' '));
		String uri = data.substring(method.length()+1, data.indexOf(" HTTP/"));
		String httpVersion = data.substring(data.indexOf(" HTTP/") + 1);

		request.setMethod(HttpUtil.HttpMethodParser(method));
		request.setUri(uri);
		request.setHttpVersion(httpVersion);
		request.setHttpVersionFloat(Float.parseFloat(httpVersion.substring(httpVersion.indexOf('/') + 1)));
	}

	private static void parseHeaders(HttpRequest request, String data) {
		String lines[] = data.split("\\r?\\n");
		for(int i = 0; i < lines.length; i++) {
			String line = lines[i];
			String key = line.substring(0, line.indexOf(':'));
			String value = line.substring(key.length() + 2);
			if(key == null || value == null)
				continue;
			request.setHeader(key, value);
		}
	}
}
