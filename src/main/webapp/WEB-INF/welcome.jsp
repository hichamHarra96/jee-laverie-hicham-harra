<%@ page import="org.eclipse.jakarta.model.Machine" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.ArrayList" %>
<%@ page pageEncoding="UTF-8" %>
<%
  List<Machine> machines = (List<Machine>) request.getAttribute("machines");
  if (machines == null) {
    machines = new ArrayList<>();
  }
%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Bienvenue</title>
  <style>
    body {
      font-family: Arial, sans-serif;
      background-color: #f4f4f4;
      margin: 0;
      padding: 20px;
    }

    h1 {
      color: #333;
    }
    h2 {
        color: #333;
    }

    p {
      color: #666;
    }

    .machine {
      border: 2px solid #ccc;
      padding: 10px;
      margin-bottom: 10px;
      background-color: #fff;
      display: inline-block;
      width: 300px;
      height: 150px;
      box-sizing: border-box;
    }

    .machine-name {
      font-weight: bold;
      font-size: 18px;
      margin-bottom: 5px;
    }

    .button-container {
      display: flex;
      gap: 5px;
      margin-top: 10px;
    }

    .button-container button {
      border: none;
      padding: 5px;
      background-color: #f2f2f2;
      cursor: pointer;
      transition: background-color 0.3s ease;
    }

    .button-container button:hover {
      background-color: #e0e0e0;
    }

    .button-container button.delete {
      background-color: #f44336;
      color: #fff;
    }

    .button-container button.put {
      background-color: #4caf50;
      color: #fff;
    }

    .button-container button:hover.delete {
      background-color: #d32f2f;
    }

    .button-container button:hover.put {
      background-color: #45a049;
    }

    .add-machine-form {
      margin-top: 20px;
    }

    .add-machine-form label {
      display: block;
      font-weight: bold;
      margin-bottom: 5px;
    }

    .add-machine-form input[type="text"] {
      padding: 5px;
      width: 200px;
      border: 1px solid #ccc;
    }

    .add-machine-form button[type="submit"] {
      padding: 5px 10px;
      border: none;
      background-color: #4caf50;
      color: #fff;
      cursor: pointer;
    }

    .add-machine-form button[type="submit"]:hover {
      background-color: #45a049;
    }

    .logout-form {
      margin-top: 20px;
    }

    .logout-form button[type="submit"] {
      padding: 5px 10px;
      border: none;
      background-color: #f44336;
      color: #fff;
      cursor: pointer;
    }

    .logout-form button[type="submit"]:hover {
      background-color: #d32f2f;
    }

    h4 {
        text-align: center;
        font-size: 24px;
        font-weight: bold;
        margin: 20px 0;
    }
  </style>
</head>
<body>
<h4>Laverie Admin</h4>
<h2>Bienvenue !</h2>
<p>Vous êtes connecté avec succès.</p>

<h1>List of Machines</h1>
<% for (Machine machine : machines) { %>
<div class="machine">
  <p class="machine-name"><%= machine.getNumeroMachine() %></p>
  <p>ID: <%= machine.getId() %></p>
  <p>Status: <%= machine.getStatus() %></p>
  <div class="button-container">
    <form action="WelcomeServlet" method="post">
      <input type="hidden" name="id" value="<%= machine.getId() %>">
      <input type="hidden" name="action" value="delete">
      <button type="submit" class="delete">Delete</button>
    </form>
    <form action="WelcomeServlet" method="post">
      <input type="hidden" name="id" value="<%= machine.getId() %>">
      <input type="hidden" name="action" value="reserve">
      <button type="submit" class="put">Reserver</button>
    </form>
    <form action="WelcomeServlet" method="post">
      <input type="hidden" name="id" value="<%= machine.getId() %>">
      <input type="hidden" name="action" value="liberer">
      <button type="submit" class="put">Libérer</button>
    </form>
  </div>
</div>
<% } %>

<div class="add-machine-form">
  <h1>Add Machine</h1>
  <form action="WelcomeServlet" method="post">
    <label for="name">Name:</label>
    <input type="text" id="name" name="name" required>
    <button type="submit" name="action" value="add">Add Machine</button>
  </form>
</div>

<div class="logout-form">
  <form action="LogoutServlet" method="post">
    <button type="submit">Logout</button>
  </form>
</div>

</body>
</html>
