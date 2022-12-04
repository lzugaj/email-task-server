# Development setup

## Run application

It is necessary to position yourself under the ``support/docker`` folder and open terminal.

Start docker container ``docker-compose up``

This will start Postgresql database and service in Docker. There is a bash script in the project that creates database when db image is creating. After application is started you can try to access some endpoints via Postman or frontend application (``yarn start``).

Example: ``http://localhost:8080/api/v1/email-message``
