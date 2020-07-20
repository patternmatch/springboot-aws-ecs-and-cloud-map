# ECS Service Discovery in Java

Sample project to present the idea of Service Discovery on Amazon Elastic Container Service (ECS).
Come and visit our blog for detailed information - [todo-link-post](http://todo).

The project consists of two SpringBoot applications. If not specified, the instructions below can be used in both cases.

## Requirements

* `Docker engine (v19 or newer)`
* `Java v11`
* `SpringBoot v2.x`
* `Gradle v6.4.x`

## How to build it?

The assumption is to run these applications on Amazon ECS, so the projects are packaged into Docker image.

Follow the steps to create Docker image and push it to Elastic Container Registry (ECR).

1. Make sure the Docker is running.
2. Navigate to the app's directory and build the project:

```
./gradlew clean build
```

3. Now, package it into Docker image:

```
docker build -t aws-pattern-match-textapi .
```

**Notice:** `aws-pattern-match-textapi` is just an example name.

4. Log in into ECR:

```
$(aws ecr get-login --no-include-email --region eu-west-1)
```

**Notice:** change the region to reflect your ECS setup

5. Tag the image:

```
docker tag aws-pattern-match-textprocessor:latest xxxxxxxxxxxx.dkr.ecr.eu-west-1.amazonaws.com/aws-pattern-match-textprocessor:latest
```

**Notice:** change the image name and ECR repo in accordance to your setup

6. Push the image:

```
docker push xxxxxxxxxxxx.dkr.ecr.eu-west-1.amazonaws.com/aws-pattern-match-textprocessor:latest
```

**Notice:** change the image name and ECR repo in accordance to your setup 

## How to run it locally?

**Notice:** In the commands below, use the images in accordance to your setup.

### TextApi

1. Run the image:

```
docker run --env AWS_REGION=<your-region> --env AWS_DEFAULT_REGION=<your-region> --env AWS_ACCESS_KEY_ID=<your-aws-key-id> --env AWS_SECRET_ACCESS_KEY=<your-aws-key> -d -p 8090:8080 aws-pattern-match-textapi:latest
```

2. Try it out:

Request:
```
curl -X POST --header "Content-type: application/json" --header "Accept: application/json" -d '{"text": "somewhere over the rainbow skies are blue, clouds high over the rainbow, makes all your dreams come true"}' http://localhost:8090/textapi
```
Response:
```
{
  "language": "en",
  "sentiment": "POSITIVE"
}
```

### TextProcessor

1. Run the image:

```
docker run --env TEXTAPI_ENDPOINT=http://host.docker.internal:8090 -d -p 8091:8080 aws-pattern-match-textprocessor:latest
```

**Notice:** The env variable `TEXTAPI_ENDPOINT` should point to the TextApi REST endpoint. Change the above command in accordance to your setup.

2. Try it out:

Request:
```
curl -X POST --header "Content-type: application/json" --header "Accept: application/json" -d '{"text": "somewhere over the rainbow skies are blue, clouds high over the rainbow, makes all your dreams come true"}' http://localhost:8091/textprocessor
```
Response:
```
{
  "language": "en",
  "sentiment": "POSITIVE"
}
```

## How to run it locally?

Please check our blog post for detailed instructions.

## Available tags

1. TODO


