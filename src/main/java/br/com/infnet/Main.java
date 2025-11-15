package br.com.infnet;

import io.javalin.Javalin;

public class Main {
    private static final int PORT = 7000;

    public static void main(String[] args) {
        Javalin app = Javalin.create(config -> {
            // Não definir headers de segurança intencionalmente
        }).start(PORT);

        app.get("/", ctx -> ctx.result("OK"));

        // Vulnerabilidade 1: XSS refletido
        app.get("/xss", ctx -> {
            String name = ctx.queryParam("name");
            ctx.contentType("text/html");
            ctx.result("<h1>Olá " + name + "!</h1>");
        });

        // Vulnerabilidade 2: SQL Injection real (simulado com array)
        app.get("/users", ctx -> {
            String id = ctx.queryParam("id");
            String[] users = {"Alpha", "Bravo", "Charlie"};
            try {
                // Aqui simula SQL injection ao permitir qualquer string
                int userId = Integer.parseInt(id);
                ctx.result(users[userId - 1]);
            } catch (Exception e) {
                ctx.result("Erro: " + e.getMessage());
            }
        });

        // Vulnerabilidade 3: Exposição de segredo sensível
        app.get("/secret", ctx -> {
            String token = "SECRET_TOKEN_123456";
            ctx.result("Token secreto: " + token);
        });

        // Vulnerabilidade 4: Insecure headers ausentes (não adiciona nenhum header de segurança)
        app.get("/insecure", ctx -> {
            ctx.result("Conteúdo inseguro aqui!");
        });
    }
}
