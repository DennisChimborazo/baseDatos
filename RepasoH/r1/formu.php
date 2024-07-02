
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
</head>
<?php
include "busca.php";
?>
<body>
  <h1>Estudiantes</h1>

  <table>
    <thead>
        <tr>
            <th>Cedula</th>
            <th>Nombre</th>
            <th>Apellido</th>
            <th>Curso</th>
        </tr>
    </thead>
    <tbody>
        <tr>
            <th><?php echo $listaEstudiantes["estCedula"]; ?></th>
            <th><?php echo $listaEstudiantes["estNombre"]; ?></th>
            <th><?php echo $listaEstudiantes["estApellido"]; ?></th>
            <th><?php echo $listaEstudiantes["curId"]; ?></th>
        </tr>
    </tbody>
  </table>
    
</body>
</html>
