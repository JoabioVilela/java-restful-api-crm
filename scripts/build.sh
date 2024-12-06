
#!/bin/bash
# Build Script
echo "Building project..."
# mvn clean package -DskipTests
# mvn clean package
./mvnw clean install -U
.mvn clean package