pipeline {
	agent {
		kubernetes {
			defaultContainer 'buildpack'
			yaml '''
kind: Pod
spec:
  containers:
  - name: buildpack
    image: maxmorhardt/topfilms-jenkins-buildpack:latest
    imagePullPolicy: Always
    securityContext:
      privileged: true
  - name: dind
    image: docker:27-dind
    imagePullPolicy: Always
    securityContext:
      privileged: true
'''
		}
	}

	parameters {
		string(name: 'TAG', defaultValue: params.TAG ?: '0.0.1', description: 'Git tag version', trim: true)
		booleanParam(name: 'DEPLOY_CA_CERT', defaultValue: false, description: 'Deploy ca cert as secret to k8s')
	}

	environment {
		APP_NAME = 'topfilms-api'
		CHART_NAME = 'topfilms-api-chart'
		GITHUB_URL = 'https://github.com/Top-Films/topfilms-api'

		DOCKER_REGISTRY = 'registry-1.docker.io'
		DOCKER_REGISTRY_FULL = "oci://${env.DOCKER_REGISTRY}"
	}

	stages {

		stage('Git Clone') {
			steps {
				script {
					checkout scmGit(
						branches: [[
							name: "refs/tags/$TAG"
						]],
						userRemoteConfigs: [[
							credentialsId: 'github',
							url: "$GITHUB_URL"
						]]
					)

					sh 'ls -lah'
				}
			}
		}

		stage('Java Build') {
			steps {
				script {
					sh """
						java --version
						mvn --version

                        mvn versions:set -DnewVersion=$TAG
						mvn clean install
					"""
				}
			}
		}

		stage('Docker CI') {
			steps {
				container('dind') {
					script {
						withCredentials([usernamePassword(credentialsId: 'docker', usernameVariable: 'DOCKER_USERNAME', passwordVariable: 'DOCKER_PASSWORD')]) {
							sh 'echo $DOCKER_PASSWORD | docker login -u $DOCKER_USERNAME --password-stdin'

							sh 'docker buildx build --platform linux/arm64/v8 . --tag $DOCKER_USERNAME/$APP_NAME:$TAG --tag $DOCKER_USERNAME/$APP_NAME:latest'
							sh 'docker push $DOCKER_USERNAME/$APP_NAME --all-tags'
						}
					}
				}
			}
		}

		stage('Helm CI') {
			steps {
				script {
					withCredentials([usernamePassword(credentialsId: 'docker', usernameVariable: 'DOCKER_USERNAME', passwordVariable: 'DOCKER_PASSWORD')]) {
						sh '''
							cd helm

							echo "$DOCKER_PASSWORD" | helm registry login $DOCKER_REGISTRY --username $DOCKER_USERNAME --password-stdin

							helm package $APP_NAME --app-version=$TAG --version=$TAG
							helm push ./$CHART_NAME-$TAG.tgz $DOCKER_REGISTRY_FULL/$DOCKER_USERNAME
						'''
					}
				}
			}
		}

		stage('Deploy CA Cert') {
			when {
				expression {
					DEPLOY_CA_CERT == "true"
				}
			}
			steps {
				script {
					withCredentials([
						file(credentialsId: 'ca-cert', variable: 'CA_CERT'),
						file(credentialsId: 'ca-cert-private-key', variable: 'CA_CERT_PRIVATE_KEY'),
						file(credentialsId: 'kube-config', variable: 'KUBE_CONFIG')
					]) {
						sh 'mkdir -p $WORKSPACE/.kube && cp $KUBE_CONFIG $WORKSPACE/.kube/config'

						sh '''
							cp $CA_CERT $WORKSPACE/cert.pem
							cp $CA_CERT_PRIVATE_KEY $WORKSPACE/key.pem

							ls -lah

							set +e

							kubectl delete secret api.topfilms.io-tls --namespace topfilms
							kubectl create secret tls api.topfilms.io-tls --cert=cert.pem --key=key.pem --namespace topfilms

							set -e
						'''
					}
				}
			}
		}

        stage('Deploy Secret') {
            steps {
                script {
                    withCredentials([
                        usernamePassword(credentialsId: 'postgres-topfilms-b64', usernameVariable: 'POSTGRES_TOPFILMS_USERNAME_B64', passwordVariable: 'POSTGRES_TOPFILMS_PASSWORD_B64'),
                        string(credentialsId: 'postgres-jdbc-b64', variable: 'POSTGRES_JDBC_B64'),
                        file(credentialsId: 'kube-config', variable: 'KUBE_CONFIG')
                    ]) {
                        sh 'mkdir -p $WORKSPACE/.kube && cp $KUBE_CONFIG $WORKSPACE/.kube/config'

                        sh '''
                            sed -i "s/<POSTGRES_JDBC_B64>/$POSTGRES_JDBC_B64/g" secret.yaml
                            sed -i "s/<POSTGRES_TOPFILMS_USERNAME_B64>/$POSTGRES_TOPFILMS_USERNAME_B64/g" secret.yaml
                            sed -i "s/<POSTGRES_TOPFILMS_PASSWORD_B64>/$POSTGRES_TOPFILMS_PASSWORD_B64/g" secret.yaml

                            cat secret.yaml
                        '''

                        sh """
                            kubectl apply --filename secret.yaml --namespace topfilms
                        """
                    }
                }
            }
        }

		stage('CD') {
			steps {
				script {
					withCredentials([
						usernamePassword(credentialsId: 'docker', usernameVariable: 'DOCKER_USERNAME', passwordVariable: 'DOCKER_PASSWORD'),
						file(credentialsId: 'kube-config', variable: 'KUBE_CONFIG')
					]) {
						sh 'mkdir -p $WORKSPACE/.kube && cp $KUBE_CONFIG $WORKSPACE/.kube/config'

						sh '''
							echo "$DOCKER_PASSWORD" | helm registry login $DOCKER_REGISTRY --username $DOCKER_USERNAME --password-stdin

							helm upgrade $APP_NAME $DOCKER_REGISTRY_FULL/$DOCKER_USERNAME/$CHART_NAME --version $TAG --install --atomic --debug --history-max=3 --namespace topfilms --set image.tag=$TAG
						'''
					}
				}
			}
		}

	}
}