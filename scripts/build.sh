
#!/bin/bash
# Build Script
echo "Building project..."
# mvn clean package -DskipTests
mvn clean package
mvn clean install