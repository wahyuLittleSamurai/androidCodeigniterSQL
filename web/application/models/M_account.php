  <?php
  defined('BASEPATH') OR exit('No direct script access allowed');
 
  class M_account extends CI_Model{

	function collectDataAdmin($data)
	{
		$this->db->select('*');
		$this->db->from('tbladmin');
		$this->db->where('nama',$data['nama']);
		$this->db->where('password',$data['password']);
		$query=$this->db->get();
		return $query->num_rows();
	}
	function addUser($data)
	{
		$this->db->insert('tbluser',$data);
	} 
	function collectDataUser()
	{
		$this->db->select('*');
		$this->db->from('tbluser');
		$query = $this->db->get();
		return $query->result();
	}	
	function updateSensor($data)
	{
		$this->db->set('kpa', $data['kpa']);
		$this->db->where('dataKe',$data['dataKe']);
		$this->db->update('tblsensor');
	}
	function collectDataSensor()
	{
		$this->db->select('*');
		$this->db->from('tblsensor');
		$query = $this->db->get();
		return $query->result();
	}	
  }