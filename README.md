The Spring Boot backend serves as the foundation for a web application [qrgen-frontend](https://github.com/IvorLipic/qrgen-frontend) that generates and manages QR codes for tickets.

- API:
-     - /generate (POST) -> generates ticket (M2M protected, JWT) saves it in the database
      - /count (GET) -> reuturns total number of generated tickets so far (Public endpoint)
      - /{ticketId} (GET) -> returns the ticket details (OIDC/User protected)

This back-end is deployed on [render](https://render.com/) in a Docker container. 
[Dockerfile](https://github.com/IvorLipic/qrgen-backend/blob/master/Dockerfile) used to make that container.

In code there are "hanging" env variables -> [application.yml](https://github.com/IvorLipic/qrgen-backend/blob/master/src/main/resources/application.yml).
They are for the database (e.g. PostgreSQL, I used [Neon](https://neon.tech/) and [auth0](https://auth0.com/) connections.
