<?php
$con=mysqli_connect("localhost","root","","cuarto");
if ($_SERVER["REQUEST_METHOD"] == "POST") {
    $user=$_POST['user'];
    $pass=$_POST['pass'];
    $sql="SELECT * FROM usuarios WHERE user='$user' AND pass='$pass'";
    $resConsulta=$con->query($sql);
    if ($resConsulta->num_rows>0) {
        header("location: hoy3.php");
        exit;
    }else {
        echo  "Usuario y contrasenia incorrecta";
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
    <h2>LOGIN</h2>
    <form method="POST">
        <h3>usuario</h3>
        <input type="text"  name="user">
        <br>
        <h3>contrasenia</h3>
        <input type="text" name="pass">
        <br>
        <br>
        <button> ingresar</button>
    </form>
    
</body>
</html>