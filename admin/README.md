# admin-v0.1.0

## Project Setup

```sh
npm install
```

### Type-Check, Compile and Minify

```sh
npm run build
```

### Build Docker Image

```sh
VERSION=v0.1.0

docker build -t admin:$VERSION .
```

### Run Docker Conatiner

```sh
docker run -d -p 5500:5500 --name admin admin:$VERSION
```
