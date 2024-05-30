# Nginx - Reverse Proxy

### Build Docker Image

```sh
VERSION=v0.1.0

docker build -t mynginx:$VERSION .
```

### Run Docker Conatiner

```sh
docker run -d -p 80:80 --name mynginx mynginx:$VERSION
```
