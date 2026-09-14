cd sfg-pet-clinic-web/target/helm/repo

$file = Get-ChildItem -Filter sfg-pet-clinic-web-chart-*.tgz | Select-Object -First 1
$APPLICATION_NAME = Get-ChildItem -Directory | Where-Object { $_.LastWriteTime -ge $file.LastWriteTime } | Select-Object -ExpandProperty Name
Write-Host "test application: $APPLICATION_NAME"
helm test $APPLICATION_NAME --namespace sfg-pet-clinic-web --logs
