# preparation for Spajam 2023

## Requirements

- Java
- Node.js
  required version is written in `.tool-versions` file.  
  just run `asdf install` at the root of this repository.  
  if you don't have `asdf`, please install it
  from [asdf installation](https://asdf-vm.com/#/core-manage-asdf-vm?id=install).

## Run locally

1. change directory to `spajam-2023-api`.
2. run  `./gradlew dockerBuildImage`.
3. run `docker-compose up`.

## Deployment

1. change directory to `spajam-2023-api`.
2. run  `./gradlew dockerBuildImage`. then you can
   see `417866577833.dkr.ecr.ap-northeast-1.amazonaws.com/spajam-2023-api:latest` image.
3.
run `aws ecr get-login-password --region ap-northeast-1 --profile ${YOUR_PROFILE_NAME} | docker login --username AWS --password-stdin 417866577833.dkr.ecr.ap-northeast-1.amazonaws.com`.
4. run `docker push 417866577833.dkr.ecr.ap-northeast-1.amazonaws.com/spajam-2023-api:latest`. then AWS App Runner
   automatically deploy the latest image triggered by ECR push event.
