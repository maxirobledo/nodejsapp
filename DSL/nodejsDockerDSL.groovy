job('Aplicacion Node.js Docker DSL') {
    description('Aplicación Node JS Docker DSL para el curso de Jenkins')
    scm {
        git('https://github.com/maxirobledo/nodejsapp.git', 'master') { node ->
            node / gitConfigName('maxirobledo')
            node / gitConfigEmail('maxirobledo@gmail.com')
        }
    }
    triggers {
        scm('H/7 * * * *')
    }
    wrappers {
        nodejs('nodejs')
    }
    steps {
        dockerBuildAndPublish {
            repositoryName('maxirobledo/nodejsapp')
            tag('${GIT_REVISION,length=7}')
            registryCredentials('dockerhub')
            forcePull(false)
            createFingerprints(false)
            skipDecorate()
        }
    }
    publishers {
	slackNotifier {
            notifyAborted(true)
            notifyEveryFailure(true)
            notifyNotBuilt(false)
            notifyUnstable(false)
            notifyBackToNormal(true)
            notifySuccess(true)
            notifyRepeatedFailure(false)
            startNotification(false)
            includeTestSummary(false)
            includeCustomMessage(false)
            customMessage(null)
            sendAs(null)
            commitInfoChoice('NONE')
            teamDomain(null)
            authToken(null)
        }
    }
}
