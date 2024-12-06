
#!/bin/bash
# Build Script
echo "Building project..."
# mvn clean package -DskipTests
mvn dependency:resolve
mvn clean package -X