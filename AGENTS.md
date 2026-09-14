# AGENTS.md

Spring Boot 4 (parent 4.1.1) Pet Clinic web application on **Java 25** (enforced by the
maven-enforcer plugin). Multi-module Maven build: aggregator `sfg-pet-clinic`, with
`sfg-pet-clinic-data` (domain, repositories, services) and `sfg-pet-clinic-web` (Spring MVC +
Thymeleaf UI). The Helm chart and the Docker image live in `sfg-pet-clinic-web`.

## Build & test commands

- Full build: `./mvnw clean verify` — format checks (spring-javaformat + Spotless), unit tests
  (`*Test`, surefire) + IT (`*IT`, failsafe), Helm lint/template/package.
- Unit tests only: `./mvnw test`. Single test: `./mvnw test -Dtest=OwnerControllerTest`.
- `./mvnw clean install` additionally builds the Docker image and packages the Helm chart into
  `sfg-pet-clinic-web/target/helm/repo/`. Skip the Docker build with `-Dskip.docker.build=true`.
- `-Dskip.start.stop.springboot=true` skips the in-build app boot (spring-boot:start/stop).
- Start locally: `./mvnw spring-boot:run` (run from the `sfg-pet-clinic-web` module; app on `:8080`).

After changing code, always verify: run the relevant Maven goal above and report its output
(evidence, not just "done").

## Formatting is enforced (fails the `validate` phase)

- Java: Spring Java Format → fix with `./mvnw spring-javaformat:apply`.
- pom.xml, `**/*.md`, json, `src/main/resources/application*.yaml`, `**/*.sh`: Spotless → fix
  with `./mvnw spotless:apply`.
- `AGENTS.md` and `CLAUDE.md` are excluded from the markdown formatter.

## Sandbox build quirk

The kit sandbox mounts the repo via filesystem passthrough, which blocks symlinks — Spotless's
`npm install` (prettier) would fail with `EPERM` unless npm skips bin links. The sandbox kit sets
`npm_config_bin_links=false` globally (`spec.yaml` → `environment.variables`), so no manual export
is needed inside the kit sandbox. On a normal host (Windows/CI) this does not apply.

## Deploy / CI

- Deployment is Helm-only: chart in `sfg-pet-clinic-web/helm-charts/`, packaged to
  `sfg-pet-clinic-web/target/helm/repo/`, release name = artifactId `sfg-pet-clinic-web`,
  namespace `sfg-pet-clinic-web` (`values.yaml` → `global.namespace`), NodePort `30080`.
- CI (`.github/workflows/`): `maven-build.yml` builds + deploys snapshots and triggers
  `deploy-and-test-cluster.yml`; `release.yml` runs `mvn release:prepare release:perform` on
  main/master only (version must be `-SNAPSHOT`); SonarCloud analysis runs in the `analyze` job.
- Dependency updates are managed via `.github/dependabot.yml` and `.github/renovate.json`; validate
  changes with `renovate-config-validator`.
