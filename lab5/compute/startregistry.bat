@echo off

set compute=D:\KAI\JAVA\labs\lab5\compute\src\
set server=D:\KAI\JAVA\labs\lab5\server\src\
set client=D:\KAI\JAVA\labs\lab5\client\src\

set local_server_directory=D:\KAI\JAVA\labs\lab5\work\
set network_folder=C:\work\

cd %compute%
"D:\Programms\Java\jdk-19\bin\rmiregistry.exe"

pause > nul