# Latice

Jeu de plateau Latice en Java 17 / JavaFX (projet SAE, IUT du Limousin).
Repris en R5.A.07 pour mettre en place une chaîne CI/CD.

![CI](https://github.com/Myanganbaatar/Jeu-Latice/actions/workflows/ci.yml/badge.svg)
![CodeQL](https://github.com/Myanganbaatar/Jeu-Latice/actions/workflows/codeql.yml/badge.svg)

## Lancer le projet

```bash
./mvnw javafx:run          # interface graphique
./mvnw verify              # compilation + tests + couverture
```

Pas besoin d'installer Maven, le wrapper (`mvnw`) le télécharge.

## Pipeline

| Job | Outils | Bloquant |
|---|---|---|
| Build & tests unitaires | Maven, JUnit 5, JaCoCo (seuil 50 % de lignes) | oui |
| Qualité | Checkstyle (`config/checkstyle.xml`), PMD, SpotBugs | oui |
| SonarCloud | SonarScanner Maven (si le secret `SONAR_TOKEN` existe) | non |
| Livrable | `mvn package`, jar en artefact (branche main) | — |
| CodeQL (workflow séparé) | analyse de sécurité GitHub, + chaque lundi | — |
| Dependabot | mises à jour Maven et GitHub Actions, chaque semaine | — |

## Analyse SonarQube en local

```bash
docker run -d --name sonarqube -p 9000:9000 sonarqube:community
./mvnw verify org.sonarsource.scanner.maven:sonar-maven-plugin:sonar \
  -Dsonar.host.url=http://localhost:9000 -Dsonar.token=<token> -Dsonar.projectKey=latice
```
