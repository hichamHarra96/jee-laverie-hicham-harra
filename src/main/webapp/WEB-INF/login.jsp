<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Accueil</title>
  <style>
    body {
      font-family: Arial, sans-serif;
      margin: 0;
      padding: 20px;
      background-color: #f5f5f5;
    }

    h1, h2 {
      color: #333;
    }

    form {
      margin-bottom: 20px;
      background-color: #fff;
      border-radius: 4px;
      padding: 20px;
      box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
    }

    label {
      display: block;
      margin-bottom: 5px;
      color: #333;
    }

    input[type="text"],
    input[type="password"] {
      width: 200px;
      padding: 10px;
      margin-bottom: 10px;
      border: 1px solid #ccc;
      border-radius: 4px;
    }

    input[type="submit"] {
      padding: 10px 20px;
      background-color: #007bff;
      color: #fff;
      border: none;
      border-radius: 4px;
      cursor: pointer;
    }

    p.error {
      color: red;
      margin-bottom: 10px;
    }

    p.success {
      color: green;
      margin-bottom: 10px;
    }
  </style>
</head>
<body>
<h1>Page d'accueil</h1>

<%-- Afficher le message d'erreur de connexion --%>
<% if (request.getAttribute("loginError") != null) { %>
<p class="error"><%= request.getAttribute("loginError") %></p>
<% } %>

<%-- Afficher le message de succès d'inscription --%>
<% if (request.getAttribute("signupSuccess") != null) { %>
<p class="success">Inscription réussie!</p>
<% } %>

<h2>Connexion</h2>
<form action="/authen" method="post">
  <input type="hidden" name="action" value="login">
  <label for="username">Nom d'utilisateur:</label>
  <input type="text" id="username" name="username" required>
  <br>
  <label for="password">Mot de passe:</label>
  <input type="password" id="password" name="password" required>
  <br>
  <input type="submit" value="Se connecter">
</form>

<h2>Inscription</h2>
<form action="/authen" method="post">
  <input type="hidden" name="action" value="signup">
  <label for="username">Nom d'utilisateur:</label>
  <input type="text" id="username" name="username" required>
  <br>
  <label for="password">Mot de passe:</label>
  <input type="password" id="password" name="password" required>
  <br>
  <input type="submit" value="S'inscrire">
</form>

</body>
</html>
