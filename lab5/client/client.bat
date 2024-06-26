
set compute=D:\KAI\JAVA\labs\lab5\compute\src
set server=D:\KAI\JAVA\labs\lab5\server\src
set client=D:\KAI\JAVA\labs\lab5\client\src

set local_server_directory=D:\KAI\JAVA\labs\lab5\work
set network_folder=C:\work\

java -cp %network_folder%client\src\;%network_folder%compute.jar^
 -Djava.rmi.server.codebase=file:///C:/work/client/src/^
 -Djava.security.policy=%local_client_directory%client.policy^
 client.ComputeSum^
 192.168.51.70 1 2 3 4 5

pause >nul