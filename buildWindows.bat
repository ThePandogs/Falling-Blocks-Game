@echo off

rem Change directory to Falling-Blocks-Game
cd Falling-Blocks-Game

rem Compile the .java files
dir /s /B *.java > paths.txt
javac -d "build" @paths.txt

rem Copy resources to the build directory, skipping existing files
xcopy /s /e /Y src\resources\* build\

rem Run the application
java -cp build view.MainWindow

rem Remove the paths.txt file
del paths.txt

rem Remove the build directory and its contents
rmdir /s /q build

rem Return to the original directory
cd ..
