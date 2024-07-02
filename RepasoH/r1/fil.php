
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Tabla HTML</title>
</head>
<body>
    <form method="post">
        <input type="text" name="cedula" id="cedula">
        <button type="submit">Buscar</button>
    </form>
    <hr>
    <table border="1">
        <caption>Esta es mi tabla</caption>
        <thead>
            <tr>
                <th>Cédula</th>
                <th>Nombre</th>
                <th>Apellido</th>
                <th>Curso</th>
            </tr>
        </thead>
        <tbody>
            <?php 
            include "filtrado.php";
                foreach ($resultado as $fila) {
                    ?>
                    <tr>
                    <th><?php echo $fila["estCedula"]; ?></th>
                    <th><?php echo $fila["estNombre"]; ?></th>
                    <th><?php echo $fila["estApellido"]; ?></th>
                    <th><?php echo $fila["Nombre"]; ?></th>
                </tr>
            <?php 
                }
            ?>
        </tbody>
    </table>
</body>
</html>