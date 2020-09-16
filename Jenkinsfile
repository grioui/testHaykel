
pipeline {
    agent any

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }
        stage('Build & SonarQube analysis') {
            steps {
                echo "Building..."
                bat "C:\\nuget\\nuget.exe restore test2.sln"
                bat "MsBuild test2.sln /p:Configuration=release /p:OutputPath=${env.WORKSPACE}/Super/bin/release /p:Platform=\"Any CPU\""
            }
        }
    }
}