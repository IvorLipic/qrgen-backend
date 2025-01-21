The Spring Boot backend serves as the foundation for a web application (qrgen-frontend) that generates and manages QR codes for tickets. It includes the following features:

- API: - /generate (POST) -> generates ticket (M2M protected, JWT) saves it in the database
       - /count (GET) -> reuturns total number of generated tickets so far (Public endpoint)
       - /{ticketId} (GET) -> returns the ticket details (OIDC/User protected)
