package com.github.enforcer32.cyber.core;

import com.github.enforcer32.cyber.http.Http;
import com.github.enforcer32.cyber.http.HttpRequest;
import com.github.enforcer32.cyber.http.HttpResponse;
import com.github.enforcer32.cyber.http.HttpServer;

public class Application {
	private static Application app;
	private HttpRequest request;
	private HttpResponse response;
	private Router router;

	public Application() {
		this.app = this;
		this.request = new HttpRequest();
		this.response = new HttpResponse();
		this.router = new Router(request, response);
	}

	public Router getRouter() {
		return router;
	}

	public void setRouter(Router router) {
		this.router = router;
	}

	public void run(String host, int port) {
		HttpServer server = Http.createServer((req, res) -> {
			request = req;
			response = res;
			router.setRequest(req);
			router.setResponse(res);
			router.resolve();
		});

		server.listen(host, port);
	}
}
