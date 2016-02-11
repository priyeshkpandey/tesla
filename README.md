Download selenium-server-standalone-2.49.0.jar,chromedriver 2.9 for respective os and follow the steps

1.Open terminal/cmd and type bellow command
---------------------------------------------------------------------------------------
java -jar selenium-server-standalone-2.49.0.jar -role hub(Note:This command should be executed in staging server.But if you want to run in local just run the command in your system)

2.Open another terminal/cmd and then run the following command
---------------------------------------------------------------------------------------
java -jar selenium-server-standalone-2.49.0.jar -role node -hub http://<ServerIpAddress>:4444/grid/register  -Dwebdriver.chrome.driver=/path/to/chromedriver -nodeConfig path/to/nodeconfig.json

Note: Here <serverIpAddress> is the  Staging ip address.To run in  local just use localhost or 127.0.0.1.
nodeconfig.json is present in the RequiredFiles folder

3.Build and deploy the projectto the server(tomcat) and  in postman hit bellow url with header
-------------------------------------------------------------
http://<ServerIPAddress>:8080/TestAutomationFramework/init/test?env=test&appType=WEB  
for local just use 127.0.0.1  

4.use header 
-----------
buildpath :path/to/android/ios/build  

Null for WEB and SERVICE for now  



