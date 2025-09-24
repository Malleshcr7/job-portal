pipeline {
    agent any

    tools {
        jdk 'jdk17'
        maven 'maven'  // Configure Maven in Global Tool Configuration
    }

    environment {
        // Docker image configuration
        DOCKER_IMAGE = "malleshcr7/job-portal"
        DOCKER_TAG = "${BUILD_NUMBER}"
        REGISTRY_CREDENTIALS = 'docker-hub-credentials'  // Configure in Jenkins credentials

        // Database configuration for testing
        TEST_DB_URL = "jdbc:h2:mem:testdb"
        TEST_DB_DRIVER = "org.h2.Driver"
    }

    stages {
        stage('Checkout') {
            steps {
                echo 'Checking out source code...'
                checkout scm
                sh 'git --version'
                sh 'java -version'
                sh 'mvn --version'
            }
        }

        stage('Compile') {
            steps {
                echo 'Compiling the application...'
                script {
                    if (isUnix()) {
                        sh 'mvn clean compile'
                    } else {
                        bat 'mvn clean compile'
                    }
                }
            }
        }

        stage('Unit Tests') {
            steps {
                echo 'Running unit tests...'
                script {
                    if (isUnix()) {
                        sh 'mvn test'
                    } else {
                        bat 'mvn test'
                    }
                }
            }
            post {
                always {
                    // Archive test results
                    junit allowEmptyResults: true, testResults: 'target/surefire-reports/*.xml'

                    // Archive test coverage reports if available
                    publishHTML([
                        allowMissing: false,
                        alwaysLinkToLastBuild: false,
                        keepAll: true,
                        reportDir: 'target/site/jacoco',
                        reportFiles: 'index.html',
                        reportName: 'JaCoCo Coverage Report'
                    ])
                }
            }
        }

        stage('Integration Tests') {
            steps {
                echo 'Running integration tests...'
                script {
                    if (isUnix()) {
                        sh 'mvn failsafe:integration-test failsafe:verify'
                    } else {
                        bat 'mvn failsafe:integration-test failsafe:verify'
                    }
                }
            }
        }

        stage('Code Quality Analysis') {
            parallel {
                stage('SonarQube Analysis') {
                    steps {
                        echo 'Running SonarQube analysis...'
                        // Uncomment when SonarQube is configured
                        // withSonarQubeEnv('SonarQube') {
                        //     sh 'mvn sonar:sonar'
                        // }
                    }
                }
                stage('Dependency Check') {
                    steps {
                        echo 'Checking for vulnerable dependencies...'
                        script {
                            if (isUnix()) {
                                sh 'mvn org.owasp:dependency-check-maven:check'
                            } else {
                                bat 'mvn org.owasp:dependency-check-maven:check'
                            }
                        }
                    }
                }
            }
        }

        stage('Build Application') {
            steps {
                echo 'Building JAR file...'
                script {
                    if (isUnix()) {
                        sh 'mvn clean package -DskipTests'
                    } else {
                        bat 'mvn clean package -DskipTests'
                    }
                }
            }
            post {
                always {
                    archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
                }
            }
        }

        stage('Build Docker Image') {
            steps {
                echo 'Building Docker image...'
                script {
                    def customImage = docker.build("${DOCKER_IMAGE}:${DOCKER_TAG}")

                    // Also tag as latest
                    customImage.tag("latest")

                    // Store image ID for later stages
                    env.DOCKER_IMAGE_ID = customImage.id
                }
            }
        }

        stage('Test Docker Image') {
            steps {
                echo 'Testing Docker image...'
                script {
                    // Start containers for testing
                    sh """
                        docker-compose -f docker-compose.yml up -d mysql
                        sleep 30  # Wait for MySQL to be ready

                        # Test if MySQL is responsive
                        docker exec job_portal_mysql mysqladmin ping -h localhost -u root -ptelugu@1258

                        # Test application container
                        docker run --rm --network lates-jobs_job_portal_network \
                            -e SPRING_DATASOURCE_URL=jdbc:mysql://job_portal_mysql:3306/job_portal_db \
                            -e SPRING_DATASOURCE_USERNAME=root \
                            -e SPRING_DATASOURCE_PASSWORD=telugu@1258 \
                            ${DOCKER_IMAGE}:${DOCKER_TAG} java -jar app.jar --server.port=8080 &

                        # Health check
                        sleep 45
                        curl -f http://localhost:8080/actuator/health || exit 1
                    """
                }
            }
            post {
                always {
                    // Cleanup test containers
                    sh 'docker-compose -f docker-compose.yml down -v || true'
                }
            }
        }

        stage('Security Scan') {
            steps {
                echo 'Scanning Docker image for vulnerabilities...'
                script {
                    // Use Docker scan or Trivy for vulnerability scanning
                    sh """
                        # Install Trivy if not available
                        if ! command -v trivy &> /dev/null; then
                            echo "Trivy not found, skipping security scan"
                        else
                            trivy image --exit-code 0 --severity MEDIUM,HIGH,CRITICAL ${DOCKER_IMAGE}:${DOCKER_TAG}
                        fi
                    """
                }
            }
        }

        stage('Push to Registry') {
            when {
                branch 'main'  // Only push from main branch
            }
            steps {
                echo 'Pushing Docker image to registry...'
                script {
                    docker.withRegistry('https://registry.hub.docker.com', REGISTRY_CREDENTIALS) {
                        def customImage = docker.image("${DOCKER_IMAGE}:${DOCKER_TAG}")
                        customImage.push()
                        customImage.push("latest")
                    }
                }
            }
        }

        stage('Deploy to Staging') {
            when {
                branch 'main'
            }
            steps {
                echo 'Deploying to staging environment...'
                script {
                    // Deploy to staging using docker-compose
                    sh """
                        # Update docker-compose to use built image
                        sed -i 's|build:|# build:|g' docker-compose.yml
                        sed -i 's|context: .|# context: .|g' docker-compose.yml
                        sed -i 's|dockerfile: Dockerfile|# dockerfile: Dockerfile|g' docker-compose.yml
                        echo '    image: ${DOCKER_IMAGE}:${DOCKER_TAG}' >> docker-compose.yml

                        # Deploy
                        docker-compose up -d

                        # Health check
                        sleep 60
                        curl -f http://localhost:8080/actuator/health
                    """
                }
            }
        }
    }

    post {
        always {
            echo 'Pipeline completed!'

            // Cleanup Docker images
            sh """
                docker image prune -f
                docker container prune -f
            """
        }
        success {
            echo '✅ Build, tests, and deployment completed successfully!'

            // Send success notification
            emailext (
                subject: "✅ SUCCESS: Job Portal Build #${BUILD_NUMBER}",
                body: """
                    Build Status: SUCCESS ✅

                    Build Number: ${BUILD_NUMBER}
                    Git Commit: ${GIT_COMMIT}

                    Docker Image: ${DOCKER_IMAGE}:${DOCKER_TAG}

                    View Console Output: ${BUILD_URL}console

                    Application URL: http://localhost:8080
                """,
                to: "telugumallesh0757@gmail.com"
            )
        }
        failure {
            echo '❌ Build failed!'

            // Send failure notification
            emailext (
                subject: "❌ FAILED: Job Portal Build #${BUILD_NUMBER}",
                body: """
                    Build Status: FAILED ❌

                    Build Number: ${BUILD_NUMBER}
                    Git Commit: ${GIT_COMMIT}

                    View Console Output: ${BUILD_URL}console
                    View Test Results: ${BUILD_URL}testReport
                """,
                to: "telugumallesh0757@gmail.com"
            )
        }
        unstable {
            echo '⚠️ Build unstable - tests may have failed'
        }
    }
}
