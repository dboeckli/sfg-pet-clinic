# sfg-pet-clinic

## Abstract
This project is a modern implementation of the classic Spring Pet Clinic application, developed using Spring Boot 3 and Java 21. It demonstrates best practices in Java development and cloud-native deployments. The application is built with a microservice architecture, utilizing Spring Data JPA for data persistence, Spring MVC for the web interface, and Lombok for reducing boilerplate code.

Key Features:
- Full Docker container support
- Kubernetes deployment with Helm charts
- Integrated monitoring through Spring Boot Actuator
- Modern web interface using Bootstrap 5
- CI/CD pipeline via GitHub Actions
- Modular structure separating data and web components

## Access
GUI: http://localhost:8080/ or http://localhost:30080

Based on: https://github.com/spring-projects/spring-petclinic.git


## Deployment with Helm

Be aware that we are using a different namespace here (not default).

Go to the directory where the tgz file has been created after 'mvn install'
```powershell
cd sfg-pet-clinic-web/target/helm/repo
```

unpack
```powershell
$file = Get-ChildItem -Filter sfg-pet-clinic-v*.tgz | Select-Object -First 1
tar -xvf $file.Name
```

install
```powershell
$APPLICATION_NAME = Get-ChildItem -Directory | Where-Object { $_.LastWriteTime -ge $file.LastWriteTime } | Select-Object -ExpandProperty Name
helm upgrade --install $APPLICATION_NAME ./$APPLICATION_NAME --namespace sfg-pet-clinic --create-namespace --wait --timeout 5m --debug --render-subchart-notes
```

show logs and show event
```powershell
kubectl get pods -n sfg-pet-clinic
```
replace $POD with pods from the command above
```powershell
kubectl logs $POD -n sfg-pet-clinic --all-containers
```

Show Details and Event

$POD_NAME can be: sfg-pet-clinic-mongodb, sfg-pet-clinic
```powershell
kubectl describe pod $POD_NAME -n sfg-pet-clinic
```

Show Endpoints
```powershell
kubectl get endpoints -n sfg-pet-clinic
```

status
```powershell
helm status $APPLICATION_NAME --namespace sfg-pet-clinic
```

test
```powershell
helm test $APPLICATION_NAME --namespace sfg-pet-clinic --logs
```

uninstall
```powershell
helm uninstall $APPLICATION_NAME --namespace sfg-pet-clinic
```

delete all
```powershell
kubectl delete all --all -n sfg-pet-clinic
```

create busybox sidecar
```powershell
kubectl run busybox-test --rm -it --image=busybox:1.36 --namespace=sfg-pet-clinic --command -- sh
```

You can use the actuator rest call to verify via port 30081

## Docker

### create image
```shell
.\mvnw clean package spring-boot:build-image
```
or just run
```shell
.\mvnw clean install
```
