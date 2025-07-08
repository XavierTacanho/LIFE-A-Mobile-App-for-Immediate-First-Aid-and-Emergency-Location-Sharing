# 📱 LIFE – A Mobile App for Immediate First Aid and Emergency Location Sharing

**Projeto final de licenciatura** – Universidade da Beira Interior
**Autor:** Xavier José André Tacanho
**Orientador:** Professor Doutor Tiago M. C. Simões
**Data:** 6 de julho de 2025

---

## 🩺 Descrição

**LIFE** é uma aplicação móvel destinada a fornecer suporte imediato em situações de emergência, combinando:

* 🚨 **Botão SOS** com envio automático da localização em tempo real (GPS, AML)
* 🩹 **Guias passo‑a‑passo** de primeiros socorros para diversos cenários clínicos
* 🔗 Integração com **serviços de emergência** (112) e **contactos pessoais de confiança**
* 📚 **Módulo educativo** com cursos interativos e quizzes de primeiros socorros

O objetivo é reduzir o tempo de resposta e aumentar a eficácia das operações de socorro.

---

## ⚙️ Estrutura do Repositório

```bash
LIFE/
├── app/                  # Código Android (Java, XML)
├── backend/              # Serviços PHP + scripts SQL
├── docs/                 # Diagramas, testes e especificação
├── assets/               # Ícones, imagens e ficheiros JSON
└── README.md             # Documentação do projeto
```

---

## 🔧 Pré‑requisitos

### Frontend (Android)

* Android Studio Electric Eel (v2024.3.2)
* Java 17
* Android SDK API 35

### Backend (PHP + MySQL)

* WampServer (Apache, PHP 8.x, MySQL 5.7+)
* PHP ext‑mysqli
* MySQL Workbench
* Composer (opcional)

### Ferramentas de Teste

* Postman v10+
* Emulador Pixel 8 Pro ou dispositivo Android físico

---

## 🚀 Instalação & Configuração

### 1. Clonar o repositório

```bash
git clone https://github.com/SEU_USUARIO/LIFE.git
cd LIFE
```

### 2. Backend

```sql
# Criar e importar base de dados
CREATE DATABASE life;
USE life;
SOURCE backend/db_dump.sql;
```

Editar `backend/config.php`:

```php
<?php
define('DB_HOST', 'localhost');
define('DB_USER', 'root');
define('DB_PASS', '');
define('DB_NAME', 'life');
```

Colocar pasta `backend/` dentro do diretório `www/` do WampServer.

### 3. Frontend

Abrir `app/` no Android Studio. Atualizar `ApiClient.java`:

```java
baseUrl = "http://10.0.2.2/backend/";
```

Adicionar chave do Google Maps em `res/values/strings.xml`:

```xml
<string name="google_maps_key">SUA_API_KEY</string>
```

Executar no emulador ou dispositivo.

---

## 🛠️ Tecnologias & Ferramentas

* **Android**: Java, XML, Retrofit, Google Maps SDK
* **Backend**: PHP, MySQL, Apache
* **Outros**: Postman, JUnit, Git, Draw\.io, MySQL Workbench

---

## 📐 Arquitetura

![Diagrama de Arquitetura](docs/architecture.png)

* Apresentação: Activities & Fragments
* Negócio: Services, Retrofit
* Dados: PHP REST API, MySQL

---

## 📊 Base de Dados

![Esquema Relacional](docs/schema-relacional.png)

* Tabelas principais: `User`, `Address`, `Contacts`, `Emergency`, `Courses`, `User_Courses`

---

## 📱 Funcionalidades

1. Registo de utilizadores
2. Login seguro
3. SOS com localização em tempo real
4. Consulta de primeiros socorros
5. Realização de cursos e quizzes com progresso

---

## ✅ Testes & Validação

* Requisitos funcionais (RF01 a RF10): ✅ Cobertos
* Requisitos não funcionais: ✅ Latência < 200ms, SSL/TLS
* Postman: coleção de endpoints disponível em `backend/postman_collection.json`

---

## 🔮 Futuras Melhorias

* App iOS com Swift
* Integração com dispositivos wearable (queda, ritmo cardíaco)
* Chatbot IA para ajuda imediata
* Localização indoor via BLE e Wi-Fi RTT

---

## 🤝 Agradecimentos

* Prof. Tiago M. C. Simões (orientação)
* Universidade da Beira Interior – DCTI
* Colegas e utilizadores beta

---

## 📜 Licença

Distribuído sob Licença MIT. Ver ficheiro `LICENSE` para mais informações.
