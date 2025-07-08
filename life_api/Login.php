<?php
// login.php
header('Content-Type: application/json');
require 'db_config.php';
require 'vendor/autoload.php';

use Firebase\JWT\JWT;
use Firebase\JWT\Key;

const JWT_SECRET = 'sua_chave_super_secreta_123!';

$data  = json_decode(file_get_contents('php://input'), true);
$user  = $mysqli->real_escape_string($data['username'] ?? '');
$plain = $data['password']       ?? '';

if (!$user || !$plain) {
    http_response_code(422);
    echo json_encode(['error'=>'Missing fields']);
    exit;
}

$stmt = $mysqli->prepare("SELECT user_id, password_hash FROM `user` WHERE username=?");
$stmt->bind_param('s', $user);
$stmt->execute();
$stmt->store_result();

if ($stmt->num_rows === 0) {
    error_log("LOGIN DEBUG: utilizador '$user' não encontrado.");
    http_response_code(401);
    echo json_encode(['success'=>false, 'error'=>'User not found']);
    exit;
}

$stmt->bind_result($uid, $hash);
$stmt->fetch();

// DEBUG
error_log("LOGIN DEBUG: plain='$plain', hash='$hash'");

if (!password_verify($plain, $hash)) {
    error_log("LOGIN DEBUG: password_verify falhou para user_id=$uid");
    http_response_code(401);
    echo json_encode(['success'=>false, 'error'=>'Invalid credentials']);
    exit;
}

// continua geração de token
$now     = time();
$exp     = $now + 3600;
$payload = [
    'iss' => 'seu_dominio.com',
    'iat' => $now,
    'exp' => $exp,
    'sub' => $uid
];

$jwt = JWT::encode($payload, JWT_SECRET, 'HS256');

echo json_encode([
    'success' => true,
    'user_id' => $uid,
    'token'   => $jwt
]);

$stmt->close();
$mysqli->close();
