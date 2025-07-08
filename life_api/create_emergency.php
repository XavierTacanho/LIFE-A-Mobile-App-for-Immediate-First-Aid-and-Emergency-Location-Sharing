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
$userId      = intval($data['user_id'] ?? 0);
$description = $mysqli->real_escape_string($data['description'] ?? '');
$status      = $mysqli->real_escape_string($data['status'] ?? 'Pendente');
$lat         = isset($data['latitude'])  ? doubleval($data['latitude'])  : null;
$lon         = isset($data['longitude']) ? doubleval($data['longitude']) : null;
$street      = $mysqli->real_escape_string($data['street_address'] ?? '');

if (!$userId || !$description || $lat === null || $lon === null) {
    http_response_code(422);
    echo json_encode(['error'=>'Missing fields']);
    exit;
}

// Insere na tabela emergency
$stmt = $mysqli->prepare("
    INSERT INTO emergency (user_id, date, description, status)
    VALUES (?, NOW(), ?, ?)
");
$stmt->bind_param('iss', $userId, $description, $status);
if (!$stmt->execute()) {
    http_response_code(500);
    echo json_encode(['error'=>'Failed to insert emergency']);
    exit;
}
$emergencyId = intval($stmt->insert_id);
$stmt->close();

// Insere na tabela emergency_localization
$stmt2 = $mysqli->prepare("
    INSERT INTO emergency_localization (emergency_id, latitude, longitude, street_address)
    VALUES (?, ?, ?, ?)
");
$stmt2->bind_param('idds', $emergencyId, $lat, $lon, $street);
if (!$stmt2->execute()) {
    http_response_code(500);
    echo json_encode(['error'=>'Failed to insert localization']);
    echo $stmt2->error;
    exit;
}
$stmt2->close();

// Sucesso
echo json_encode([
    'success'       => true,
    'emergency_id'  => $emergencyId,
    'latitude'      => $lat,
    'longitude'     => $lon,
    'street_address'=> $street,
    'status'        => $status,
    'description'   => $description
]);

$mysqli->close();
?>
