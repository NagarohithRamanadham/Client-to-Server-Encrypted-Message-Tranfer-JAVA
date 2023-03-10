# Client-to-Server-Encrypted-Message-Tranfer-JAVA

1. First run the ServerMachine.java so it waits for the clients to be connected.
2. Then run the ClientMachine.java, then this .java file connects with the Server that is running.
3. The message typed in the ClientMachine gets encrypted using AES algorithm which generates a secret key.
4. Then the encrypted message is sent to the ServerMachine.java with the secret key
5. The ServerMachine also uses the same AES algorithm to Decrypt the message.
