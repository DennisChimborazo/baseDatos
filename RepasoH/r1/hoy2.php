<?php 
$con=mysqli_connect("localhost","root","","cuarto");
$sql="SELECT * FROM cursos";
$resCons=$con->query($sql);
while ($fila=$resCons->fetch_array()) {
   $lista[]=$fila;
}
?>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
</head>
<body>
    <table>
        <thead>
            <tr>
                <th>NOMBRE </th>
                <th>ID</th>
            </tr>
        </thead>
        <tbody>

            <?php foreach ($lista as $fila): ?>
            <tr>
                <td><?php echo $fila['curId']; ?></td>
                <td><?php echo $fila['nombre']; ?></td>
            </tr>
            <?php endforeach; ?>

        </tbody>
    </table>
</body>
</html>