<?php 
$lista=[];
$con=mysqli_connect("localhost","root","","prueba");
$sql="SELECT e.pre1, e.pre2, e.pre3, u.nomUser FROM encuesta e INNER JOIN usuario u ON e.idUser=u.idUser";
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
    <h2>RESULTADOS</h2>
    <table>
        <thead>
            <tr>
                <th>Nombre Persona</th>
                <th>Pregunta 1</th>
                <th>Pregunta 2</th>
                <th>Pregunta 3</th>
            </tr>
        </thead>
        <tbody>
            <?php foreach ($lista as $fila):?>
                <tr>
                    <td><?php echo $fila['nomUser'];?> </td>
                    <td><?php echo $fila['pre1'];?> </td>
                    <td><?php echo $fila['pre2'];?> </td>
                    <td><?php echo $fila['pre3'];?> </td>
                </tr>
            <?php endforeach;?>


        </tbody>
    </table>
    
</body>
</html>