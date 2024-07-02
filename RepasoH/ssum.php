$res=0;
 $parIn="";
 $fac=0;

if ($_SERVER["REQUEST_METHOD"]=="POST") {
    $num1 = $_POST['num1'];
    $num2 = $_POST['num2'];
    $res = $num1 + $num2;
    if ($res%2==0) {
 $parIn="Par";
        
    }else {
        $parIn="Inpar";

    }
    $fac=1;
    for ($i=1; $i <= $res; $i++) { 
        $fac*=$i;
    }

}
