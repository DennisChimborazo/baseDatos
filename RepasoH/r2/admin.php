<?php
if (isset($_POST['user'])) {
    $con=mysqli_connect("localhost","root","","prueba");
    $user=$_POST['user'];
    $pass=$_POST['pass'];
    $sql="INSERT INTO usuario (nomUser,passUser,rolUser) VALUES ('$user','$pass','user')";
    if ($con->query($sql)==TRUE) {
        echo "se guardo ";
    }else {
        echo "verifica tus datos ";
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

    <h2>Crear usuarios</h2>
    <form action="" method="post">
        <h3>usuario</h3>
        <input type="text" name ="user">
        <h3>contrasenia</h3>
        <input type="text" name="pass">
        <br>
        <button> crear</button>
    </form>
    <h2> Resultados</h2>
    <form action="resultados.php">
        <button> Observar</button>
    </form>
</body>
</html>