package com.github.enforcer32.cyber.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

public class HttpUtil {
	public static String socketReadAll(Socket socket) throws IOException {
		BufferedReader input = new BufferedReader(
				new InputStreamReader(socket.getInputStream()));
		StringBuilder sb = new StringBuilder();
		String line;
		while (input.ready() && (line = input.readLine()) != null)
			sb.append(line).append("\r\n");
		return sb.toString();
	}

	public static HttpMethod HttpMethodParser(String method) {
		switch (method) {
			case "GET":
				return HttpMethod.GET;
			case "HEAD":
				return HttpMethod.HEAD;
			case "POST":
				return HttpMethod.POST;
			case "PUT":
				return HttpMethod.PUT;
			case "DELETE":
				return HttpMethod.DELETE;
			case "CONNECT":
				return HttpMethod.CONNECT;
			case "OPTIONS":
				return HttpMethod.OPTIONS;
			case "TRACE":
				return HttpMethod.TRACE;
			case "PATCH":
				return HttpMethod.PATCH;
		}

		return null;
	}
}
