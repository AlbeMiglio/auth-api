# Authentication API 

This documentation provides guidelines on how to use the authentication endpoint to verify user credentials.\
The endpoint is hosted on a Java server and processes HTTP `POST` requests to authenticate users.

### Endpoint

The endpoint port can be configured in the `config.yml` file, together with a **secret key** for additional security.

Example URL, with default port: `http://localhost:8080/auth` \
Header: `Content-Type: application/json`

### Body
The body of the request should be a JSON object containing the following fields:

- `username`: The username of the user.
- `password`: The hashed password of the user.
- `secret_key`: A secret key for additional security.
- 
```json
{
    "username": "expectedUsername",
    "password": "expectedPasswordHash",
    "secret_key": "expectedSecretKey"
}
```

### Response
The response will be a JSON object containing the following fields:
- `status`: The status of the request. Possible values are `valid`, `invalid`, and `wrong_token`.

```json
{
    "status": "valid"
}
```

### Supported Authentication Systems
The endpoint currently supports the following authentication systems:
- **AuthMe**: https://dev.bukkit.org/projects/authme-reloaded


