package com.github.enforcer32.cyber;

import com.github.enforcer32.cyber.core.Application;

public class Cyber {
    public static void main(String[] args) {
		Application cyber = new Application();

		cyber.getRouter().get("/hello", (req, res) -> {
			System.out.println("/HELLO ROUTE");
			res.send("200 OK", "HELLO WORLD").end();
		});

		cyber.getRouter().get("/hello2", (req, res) -> {
			System.out.println("/HELLO ROUTE 2");
			res.send("201 CREATED", "HELLO 2").end();
		});

		cyber.getRouter().get("/hello3", (req, res) -> {
			System.out.println("MIDDLE WARE FELLA");
		}, (req, res) -> {
			System.out.println("/HELLO ROUTE");
			res.send("200 OK", "HELLO WORLD").end();
		});

		cyber.run("0.0.0.0", 8081);
    }
}
