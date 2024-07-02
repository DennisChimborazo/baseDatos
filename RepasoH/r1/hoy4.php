<?php 
$con=mysqli_connect("localhost","root","","cuarto");
if (isset($_POST["ced"])) {
    $ced=$_POST["ced"];
    $nom=$_POST["nom"];
    $ape=$_POST["ape"];
    $dire=$_POST["dire"];
    $tel=$_POST["tel"];
    $cur=$_POST["cur"];

 $sql="INSERT INTO estudiantes(estCedula,estNombre,estApellido,estTelefono,estDireccion,curId) values ('$ced','$nom','$ape','$tel','$dire','$cur')";
  if ($con->query($sql)==true) {
    echo "se guardo exitosamente";
  }else {
    echo "verifica tus datos";
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
    <h2> Guardar estudiantes </h2>
    <form action="" method="post">
        <h3> cedula </h3>
        <input type="text" name="ced">
        <br>
        <h3> nombre </h3>
        <input type="text" name="nom">
        <br>
        <h3> Apellido </h3>
        <input type="text" name="ape">
        <br>
        <h3> telefono </h3>
        <input type="text" name="tel">
        <br>
        <h3> direccion </h3>
        <input type="text" name="dire">
        <br>
        <h3> codigo de curso </h3>
        <input type="text" name="cur">
        <br>
        <br>
        <button>Guardar</button>
        </form>
        <br>
        <form action="hoy3.php">
            <button>volver</button>
        </form>

</body>
</html>

<?php ?>
