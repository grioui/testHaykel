
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
                bat "C:\\nuget\\nuget.exe restore testHaykel.sln"
                bat "\"${tool 'MsBuild 4.0 x64'}\" testHaykel.sln /p:Configuration=release /p:OutputPath=${env.WORKSPACE}/Super/bin/release /p:Platform=\"Any CPU\""
            }
        }
    }
}