package br.com.infnet;

import io.javalin.Javalin;

import java.util.Objects;

public class Main {
    private static final int PORT = 7000;
    private static final String SECRET_KEY = "123456";

    public static void main(String[] args) {
        Javalin app = Javalin.create().start(PORT);

        // Endpoint 1: Exposição de secret key
        app.get("/hello", ctx -> {
            String user = ctx.queryParam("user");

            if ("admin".equals(user)) {
                ctx.result("Bem-vindo admin! Chave: " + SECRET_KEY);
            } else {
                ctx.result("Olá, " + user + " !");
            }
        });

        // Endpoint 2: Simulação de SQL Injection
        app.get("/getUser", ctx -> {
            String id = ctx.queryParam("id");
            String query = "SELECT * FROM users WHERE id = " + id;

            System.out.println("Executando query (demo): " + query);

            try {
                int userId = Integer.parseInt(Objects.requireNonNull(id));
                String[] users = {"Alpha", "Bravo", "Charlie", "Delta", "Echo", "Foxtrot"};

                if (userId > 0 && userId < users.length) {
                    ctx.result(users[userId - 1]);
                } else {
                    ctx.result("Usuário não encontrado");
                }
            } catch (Exception e) {
                ctx.result("Erro no input");
            }
        });

        // Endpoint 3: Simulação de command injection
        app.get("/run", ctx -> {
            String cmd = ctx.queryParam("cmd");

            try {
                Runtime.getRuntime().exec(cmd);
            } catch (Exception e) {
                ctx.result("Erro na solicitação");
            }

            ctx.result("Comando recebido: " + cmd);
        });
    }
}
