<?php
$nom=$_POST["nombre"];
$ape=$_POST["apellido"];
$conn= mysqli_connect("localhost","root","","cuarto");
$sql = "SELECT * FROM usuarios WHERE user = '$nom' AND pass = '$ape'";
$resultado=$conn->query($sql);
if ($resultado->num_rows > 0) {
    header("Location: fil.php");
    exit; 
}else{
    echo "usuaruio o contraseña incorrecta";
}
?>