#### Mordeczki K8S-GCP:


1. GCP Cli (gcloud) (run `gcloud init`)
2. Docker

#### Sign in to GCP
`gcloud auth login`

#### Create project: 
`gcloud projects create mordeczki-project`


#### Set newly created project as active: 
`gcloud config set project mordeczki-project`


#### Enable billing:
[Read this](https://support.google.com/googleapi/answer/6158867?hl=en)


#### Enable few services:
`gcloud services enable containerregistry.googleapis.com`
`gcloud services enable deploymentmanager.googleapis.com`
`gcloud services enable container.googleapis.com`



#### Create (or update) project's infrastructure

`gcloud deployment-manager deployments create mordeczki --config 001-cloud.yml`

`gcloud deployment-manager deployments update mordeczki --config 001-cloud.yml`

#### Get credentials for `kubectl`

`gcloud container clusters get-credentials mordeczki-cluster --zone europe-north1-c`




#### Clean up - remove project:
`gcloud projects delete mordeczki-project` 






### DOCKER

#### Before pushing image do GCP authenticate:
`gcloud auth configure-docker`


### KUBERNETES

`kubectl apply -f 010-search-deployment.yml`