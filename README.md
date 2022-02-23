This projects contains a Spring Boot application that demonstrates an issue.

You need to have a local RabbitMQ broker running on port 5672. If you have docker installed, simply call docker-compose up -d to start the RabbitMQ docker image as defined in docker-compose.yml in the project root.

1. Start this application.
2. Publish one or more messages with big payload (for payload with 20mb file)
  {"itemId":"123","topic":"private.topic.test-service","tenantId":"test","payload":{"file": <base64 file content>}}
3. Profile this application (e.g .with yourkit), create snapshot and look at the memory section.
4. You can find allocated memory for already sent messages.