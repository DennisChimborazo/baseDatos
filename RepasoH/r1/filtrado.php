<?php
$con= mysqli_connect("localhost","root","","cuarto");
$resultado = array();

    if (isset($_POST['cedula'])) {
        $cedula = $_POST['cedula'];
        $sqlSelect = "SELECT 
            estudiantes.estCedula,
            estudiantes.estNombre,
            estudiantes.estApellido,
            estudiantes.estTelefono,
            estudiantes.estDireccion,
            cursos.Nombre 
        FROM estudiantes
        JOIN cursos ON estudiantes.curId = cursos.curId
        WHERE estudiantes.estCedula ='$cedula'";
        $respuesta = $con->query($sqlSelect);
        while ($fila = $respuesta->fetch_array()) {
            array_push($resultado, $fila);
        }
    } 
?>