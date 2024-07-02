
<?php
session_start();
$iduser = $_SESSION['idUser'];
echo "El ID del usuario es: " . $iduser;
if ($_SERVER["REQUEST_METHOD"] == "POST") {
    $respuesta1 = $_POST['respuesta1'];
    $respuesta2 = $_POST['respuesta2'];
    $respuesta3 = $_POST['respuesta3'];
    $con=mysqli_connect("localhost","root","","prueba");
    $sql=("INSERT INTO encuesta (pre1, pre2, pre3,idUser) VALUES ('$respuesta1', '$respuesta2', '$respuesta3','$iduser')");
    if ($con->query($sql)==true) {
        echo "<script>alert('Se guardó la encuesta correctamente.');</script>";
        echo "<script>window.location.href = 'login.php';</script>";
    } else {
        echo "Error al guardar la encuesta: " . $stmt->error;
    }
}
?>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Encuesta</title>
</head>
<body>
    <form method="post" action="">
       <h2>Pregunta 1:</h2>
        <input type="radio" name="respuesta1" value="si">
        <label>Si</label><br>
        <input type="radio" name="respuesta1" value="no">
        <label>No</label><br>
        <br>
        <h2>Pregunta 2:</h2>
        <input type="radio" name="respuesta2" value="si">
        <label>Si</label><br>
        <input type="radio" name="respuesta2" value="no">
        <label>No</label><br>
        <br>
        <h2>Pregunta 3:</h2>
        <input type="radio" name="respuesta3" value="si">
        <label>Si</label><br>
        <input type="radio" name="respuesta3" value="no">
        <label>No</label><br>
        <br>
        <button type="submit">Enviar</button>
    </form>
</body>
</html>
