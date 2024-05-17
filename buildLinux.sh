#!/bin/bash

# Compile the .java files
find -name "*.java" > paths.txt
mkdir -p build
javac -d "build" @paths.txt

# Copy resources to the build directory
cp -r src/main/resources/* build/

# Run the application
java -cp build view.MainWindow

# Remove the paths.txt file
rm paths.txt