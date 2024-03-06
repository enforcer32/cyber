package com.github.enforcer32.cyber.http;

import com.github.enforcer32.cyber.core.Logger;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;

public class HttpServer {
	private ServerSocket serverSocket;
	HttpHandleCallback handleCallback;

	public void setHandleCallback(HttpHandleCallback handleCallback) {
		this.handleCallback = handleCallback;
	}

	public void listen(String host, int port) {
		try {
			serverSocket = new ServerSocket(port, 255, InetAddress.getByName((host)));
			serverSocket.setReuseAddress(true);

			Logger.getLogger().info(String.format("Http Server Running on '%s':'%d'", host, port));

			while (true) {
					Socket clientSocket = serverSocket.accept();
					HttpClientHandler clientHandler = new HttpClientHandler(clientSocket, handleCallback);
					new Thread(clientHandler).start();
			}
		} catch (IOException e) {
			Logger.getLogger().error(e.getMessage());
		}
	}

	private static class HttpClientHandler implements Runnable {
		private final Socket clientSocket;
		private final HttpHandleCallback handleCallback;

		public HttpClientHandler(Socket clientSocket, HttpHandleCallback handleCallback) {
			this.clientSocket = clientSocket;
			this.handleCallback = handleCallback;
		}

		@Override
		public void run() {
			try {
				String data = HttpUtil.socketReadAll(clientSocket);
				HttpRequest request = HttpParser.parseRequest(data);
				if(request != null)
					handleRequest(request);
//					request.dump();
				clientSocket.close();
			} catch (IOException e) {
				Logger.getLogger().error(e.getMessage());
			} finally {
				try {
					if (clientSocket != null)
						clientSocket.close();
				} catch (IOException e) {
					Logger.getLogger().error(e.getMessage());
				}
			}
		}

		private void handleRequest(HttpRequest req) {
			handleCallback.handle(req, new HttpResponse(clientSocket));
		}
	}
}
