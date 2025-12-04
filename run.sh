#!/bin/bash

# Simple runner for Video Encryption Suite

set -e

PROJECT_DIR="$(cd "$(dirname "$0")" && pwd)"
OUT_DIR="$PROJECT_DIR/out"
BCPROV_JAR="/usr/share/java/bcprov.jar"
MAIN_CLASS="com.project.cryptoapp.Main"

echo "== Video Encryption Suite =="
echo "Project directory: $PROJECT_DIR"
echo

# ---- Check Java ----
if ! command -v javac >/dev/null 2>&1; then
  echo "ERROR: javac (JDK) not found."
  echo "Install it with:  sudo apt install default-jdk"
  exit 1
fi

if ! command -v java >/dev/null 2>&1; then
  echo "ERROR: java runtime not found."
  echo "Install it with:  sudo apt install default-jdk"
  exit 1
fi

# ---- Check BouncyCastle ----
if [ ! -f "$BCPROV_JAR" ]; then
  echo "ERROR: BouncyCastle JAR not found at $BCPROV_JAR"
  echo "Install it with:  sudo apt install libbcprov-java"
  exit 1
fi

echo "Java and BouncyCastle found."

# ---- Compile ----
echo
echo "Compiling source files..."
rm -rf "$OUT_DIR"
mkdir -p "$OUT_DIR"

javac -cp "$BCPROV_JAR" -d "$OUT_DIR" $(find "$PROJECT_DIR/src" -name "*.java")

echo "Compilation successful."

# ---- Run ----
echo
echo "Starting application..."
echo

java -cp "$OUT_DIR:$BCPROV_JAR" "$MAIN_CLASS"

