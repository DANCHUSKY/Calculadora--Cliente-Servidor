# Client-Server Calculator Application

This project is a client-server calculator application that allows clients to connect to a server to perform mathematical operations and retrieve the history of these operations.

## Project Structure

### Server Project
- **Accepting Connections:** Listens for incoming client connections on a specified port.
- **Handling Requests:** Processes mathematical operations requested by clients.
- **Maintaining History:** Keeps a history of the last 100 operations.
- **Logging:** Logs client connections, disconnections, and operations.

### Client Project
- **Perform Operations:** Sends mathematical operations to the server for evaluation.
- **Retrieve History:** Requests the history of operations from the server.
- **Display Results:** Displays the results or errors received from the server.

## Supported Operations

### Basic Operations
- Addition: `a + b`
- Subtraction: `a - b`
- Multiplication: `a * b`
- Division: `a / b`

### Advanced Operations
- Exponentiation: `a ^ b`
- Square Root: `sqrt a`

### Combined and Nested Operations
- Mixed operations with parentheses, e.g., `(a + b) * c`

## Error Handling
- **Division by Zero:** Returns an error for `4 / 0`.
- **Invalid Tokens:** Returns an error for invalid inputs.
- **Square Root of Negative Numbers:** Returns an error for `sqrt -9`.

## Example Usage
- Addition: `2 + 3`
- Combined Operations: `(2 + 3) * 4`
- Square Root and Multiplication: `sqrt 16 * 2`
- Exponentiation: `2 ^ 3`



