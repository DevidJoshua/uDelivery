<?php

$server = "localhost";
$username = "root";
$password = "";
$database = "db_udelivery";

mysql_connect($server, $username, $password) or die("<h1>Connection to database is error : </h1>" . mysql_error());
mysql_select_db($database) or die("<h1>Connection error : </h1>" . mysql_error());

@$operation = $_GET['operation'];
switch ($operation) {
	case "login":
       
        @$nim = $_GET['cust_nim'];
        @$password = $_GET['cust_password'];
        @$status = "";
        $query = mysql_query("SELECT *FROM tb_customer WHERE cust_nim
        	='$nim' AND cust_password='$password'") or die(mysql_error());
        $Total=mysql_num_rows($query);
        $data=array();
        $data=mysql_fetch_assoc($query);
        if($Total>0)
        {
            $status = array(
                'status' => 1, 
                'stat'=>'data exist',
                'name'=>$data['cust_name'],
                'image'=>$data['cust_image']    
             );
            session_start();
            @$_SESSION['nim']=$nim;
        }
        else
        {
            $status = array(
                      'status' => 0
             );
        }
        
         echo "[" . json_encode($status) . "]";    
            break;
    case "get_shop_distance":
        @$o_latitude=$_GET['o_latitude']; 
        @$o_longitude=$_GET['o_longitude'];
        @$query = mysql_query("SELECT (CALCULATE_DISTANCE($o_latitude,$o_longitude,vend_pos_latitude,vend_pos_longtitude)*1000) AS distance ") or die(mysql_error());
        $data_array = array();
        while ($data = mysql_fetch_assoc($query)) {
            $data_array[] = $data;
        }
        echo json_encode($data_array);
    break;
    case "get_available_courier":
        $max_radius=400;
        @$my_latitude=$_GET['my_latitude']; 
        @$my_longitude=$_GET['my_longitude'];
        @$query = mysql_query("SELECT cour_email,cour_phone,cour_image,cour_name,cour_pos_latitude,cour_pos_longtitude,cour_vehicle_type,cour_vehicle_number,cour_vehicle_name,(CALCULATE_DISTANCE($my_latitude,$my_longitude,cour_pos_latitude,cour_pos_longtitude)*1000) as distance from tb_courier where ((CALCULATE_DISTANCE($my_latitude,$my_longitude,cour_pos_latitude,cour_pos_longtitude)*1000)<=$max_radius) AND cour_state='Available' order by rand() asc LIMIT 1 ;") or die(mysql_error());
        $data_array = array();
        $Total=mysql_num_rows($query);
        if($Total>0){
            // while ($data = mysql_fetch_assoc($query)) {
            // $data_array[] = $data;
            // }
            $data=mysql_fetch_assoc($query);
            
               $result = array(
                    'result' => 1,
                    'cour_email'=>$data['cour_email'],
                    'cour_distance'=>round($data['distance']),
                    'cour_image'=>$data['cour_image'],
                    'cour_phone'=>round($data['cour_phone']),
                    'cour_name'=>$data['cour_name'],
                    'cour_pos_latitude'=>$data['cour_pos_latitude'],
                    'cour_pos_longtitude'=>$data['cour_pos_longtitude'],
                    'cour_vehicle_type'=>$data['cour_vehicle_type'],
                    'cour_vehicle_number'=>$data['cour_vehicle_number'],
                    'cour_vehicle_name'=>$data['cour_vehicle_name']
             );    
        }
        else{
               $result = array(
                    'result' => 0, 
             );    
        }
        echo "[" . json_encode($result) . "]";
    break;
        case "get_couriers":
        $max_radius=1000;
        @$my_latitude=$_GET['my_latitude']; 
        @$my_longitude=$_GET['my_longitude'];
        @$query = mysql_query("SELECT cour_image,cour_name,cour_pos_latitude,cour_pos_longtitude,cour_vehicle_type,cour_vehicle_number,cour_vehicle_name,(CALCULATE_DISTANCE($my_latitude,$my_longitude,cour_pos_latitude,cour_pos_longtitude)*1000) as distance from tb_courier where ((CALCULATE_DISTANCE($my_latitude,$my_longitude,cour_pos_latitude,cour_pos_longtitude)*1000)<=$max_radius) AND cour_state='Available' ;") or die(mysql_error());
        $data_array = array();
        $Total=mysql_num_rows($query);
        if($Total>0){
            // while ($data = mysql_fetch_assoc($query)) {
            // $data_array[] = $data;
            // }
            $data=mysql_fetch_assoc($query);
               $result = array(
                    'result' => 1,
                    'cour_distance'=>round($data['distance']),
                    'cour_image'=>$data['cour_image'],
                    'cour_name'=>$data['cour_name'],
                    'cour_pos_latitude'=>$data['cour_pos_latitude'],
                    'cour_pos_longtitude'=>$data['cour_pos_longtitude'],
                    'cour_vehicle_type'=>$data['cour_vehicle_type'],
                    'cour_vehicle_number'=>$data['cour_vehicle_number'],
                    'cour_vehicle_name'=>$data['cour_vehicle_name']
             );    
        }
        else{
               $result = array(
                    'result' => 0, 
             );    
        }
        echo "[" . json_encode($result) . "]";
    break;
  case 'check_order_status':
            @$or_id=$_GET['order_id'];
            @$query = mysql_query("SELECT or_status FROM tb_order WHERE or_id='$or_id'") or die(mysql_error());
            @$row=mysql_fetch_array($query);              

            $result = array(
                    'result' => $row['or_status'] 
             );    
        
        echo "[" . json_encode($result) . "]";  
              break;
    case 'place_order':
        @$vend_id=$_GET['vend_id'];
        @$cour_email=$_GET['cour_email'];  
        @$cust_nim=$_GET['cust_nim']; 
        @$pickup_lat=$_GET['pickup_lat']; 
        @$pickup_long=$_GET['pickup_long'];
        @$vend_pos_lat=$_GET['vend_pos_lat']; 
        @$vend_pos_long=$_GET['vend_pos_long'];
        @$vend_name=$_GET['vend_name'];  
        @$vend_address=$_GET['vend_address'];  
        @$or_distance=$_GET['or_distance'];  
        @$status="Uncheck";
        // echo "INSERT INTO tb_order VALUES (null,'$cour_email','$cust_nim','$vend_id','$vend_name','$vend_address','$pickup_lat','$pickup_long','$vend_pos_lat','$vend_pos_long','$or_distance','$status');";
        $characters = '0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ';
        $charactersLength = strlen($characters);
        $order_id = '';
        for ($i = 0; $i < 7; $i++) {
            $order_id .= $characters[rand(0, $charactersLength - 1)];
        }
        @$query = mysql_query("INSERT INTO tb_order VALUES ('$order_id','$cour_email','$cust_nim','$vend_id','$vend_name','$vend_address','$pickup_lat','$pickup_long','$vend_pos_lat','$vend_pos_long','$or_distance','$status');") or die(mysql_error());
        // echo "UPDATE tb_courier SET cour_order_handle='$order_id',cour_state='Booked' WHERE cour_email='$cour_email'";
        @$query = mysql_query("UPDATE tb_courier SET cour_order_handle='$order_id',cour_state='Booked' WHERE cour_email='$cour_email'") or die(mysql_error());
        $status = array(
                            'order_id'=>$order_id,
                          'status'=>'Success'
                          
                     );
            echo "[" . json_encode($status) . "]";
        break;
    case "get_nearest_vendors":
        $max_radius=1000.00; //Meter

        @$o_latitude=$_GET['o_latitude']; 
        @$o_longitude=$_GET['o_longitude'];
        // @$d_latitude=$_GET['d_latitude']; 
        // @$d_longitude=$_GET['d_longitude'];
        // echo $o_latitude.",".$o_longitude;
        @$query = mysql_query("SELECT vend_pos_latitude,vend_pos_longtitude,vend_phone,vend_address,vend_id,vend_name,vend_profile_image,round((CALCULATE_DISTANCE($o_latitude,$o_longitude,vend_pos_latitude,vend_pos_longtitude)*1000)) AS distance from tb_vendor where (CALCULATE_DISTANCE($o_latitude,$o_longitude,vend_pos_latitude,vend_pos_longtitude)*1000)<=$max_radius") or die(mysql_error());
        $data_array = array();
        $vendor_array= array();
        $selected_vendor=array();
        while ($data = mysql_fetch_assoc($query)) {
            $data_array[]=$data;
            // if(hitungJarak($o_latitude,$o_longitude,$data['vend_pos_latitude'],$data['vend_pos_longtitude'])<=$max_radius)
            // {
            // $data_array[] = $data;
            //     array_push($selected_vendor, $data_array);    // print_r($selected_vendors);
            // }
            // // $vendor_array[]=$selected_vendor;
            // print_r($selected_vendor);
        }
            echo json_encode($data_array);
    break;
    case "get_products":
        @$vend_id=$_GET['vend_id']; 
        @$query = mysql_query("SELECT vend_id,product_id,product_price,product_image,product_name,product_caption FROM tb_product WHERE vend_id='$vend_id'") or die(mysql_error());
        $data_array = array();
        while ($data = mysql_fetch_assoc($query)) {
            $data_array[] = $data;
        }
        echo json_encode($data_array);
    break;
    case 'insert_products':
  $or_id=$_GET['or_id'];         
  $product_id=$_GET['product_id'];     
  $product_name=$_GET['product_name'];   
  $cust_nim=$_GET['cust_nim'];       
  $trans_price=$_GET['trans_price'];    
  $trans_quantity=$_GET['trans_quantity'];

  @$query = mysql_query("INSERT INTO tb_transaction VALUES (null,'$or_id','$product_id','$product_name','$cust_nim','datetime()','Waiting','$trans_quantity','$trans_price');") or die(mysql_error());
  $result = array(
                    'result' => 'success', 
             );    
        echo "[" . json_encode($result) . "]";
        break;
    case 'check_order_status':
        @$query = mysql_query("INSERT INTO tb_order VALUES (null,'$cour_email','$cust_nim','$vend_id','$vend_name','$vend_address','$pickup_lat','$pickup_long','$vend_pos_lat','$vend_pos_long','$or_distance','$status');") or die(mysql_error());
        
        @$query = mysql_query("UPDATE tb_courier SET cour_order_handle='$or_distance' WHERE cour_email='$cour_email'") or die(mysql_error());
        
        $result = array(
                    'result' => 0, 
             );    
        echo "[" . json_encode($result) . "]";
        break;
    default:
        break;
}

    // function hitungJarak($lokasi1_lat, $lokasi1_long, $lokasi2_lat, $lokasi2_long, $unit = 'm',$desimal=2) {
    //  $derajat = rad2deg(acos((sin(deg2rad($lokasi1_lat))*sin(deg2rad($lokasi2_lat))) + (cos(deg2rad($lokasi1_lat))*cos(deg2rad($lokasi2_lat))*cos(deg2rad($lokasi1_long-$lokasi2_long)))));

    //    switch($unit) {
    //   case 'km':
    //    $jarak = $derajat * 6371; // 1 derajat = 111.13384 km, berdasarkan diameter rata-rata bumi (12,735 km)
    //    break;
         
    //   case 'm':
    //    $jarak = ($derajat * 6371)*1000;
    //    break;
    //   case 'mi':
    //    $jarak = $derajat * 69.05482; // 1 derajat = 69.05482 miles(mil), berdasarkan diameter rata-rata bumi (7,913.1 miles)
    //    break;
    //   case 'nmi':
    //    $jarak =  $derajat * 59.97662; // 1 derajat = 59.97662 nautic miles(mil laut), berdasarkan diameter rata-rata bumi (6,876.3 nautical miles)
    //  }
    //  return round($jarak,$desimal);
    // }

?>
