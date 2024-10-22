# Development
### Postgresql
```sh
docker run -d -p 5432:5432 -v $HOME/volumes/postgres-data:/var/lib/postgresql/data --name postgres mypostgres
```	
### Keycloak
```sh
docker build -t dev/keycloak .

docker run -d \
	-v $HOME/work/symfoititis/keycloak/custom-keycloak-themes-24.0.1.jar:/opt/keycloak/lib/lib/main/org/keycloak.keycloak-themes-24.0.1.jar \
	--network host \
	--name keycloak \
	dev/keycloak
```

### Nginx
```sh
DOCKER_BUILDKIT=1 docker build --target=dev-image -t dev/nginx .
docker run -d -p 80:80 --name nginx dev/nginx
```	
### Student, Admin API
```sh
./mvnw spring-boot run
```	
### Student
```sh
npm run dev -- --host
```
### Admin
```sh
npm run dev -- --host --port 5500
```			

# Staging
### Postgresql
```sh
docker run -d -v $HOME/volumes/postgres-data:/var/lib/postgresql/data \
	--name postgres \
	--network symfoititis \
	mypostgres
```

### Keycloak
```sh
docker build -t stage/keycloak .
		
docker run -d \
	--network symfoititis \
	--name keycloak \
	stage/keycloak
```
		
### Student API
```sh
DOCKER_BUILDKIT=1 docker build --add-host repo.maven.apache.org=146.75.124.215 \
	--target=stage-image \
	-t stage/student-api .

docker run -d -v $HOME/Documents/symfoititis/courses:/mnt --name student-api --network symfoititis stage/student-api
```

### Admin API
```sh
DOCKER_BUILDKIT=1 docker build --add-host repo.maven.apache.org=146.75.124.215 \
	--target=stage-image \
	-t stage/admin-api .
	
docker run -d -v $HOME/Documents/symfoititis/courses:/mnt --name admin-api --network symfoititis stage/admin-api
```				
	
### Student
```sh
DOCKER_BUILDKIT=1 docker build --target=stage-image -t stage/student .
docker run -d --name student --network symfoititis stage/student
```	

### Admin
```sh
DOCKER_BUILDKIT=1 docker build --target=stage-image -t stage/admin .
docker run -d --name admin --network symfoititis stage/admin
```

### Nginx
```sh
DOCKER_BUILDKIT=1 docker build --target=stage-image -t stage/nginx .
docker run -d -p 80:80 -p 443:443 --name nginx --network symfoititis stage/nginx
```

# Production
### Postgres
```sh
docker run -d -v $HOME/volumes/postgres-data:/var/lib/postgresql/data \
	--name postgres \
	--network symfoititis \
	dimleo/symfoititis:postgres
```

### Keycloak
```sh
docker build -t prod/keycloak .
docker tag prod/keycloak dimleo/symfoititis:keycloak
docker push dimleo/symfoititis:keycloak

docker run -d \
	--network symfoititis \
	--name keycloak \
	dimleo/symfoititis:keycloak
```	
### Student API
```sh
DOCKER_BUILDKIT=1 docker build --add-host repo.maven.apache.org=146.75.124.215 \
	--target=prod-image \
	-t prod/student-api .
docker tag prod/student-api dimleo/symfoititis:student-api
docker push dimleo/symfoititis:student-api

docker run -d -v $HOME/Documents/symfoititis/courses:/mnt --name student-api --network symfoititis dimleo/symfoititis:student-api
```
###  Admin API
```sh
DOCKER_BUILDKIT=1 docker build --add-host repo.maven.apache.org=146.75.124.215 \
	--target=prod-image \
	-t prod/admin-api .
docker tag prod/admin-api dimleo/symfoititis:admin-api
docker push dimleo/symfoititis:admin-api

docker run -d -v $HOME/Documents/symfoititis/courses:/mnt --name admin-api --network symfoititis dimleo/symfoititis:admin-api
```
				
### Student API
```sh
DOCKER_BUILDKIT=1 docker build --target=prod-image -t prod/student .
docker tag prod/student dimleo/symfoititis:student
docker push dimleo/symfoititis:student

docker run -d --name student --network symfoititis dimleo/symfoititis:student
```
	
### Admin
```sh
DOCKER_BUILDKIT=1 docker build --target=prod-image -t prod/admin .
docker tag prod/admin dimleo/symfoititis:admin
docker push dimleo/symfoititis:admin

docker run -d --name admin --network symfoititis dimleo/symfoititis:admin
```

### Nginx
```sh
DOCKER_BUILDKIT=1 docker build --target=prod-image -t prod/nginx .
docker tag prod/nginx dimleo/symfoititis:nginx
docker push dimleo/symfoititis:nginx

docker run -d -p 80:80 -p 443:443 --name nginx --network symfoititis dimleo/symfoititis:nginx	
```