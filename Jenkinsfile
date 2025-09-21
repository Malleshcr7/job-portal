pipeline {
    agent any

    tools {
        // Ensure you have JDK installed/configured in Jenkins under "Global Tool Configuration"
        jdk 'jdk17'  // or whatever name you gave your JDK in Jenkins
        // Optional: If using Maven wrapper, you may skip maven tool declaration
        // maven 'maven-3.9.6'
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Build') {
            steps {
                sh './mvnw clean package'  // Uses Maven Wrapper (recommended)
                // OR if you prefer global Maven:
                // sh 'mvn clean package'
            }
        }

        stage('Test') {
            steps {
                sh './mvnw test'
            }
            post {
                always {
                    junit '**/target/surefire-reports/*.xml'
                }
            }
        }

        stage('Archive Artifacts') {
            steps {
                archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
            }
        }
    }

    post {
        success {
            echo '✅ Build and tests completed successfully!'
        }
        failure {
            echo '❌ Build failed!'
        }
    }
}