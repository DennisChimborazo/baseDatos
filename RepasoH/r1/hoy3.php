<?php
$con=mysqli_connect("localhost","root","","cuarto");
$lista = [];

if (isset($_POST['cedula'])) {
    $id=$_POST['cedula'];
    $sql="SELECT e.estCedula, e.estNombre, e.estApellido, e.estTelefono, estDireccion, c.nombre
     FROM estudiantes e 
     INNER JOIN cursos c ON c.curId=e.curid 
     WHERE e.estCedula='$id'";
    $resuConsulta=$con->query($sql);
    while ($fila=$resuConsulta->fetch_array()) {
        $lista [] = $fila;
    }
}else{
    if ( isset($_POST['ceduladele'])) {
        $id=$_POST['ceduladele'];
        $con=mysqli_connect("localhost","root","","cuarto");
        $sql="DELETE FROM estudiantes WHERE estCedula='$id'";
        if ($con->query($sql)==true) {
            echo "se elimino correctamente";
        }else {
            echo "verifica tus datos";
        }
        }
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
    <h2> Buscar estudiante </h2>
    <form action="" method="post">
        <br>
        <input type="text" name="cedula">
        <br>
        <br>
        <button> buscar</button>
    </form>
    <br>
    <form action="" method="post">
        <h3>eliminar estudiante</h3>
    
        <input type="text"name="ceduladele">
        <input type="radio"name="f">


        <br>
        <br>

        <button>eliminar</button>
    </form>
    <br>
    <br>
    <table border='1'>
        <thead>
            <tr>
                <th>Cedula</th>
                <th>Nombre</th>
                <th>Apellido</th>
                <th>Telefono</th>
                <th>Direccion</th>
                <th>Curso</th>


            </tr>
        </thead>
        <tbody>
        <?php foreach($lista as $fila): ?>
           <tr>
            <td> <?php echo $fila['estCedula']?></td>
            <td> <?php echo $fila['estNombre']?></td>
            <td> <?php echo $fila['estApellido']?></td>
            <td> <?php echo $fila['estTelefono']?></td>
            <td> <?php echo $fila['estDireccion']?></td>
            <td> <?php echo $fila['nombre']?></td>
           </tr>
        <?php endforeach; ?>

        </tbody>

    </table>
    <br>
    <form action="hoy4.php">
      <button> Guardar nuevo estudiante </button>
    </form>



</body>
</html>

<?php ?>
