# HA-TDD-Chess

## Building & Running

```bash
./mvnw test spring-boot:run
```

This command will test and deploy a development server on http://localhost:8080

## Live-Reload
This project uses spring-boot-devtools enabling live reload of code changes if your editor is set up correctly.
For IntelliJ, activate Advanced Settings > Compiler > 'Allow auto-make to start even if developed application is currently running', and Build, Execution, Deployment > Compiler > Build project automatically.
Create a maven run-configuration with the command mvn spring-boot:run, and you can start refreshing the webpage to see your latest changes.

## Docker
If you want to test out the docker setup, you can run the app via docker-compose.

```bash
docker-compose up
```