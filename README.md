# 🛡️ Smart TCP Chat System

A Java-based Client-Server chat system featuring automated content filtering, anti-spam protection, and multi-threaded communication using TCP/IP Sockets. Designed as a practice for the CPCS371 Networks course.

---

## ✨ Main Features
* **Multithreading:** Supports multiple users chatting at the same time.
* **Anti-Spam:** Limits users to 3 messages every 5 seconds to prevent flooding.
* **Content Filter:** Automatically masks "bad words" using a blacklist.

---

## 🛠️ Skills Demonstrated
* **TCP Sockets:** Built a reliable connection between Client and Server.
* **Java Threads:** Managed concurrent users using Multithreading.
* **System Logic:** Implemented rate-limiting using the System Clock.
* **Text Processing:** Used Regex for real-time message filtering.

---

## 🏗️ How it Works
1. **Server:** Starts and listens for new connections. Each client gets its own thread.
2. **Client:** Connects to the server, enters a nickname, and starts chatting.
3. **Moderation:** The server checks every message for spam and filtered words before broadcasting it.

---

## 🚀 How to Run
1. Run `Server.java` first.
2. Run `Client.java` (you can run it multiple times to test the group chat).

---

**Practice:** CPCS371  
**Tools:** Java | Sockets | Threads
