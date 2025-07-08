<?php
header('Content-Type: application/json');
require 'db_config.php';

// Lê JSON do body
$data = json_decode(file_get_contents('php://input'), true);
if (!$data) {
    http_response_code(400);
    echo json_encode(['error'=>'Invalid JSON']);
    exit;
}

// Campos esperados
$first  = $mysqli->real_escape_string($data['first_name'] ?? '');
$last   = $mysqli->real_escape_string($data['last_name']  ?? '');
$email  = $mysqli->real_escape_string($data['email']      ?? '');
$user   = $mysqli->real_escape_string($data['username']   ?? '');
$plain  = $data['password'] ?? '';
$dob    = $mysqli->real_escape_string($data['date_of_birth'] ?? '');

if (!$first || !$last || !$email || !$user || !$plain || !$dob) {
    http_response_code(422);
    echo json_encode(['error'=>'Missing fields']);
    exit;
}

$res = $mysqli->query("SELECT user_id FROM user WHERE username='$user' OR email='$email'");
if ($res && $res->num_rows > 0) {
    http_response_code(409); // Conflict
    echo json_encode(['error' => 'Username or email already exists']);
    exit;
}
// Gera hash seguro
$hash = password_hash($plain, PASSWORD_BCRYPT);

// Insere na BD
$stmt = $mysqli->prepare(
  "INSERT INTO `user` (first_name, last_name, date_of_birth, username, email, password_hash) 
   VALUES (?, ?, ?, ?, ?, ?)"
);
$stmt->bind_param('ssssss', $first, $last, $dob, $user, $email, $hash);

if ($stmt->execute()) {
    echo json_encode(['success'=>true, 'user_id'=>$stmt->insert_id]);
} else {
    http_response_code(500);
    echo json_encode(['error'=>'Insert failed', 'details' => $stmt->error]);
}

$stmt->close();
$mysqli->close();
?>
