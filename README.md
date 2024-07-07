# Connectly

This project implements a simple chat application using Java sockets. It consists of a server (`Server.java`) that manages client connections and a client (`Client.java`) that connects to the server to send and receive messages.

## Features

- Allows multiple clients to connect to the server concurrently.
- Clients can set their nicknames and chat in a common chat room.
- Supports commands such as `/nick` to change nicknames and `/quit` to disconnect from the server.


## Prerequisites

- Java Development Kit (JDK) installed
- Basic understanding of networking concepts

## Getting Started

### Clone the repository

```bash
git clone https://github.com/your/repository.git
cd repository
```

## Compile the server and client

```bash
javac Server.java
javac Client.java
```

##Run the server
```bash
java Server
```

##Run the client
```bash
java Client
```


## Overview

Once the client connects to the server, it prompts for a nickname. Users can then use commands like `/nick <newnickname>` to change their nickname. Typing `/quit` disconnects from the chat.

## Usage

Upon running the client, it connects to the server at `127.0.0.1` on port `9999` by default. Messages typed in the client are broadcasted to all connected clients via the server.

Users can change their nickname using the `/nick <newnickname>` command.

## Contributing

Contributions are welcome! Fork the repository and submit pull requests to propose improvements or new features.

## License

This project is licensed under the MIT License. See the LICENSE file for details.


## Acknowledgments

- Inspired by [https://www.youtube.com/watch?v=hIc_9Wbn704].

## Support

For issues and questions, please contact [rachitpathakk@gmail.com].
