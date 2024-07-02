<?php ?>
<?php 
session_start();
if (isset($_POST['user'])) {
    $con=mysqli_connect("localhost","root","","prueba");
    $user=$_POST['user'];
    $pass=$_POST['pass'];
    $sql="SELECT * FROM usuario WHERE nomUser='$user' AND passUser='$pass'";
    $res=$con->query($sql);
    if ($res->num_rows>0) {
        $usuario = $res->fetch_assoc(); 
        $rol = $usuario['rolUser'];
        $iduser = $usuario['idUser'];
        if ($rol=="admin") {
            header("location: admin.php");
        }else {
            $sql2="SELECT * FROM encuesta WHERE idUser='$iduser'";
            $res2=$con->query($sql2);
            if ($res2->num_rows>0) {
                echo "este usuario ya cuenta con la encuenta realizada";
            } else {
            $_SESSION['idUser'] = $iduser;
            header("location: encuesta.php");
            }

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
    <form action="" method="post">
      <h2>LOGIN</h2>
      <h3>usuario</h3>
      <input type="text" name="user">
      <br>
      <h3>pass</h3>
      <input type="text" name="pass">
      <br>
      <button> ingreso</button>
    </form>
</body>
</html>