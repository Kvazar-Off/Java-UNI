

set compute=D:\KAI\JAVA\labs\lab5\compute\src\
set server=D:\KAI\JAVA\labs\lab5\server\src\
set client=D:\KAI\JAVA\labs\lab5\client\src\

set local_server_directory=D:\KAI\JAVA\labs\lab5\work\
set network_folder=C:\work\


java -cp %server%;%network_folder%compute.jar^
 -Djava.rmi.server.codebase=file:///C:/work/client/src/^
 -Djava.security.policy=%network_folder%server.policy^
 -Djava.rmi.server.hostname=localhost^
 engine.ComputeEngine


pause >nul