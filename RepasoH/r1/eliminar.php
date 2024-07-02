<?php 
if ($_SERVER["REQUEST_METHOD"] == "POST" && isset($_POST['cedula'])) {
$id=$_POST['cedula'];
$con=mysqli_connect("localhost","root","","cuarto");
$sql="DELETE FROM estudiantes WHERE estCedula='$id'";
if ($con->query($sql)==true) {
    echo "se elimino correctamente";
}else {
    echo "verifica tus datos";
}
}


?>
