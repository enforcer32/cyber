package com.github.enforcer32.cyber.core;

import com.github.enforcer32.cyber.http.HttpHandleCallback;
import com.github.enforcer32.cyber.http.HttpMethod;
import com.github.enforcer32.cyber.http.HttpRequest;
import com.github.enforcer32.cyber.http.HttpResponse;
import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class Router {
	private HttpRequest request;
	private HttpResponse response;
	private MultiValuedMap<HttpMethod, Route> routerMap;

	public Router(HttpRequest request, HttpResponse response) {
		this.request = request;
		this.response = response;
		routerMap = new ArrayListValuedHashMap<HttpMethod, Route>();
	}

	public HttpRequest getRequest() {
		return request;
	}

	public void setRequest(HttpRequest request) {
		this.request = request;
	}

	public HttpResponse getResponse() {
		return response;
	}

	public void setResponse(HttpResponse response) {
		this.response = response;
	}

	public void get(String uri, HttpHandleCallback... callbacks) {
		var cbs = Arrays.asList(callbacks);
		HttpHandleCallback callback = cbs.get(cbs.size() - 1);

		Route route = new Route(HttpMethod.GET, uri, callback);
		for(int i = 0; i < cbs.size() - 1; i++ )
			route.registerMiddleware(cbs.get(i));

		routerMap.put(HttpMethod.GET, route);
	}

	public Collection<Route> getRoutes(HttpMethod method) {
		return routerMap.get(method);
	}

	public Route getRoute(HttpMethod method, String uri) {
		List<Route> routes = getRoutes(method).stream().filter(r -> r.method.equals(method) && r.uri.equals(uri)).collect(Collectors.toList());
		return (routes == null || routes.size() < 1) ? null : routes.get(routes.size() - 1);
	}

	public boolean resolve() {
		HttpMethod method = request.getMethod();
		String uri = request.getUri();

		Route route = getRoute(method, uri);
		if(route == null){
			response.send("404 NOT FOUND", Templates.NotFound404(uri));
			return false;
		}

		for(var middleware: route.middlewares)
			middleware.handle(request, response);
		route.callback.handle(request, response);

		return true;
	}

	private class Route {
		private HttpMethod method;
		private String uri;
		private HttpHandleCallback callback;
		private ArrayList<HttpHandleCallback> middlewares;

		public Route(HttpMethod method, String uri, HttpHandleCallback callback) {
			this.method = method;
			this.uri = uri;
			this.callback = callback;
			this.middlewares = new ArrayList<>();
		}

		public HttpMethod getMethod() {
			return method;
		}

		public String getUri() {
			return uri;
		}

		public HttpHandleCallback getCallback() {
			return callback;
		}

		public ArrayList<HttpHandleCallback> getMiddlewares() {
			return middlewares;
		}

		public void registerMiddleware(HttpHandleCallback callback) {
			middlewares.add(callback);
		}

		@Override
		public String toString() {
			return "Route{" +
					"method=" + method +
					", uri='" + uri + '\'' +
					", callback=" + callback +
					", middlewares=" + middlewares +
					'}';
		}
	}
}
