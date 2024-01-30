def call() {
    node {
        stage('Clone Repo') {
            cloneRepo('dev')
        }
        stage('Compile') {
            compile()
        }
        stage('Unit test') {
            unitTest()
        }
        stage('Sonar Scan') {
            scan()
        }
        // stage('Add tag') {
        //     addTag('dev')
        // }
        stage('Deploy Nexus') {
            deployNexus('uat')
        }
        stage('Deploy App') {
            deployApp('8083')
        }
        stage('Check health') {
            checkHealth('8083')
        }
        post{
            success{
                emailext to: "khoadnguyen178@gmail.com",
                subject: "Jenkins build: ${currentBuild.currentResult}: ${env.STAGE_NAME}",
                body: "${currentBuild.currentResult}: Job ${env.STAGE_NAME}"
            }
            failure{
                emailext to: "khoadnguyen178@gmail.com",
                subject: "Jenkins build: ${currentBuild.currentResult}: ${env.STAGE_NAME}",
                body: "${currentBuild.currentResult}: Job ${env.STAGE_NAME}\nFor more information, click here: ${env.BUILD_URL}"
            }
        }
    }
}