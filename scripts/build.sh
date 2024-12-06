
#!/bin/bash
# Build Script
echo "Building project..."
mvn clean package -DskipTests
ls -l target/
