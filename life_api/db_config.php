<?php
// db_config.php
$mysqli = new mysqli('127.0.0.1','root','','life');
if ($mysqli->connect_errno) {
  http_response_code(500);
  echo json_encode(['error'=>'DB connection failed']);
  exit;
}
$mysqli->set_charset('utf8');
