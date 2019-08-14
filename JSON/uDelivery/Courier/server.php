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
        @$nim = $_GET['cour_email'];
        @$password = $_GET['cour_password'];
        @$status = "";
        $query = mysql_query("SELECT *FROM tb_courier WHERE cour_email
        	='$nim' AND cour_password='$password'") or die(mysql_error());
        $Total=mysql_num_rows($query);
        $data=array();
        $data=mysql_fetch_assoc($query);
        if($Total>0)
        {
            $status = array(
                'status' => 1, 
                 'stat'=>'data exist',
                  'name'=>$data['cour_name'],
                  'longtitude'=>$data['cour_pos_longtitude'],
                  'latitude'=>$data['cour_pos_latitude'],
                  'image'=>$data['cour_image'],
                  'vehicle_name'=>$data['cour_vehicle_name'],
                  'vehicle_number'=>$data['cour_vehicle_number'],
                  'type'=>$data['cour_vehicle_type']
             );
        }
        else
        {
            $status = array(
                'status' => 0, 
                      'stat'=>'data not exist'
                
             );
        }
         echo "[" . json_encode($status) . "]";    
            break;
  case "get_location":
        @$email=$_GET['cour_email']; 
        @$query = mysql_query("SELECT cour_pos_longtitude,cour_pos_latitude FROM tb_courier WHERE cour_email='$email'") or die(mysql_error());
        @$row=mysql_fetch_array($query);
    		$status = array(
    		                	'latitude' => $row['cour_pos_longtitude'], 
    		                      'longtitude'=>$row['cour_pos_latitude']		                
    		             );
            
             echo "[" . json_encode($status) . "]";
   		 break;
  case "update_location":
            @$email = $_GET['email'];
            @$lat = $_GET['latitude'];
            @$long = $_GET['longtitude'];

            $query = mysql_query("UPDATE tb_courier SET cour_pos_longtitude='$long',cour_pos_latitude='$lat' WHERE cour_email='$email'");
            $query1 = mysql_query("UPDATE tb_courier SET cour_state='Available' WHERE cour_email='$email'");
            @$query2 = mysql_query("SELECT cour_order_handle,cour_phone,cour_name,cour_image FROM tb_courier WHERE cour_email='$email'") or die(mysql_error());
            @$row=mysql_fetch_array($query2);

            if($row['cour_order_handle']=="N/A")
            {
                   $status = array(
                          'result'=>0                    
                     );
            }
            else{
                  $status = array(
                          'result'=>1,
                          'cour_order_handle'=>$row['cour_order_handle'],
                     );
            }
            
            echo "[" . json_encode($status) . "]";
            break;

  case 'cancel_order':
            @$o_id=$_GET['order_id'];
            @$cour_email=$_GET['cour_email'];
            
            @$query2 = mysql_query("UPDATE tb_order SET or_status='Canceled by Courier' WHERE or_id='$o_id';") or die(mysql_error());
            @$query2 = mysql_query("UPDATE tb_courier SET cour_order_handle='N/A' WHERE cour_email='$cour_email';") or die(mysql_error());
            
                       $status = array(
                          'status'=>'Success'
                          
                     );
            echo "[" . json_encode($status) . "]";
              break;
  case 'confirm_order':
            @$o_id=$_GET['order_id'];
            @$cour_email=$_GET['cour_email'];
            @$query2 = mysql_query("UPDATE tb_order SET or_status='Inprogress' WHERE or_id='$o_id';") or die(mysql_error());
            @$query2 = mysql_query("UPDATE tb_courier SET cour_state='Booked' WHERE cour_email='$cour_email';") or die(mysql_error());
            
                       $status = array(
                          'status'=>'Success'
                          
                     );
            echo "[" . json_encode($status) . "]";
              break;
  case 'set_offline':
            @$cour_email=$_GET['cour_email'];
            @$query2 = mysql_query("UPDATE tb_courier SET cour_state='Offline' WHERE cour_email='$cour_email';") or die(mysql_error());
            
            $status = array(
                          'status'=>'Success'        
                     );
            echo "[" . json_encode($status) . "]";
    break;
  case 'set_online':
            @$cour_email=$_GET['cour_email'];
            @$query2 = mysql_query("UPDATE tb_courier SET cour_state='Available' WHERE cour_email='$cour_email';") or die(mysql_error());
            
            $status = array(
                          'status'=>'Success'        
                     );
            echo "[" . json_encode($status) . "]";
    break;
  case 'get_order_data':
            @$or_id=$_GET['order_id'];
            @$query = mysql_query("SELECT *from tb_order WHERE or_id='$or_id';") or die(mysql_error());
            @$row=mysql_fetch_array($query);
            
            $nim=$row['cust_nim'];

            @$query2 = mysql_query("SELECT cust_name,cust_Image,cust_phone from tb_customer WHERE cust_nim='$nim';") or die(mysql_error());
            @$row2=mysql_fetch_array($query2);

            $vend=$row['vend_id'];
            // echo "SELECT vend_name from tb_vendor WHERE vend_id='$vend';";
            @$query3 = mysql_query("SELECT vend_name,vend_phone from tb_vendor WHERE vend_id='$vend';") or die(mysql_error());
            @$row3=mysql_fetch_array($query3);

            @$query4 = mysql_query("SELECT sum(trans_quantity) as qty, sum(trans_price) as payment from tb_transaction  WHERE or_id='$order_id';") or die(mysql_error());
            @$row4=mysql_fetch_array($query4);
          
          $status = array( 
                  'Vend_phone'=>$row3['vend_phone'],
                  'Cust_phone'=>$row2['cust_phone'],
                  'cust_Image'=>$row2['cust_Image'],
                  'cust_name'=>$row2['cust_name'],
                  'vend_name'=>$row3['vend_name'],
                  'or_id'=>$row['or_id'],
                  'cour_email'=>$row['cour_email'],
                  'cust_nim'=>$row['cust_nim'],
                  'vend_id'=>$row['vend_id'],
                  'vend_name'=>$row['vend_name'],
                  'vend_address'=>$row['vend_address'],
                  'pickup_lat'=>$row['pickup_lat'],
                  'pickup_long'=>$row['pickup_long'],
                  'vend_pos_lat'=>$row['vend_pos_lat'],
                  'vend_pos_long'=>$row['vend_pos_long'],
                  'or_distance'=>$row['or_distance'],
                  'or_status'=>$row['or_status'],
                  'qty'=>$row4['qty'],
                  'payment'=>$row4['payment']

          );
          echo "[" . json_encode($status) . "]";
              break;
  case "change_password":
            @$email = $_GET['email'];
            @$password = $_GET['password'];
            $query = mysql_query("UPDATE tb_courier SET password='$password' WHERE cour_email='$email'");
            if ($query) {
                echo "Password updated";
            } else {
                echo mysql_error();
            }
            break;
  case 'get_trans_data':
    	@$nim=$_GET['nim'];
    	@$query = mysql_query("SELECT tb_transaction.trans_id, tb_transaction.trans_date,tb_transaction.trans_status,tb_customer.cust_name,tb_customer.cust_Image FROM tb_transaction left join tb_customer on(tb_transaction.cust_nim=tb_customer.cust_nim) WHERE tb_transaction.cour_email='$nim' group by tb_transaction.trans_id") or die(mysql_error());
    	$data_array = array();
    	while ($data = mysql_fetch_assoc($query)) {
            $data_array[] = $data;
        } 
        echo json_encode($data_array);
    	break;
  case 'set_onof':
    	@$op=$_GET['status'];
    	@$email=$_GET['email'];
	    	if($op=="on")
	    	{
	    		@$query = mysql_query("UPDATE tb_courier SET cour_isOnline=1 ") or die(mysql_error());   
	    	}
	    	else{
	    		@$query = mysql_query("UPDATE tb_courier SET cour_isOnline=0 ") or die(mysql_error());   
	    	}
    	break;
  }
?>