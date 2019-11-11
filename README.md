# Codacy

This project has the goal of listing commits done in a certain GitHub repository. This repository is determined by the URL specified by the user.
The code is partitioned into three stages, according to the code challenge provided.
The first part consists in an app that uses the Git Command Line Interface to fetch a list of commits done in a certain repository.
The second part, like the first one, also uses Git CLI to fetch the commits, but instead of a Main class as an entry point, it provides an endpoint for the same purpose.
Finally, the third part uses the available GitHub API to fetch the list of commits, but if anything goes wrong, it reverts to the previous flow from part 2, using Git CLI.


> **To note:** This code was tested using only macOS. Other platforms might work, but it's not guaranteed.


## Pre Requisits

The requisits specified are the tested versions. Other version numbers might not function properly.

 - Java JDK 8 (version 1.8.0_121)
 - Apache Maven (version 3.5.0)
 - Apache Tomcat (version 9.0.27)

## Build

Clone repository to your prefered folder
`git clone https://github.com/rvlucas/Codacy.git`

Enter the root of the cloned repository and build the projects with maven
`mvn clean install`

This will generate jar/war files on the projects target folders that will be used to run the programs.


### Part 1

Execute the command `java -jar PATH_TO_GENERATED_JAR GITHUB_URL`. For example: `java -jar part1-1.0.0.jar https://github.com/rvlucas/Codacy.git`

*Missing*: Unit Testing.

### Part 2.1

Run your Tomcat instance and place the generated War file from part 2 on the webapps folder of the Tomcat installation directory.

Doing a GET request to `http://localhost:8080/part2/gitcli/commits?url=GITHUB_URL` will provide you with a list of commits from the repository passed as GITHUB_URL.

This endpoint accepts three query parameters:

 1. *url*: An encoded url of the repository to list commits from (required)
 2. *page*: The page number you want to see (optional, default 1)
 3. *perPage*: The number of commits you want to see per page (optional, default 10)

Full example of use: `http://localhost:8080/part2/gitcli/commits?url=https%3A%2F%2Fgithub.com%2Frvlucas%2FCodacy.git&page=3&perPage=2`

*Missing*: Unit Testing.

### Part 3

Similarly to Part 2.1, deploy the war generated in the part 3 project to your Tomcat instance.


Do a GET request to `http://localhost:8080/part2/gitcli/commits?url=GITHUB_URL` will provide you with a list of commits from the repository passed as GITHUB_URL.

This endpoint accepts three query parameters:

 1. url: An encoded url of the repository to list commits from (required)
 2. page: The page number you want to see (optional, default 1)
 3. perPage: The number of commits you want to see per page (optional, default 10)

Full example of use: `http://localhost:8080/part2/gitcli/commits?url=https%3A%2F%2Fgithub.com%2Frvlucas%2FCodacy.git&page=3&perPage=2`


*Missing*: Unit Testing.
