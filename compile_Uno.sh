#!/bin/bash


#!/usr/bin/env bash

# Exit immediately if a command exits with a non-zero status
set -e

MANIFEST="manifest.txt"
UNO_JAR="uno.jar"

# --- CONFIGURATION ---
SRC_DIR="./*/"
BIN_DIR="bin"
LIB_DIR="uno"
UNO_PATH="uno/*"
UNO_DOCS="docs"

# Main class names (without .java extension)
MAIN_NORMAL="Main"
MAIN_NAME="Main.java"
MAIN_EXT="ExtendedMain"
MAIN_EXT_NAME="ExtendedMain.java"

# Setup Classpath (includes bin directory and any JARs in the lib folder)
CP="${BIN_DIR}"
if [ -d "$LIB_DIR" ] && [ "$(ls -A $LIB_DIR)" ]; then
    CP="${BIN_DIR}:${LIB_DIR}/*"
fi

# --- FUNCTIONS ---

# Ensure the build directory exists
init() {
    if [ ! -d "$BIN_DIR" ]; then
        mkdir "$BIN_DIR"
        echo "[+] Created $BIN_DIR/ directory."
    fi
}

# Compile all Java files
compile() {
    init
    echo "[*] Compiling Java source files..."
    # Finds all .java files in src/ and compiles them into bin/
    #javac -d "$BIN_DIR" -cp "$CP" $(find "$SRC_DIR" -wholename "/*/*.java")
    javac -d "$BIN_DIR" $(find -name $MAIN_NAME) $(find $SRC_DIR -name "*.java") $(find -name $MAIN_EXT_NAME)

    echo "[+] Compilation successful."
}

# Run the normal application
run_normal() {
    if [ ! -d "$BIN_DIR" ] || [ -z "$(ls -A $BIN_DIR)" ]; then
        compile
    fi
    echo "[*] Running Normal Application ($MAIN_NORMAL)..."
    echo "--------------------------------------------------"
    java -cp "$CP" "$MAIN_NORMAL"
}

# Run the extended application
run_ext() {
    if [ ! -d "$BIN_DIR" ] || [ -z "$(ls -A $BIN_DIR)" ]; then
        compile
    fi
    echo "[*] Running Extended Application ($MAIN_EXT)..."
    echo "--------------------------------------------------"
    java -cp "$CP" "$MAIN_EXT"
}

# Clean build artifacts (like 'make clean')
clean() {
    echo "[*] Cleaning build artifacts..."
    if [ -d "$BIN_DIR" ]; then
        rm -rf "$BIN_DIR"
        echo "[+] Removed $BIN_DIR/ directory and all .class files."
    else
        echo "[~] Nothing to clean."
    fi
}

gen_jar() {
	echo "[*] Generating JAR file..."
	jar cmf $MANIFEST $UNO_JAR $UNO_PATH
}

inspect_jar(){
	jar tf $UNO_JAR	
}

gen_javadoc(){
	javadoc -d $UNO_DOCS $(find $SRC_DIR -name "*.java")
}


# Print usage helper
usage() {
    echo "Usage: $0 {compile|run|run-ext|clean|all}"
    echo "  compile : Compiles the source code"
    echo "  run     : Runs the normal application (compiles if needed)"
    echo "  run-ext : Runs the extended application (compiles if needed)"
    echo "  clean   : Removes compiled .class files and bin directory"
    echo "  all     : Cleans, compiles, and runs the normal app"
    echo "  gen_jar : Generates JAR File"
    echo "  inspect_jar	: Inspects a JAR file"
    echo "  gen_javadoc	: Create javadocs documentation"
    exit 1
}

# --- MAIN EXECUTION ---

# Default to showing usage if no argument is provided
if [ -z "$1" ]; then
    usage
fi

case "$1" in
    compile)
        compile
        ;;
    run)
        run_normal
        ;;
    run-ext)
        run_ext
        ;;
    clean)
        clean
        ;;
    all)
        clean
        compile
        run_normal
        ;;
    gen_jar)
	gen_jar
	;;
    inspect_jar)
	inspect_jar
	;;
gen_javadoc)
	gen_javadoc
	;;
    *)
        usage
        ;;
esac
