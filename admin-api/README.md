# admin-api:v0.1.0

## Project Setup

### Build Docker Image

```sh
VERSION=v0.1.0

docker build --add-host repo.maven.apache.org=146.75.124.215 -t admin-api:$VERSION .
```

### Run Docker Container

```sh
docker run -d -v $HOME/Documents/symfoititis/courses:/mnt --name admin-api --network symfoititis admin-api:$VERSION
```

