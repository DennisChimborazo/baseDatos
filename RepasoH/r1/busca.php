<?php
$conn= mysqli_connect("localhost","root","","cuarto");
$sql = "SELECT e.estCedula, e.estNombre,e.estApellido, c.curId FROM estudiantes e JOIN cursos c ON e.curId = c.curId ";
$resultado=$conn->query($sql);
while ($fila=$resultado->fetch_array()) {
    $listaEstudiantes[] = $fila;
}

?>
  