# 🎥 Video Encryption & Decryption Suite (Java Swing)

This project is a desktop application built using **Java Swing** that allows users to encrypt and decrypt video files using multiple cryptographic algorithms. It supports common video formats such as:


The tool was developed as part of a university cybersecurity project to study encryption performance and usability for multimedia security.

---

## 🚀 Features

✔ GUI-based encryption & decryption  
✔ Supports 10 cryptographic algorithms  
✔ Shows file size, execution time & status messages  
✔ Stores encryption keys securely in separate `.key` files  
✔ Built-in **Play** button to preview decrypted video  
✔ Log window for debugging and progress feedback  

---

## 🔐 Supported Algorithms

| Category | Algorithms |
|----------|-----------|
| Modern AES Variants | AES-128, AES-192, AES-256 |
| Legacy Standards | DES, 3DES |
| Lightweight Block Ciphers | Blowfish, RC5 |
| Research / Hybrid Models | IDEA, BEAR (AES-256), LION (Blowfish-128) |

---

## 🛠️ Technologies Used

- Java 17+
- Swing Framework
- BouncyCastle Cryptography Library

---

## 📁 Project Structure
📦 video-encryption-gui
┣ 📂 src
┃ ┣ 📂 com.project.cryptoapp.crypto
┃ ┣ 📂 com.project.cryptoapp.controller
┃ ┣ 📂 com.project.cryptoapp.model
┃ ┗ 📂 com.project.cryptoapp.ui
┣ out/ (generated during build)
┣ README.md
┣ .gitignore


---

## 🧪 How to Run

### 1️⃣ Install Dependencies

```bash
sudo apt install default-jdk libbcprov-java


javac -cp /usr/share/java/bcprov.jar -d out $(find src -name "*.java")

java -cp out:/usr/share/java/bcprov.jar com.project.cryptoapp.Main


📊 Performance Results Summary

Fastest: AES-128, Blowfish, LION

Balanced: AES-256, IDEA, BEAR

Slowest: DES and 3DES (legacy, weak security)

Execution time depends on file size and algorithm complexity.


🎓 Academic Value

This project demonstrates:

Practical file-level cryptography

Algorithm performance comparison

Key storage & secure decryption workflow

Implementation of GUI-based security software

👤 Author
Field	 -Value
Student-Afwan
Project Type - University Cybersecurity Project
Platform - Linux (Ubuntu/Kali)
Language - Java


