<?php
 defined('BASEPATH') OR exit('No direct script access allowed'); 
 
 class Dashboard extends CI_Controller {
     function __construct(){
         parent::__construct();
		 $this->load->library(array('form_validation')); // library untuk validasi data ketika pengiriman dengan metode post atau get
         $this->load->helper(array('url','form'));	//library untuk load url atau link yg akan di gunakan dan juga form digunakan untuk pengiriman ke database
         $this->load->model('m_account'); 
     }
 
     //Load Halaman dashboard
     public function index() {							//awal ketika website di load 
         $this->load->view('welcome_message.php');
     }
	
	public function getAdminAccount($user, $password)		//fungsi untuk login exmpl: lungproject.000webhostapp.com/index.php/dashboard/getAdminAccount/user/password
	{
		//disimpan di object dengan nama data isi object nama/password
		$data['nama'] = $user;	
		$data['password'] = $password;
		
		$dataAdmin = $this->m_account->collectDataAdmin($data);
		echo $dataAdmin;
	
	}
	public function addUser()						//nambah user yg akan di ukur dari android studio
	{
		
		$data['nama'] = $this->input->post('nama');				//didapat dari android studio dengan mengirimkan data post dengan variable nama
		$data['gender'] = $this->input->post('gender');
		$data['usia'] = $this->input->post('usia');
		$data['tinggi'] = $this->input->post('tinggi');
		$data['berat'] = $this->input->post('berat');
		/*
		echo $data['nama']; echo " ";
		echo $data['gender']; echo " ";
		echo $data['tglLahir']; echo " ";
		echo $data['alamat'];
		*/
		$this->m_account->addUser($data);
			
		
	}
	public function fromArdu($dataKe, $dataKpa) //tekanan MPX lungproject.000webhostapp.com/index.php/dashboard/fromArdu/dataKe/nilai
	{
		
		$data['dataKe'] = $dataKe;
		$data['kpa'] = $dataKpa;
		$this->m_account->updateSensor($data);
			

	}
	public function getDatabaseSensor()			//digunakan android studio untuk membaca data tekanan sekarang dari user
	{
		$returnData = $this->m_account->collectDataSensor();
		
		$response = ['user' => $returnData];	//encode ke json agar mudah untuk pembacaan di android studio 
		echo json_encode($response);
				
	}
	
	public function getDatabase()
	{
		$returnData = $this->m_account->collectDataUser();
		
			
		//$response = ['status' => true, 'user' => $returnData];
		$response = ['user' => $returnData];
		echo json_encode($response);
		
		/*
		foreach ($returnData as $row)
		{  
			echo $row->id; echo " "; 
			echo $row->nama;  echo " "; 
			echo $row->gender;   echo " ";
			echo $row->tglLahir;   echo " ";
			echo $row->alamat;   echo " ";
			echo $row->timein;   echo " ";
		} 
		*/		
	}
	 
 }