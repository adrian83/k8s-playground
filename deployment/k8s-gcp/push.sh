DOCKER_IMAGE=$2
VERSION=$3
APP=$1

if [[ -z "$DOCKER_IMAGE" ]] || [[ -z "$VERSION" ]]
then
    echo ""
    echo "Error! Missing parameter(s)"
    echo "Usage ./push.sh <app> <docker image id> <version>"
    echo ""
    exit 1
fi


PROJECT='mordeczki-project' #`gcloud config get-value project`

echo Project: $PROJECT
echo Docker image id: $DOCKER_IMAGE
echo Version: $VERSION


# tag Docker image
docker tag $DOCKER_IMAGE gcr.io/$PROJECT/$APP:$VERSION

# push image into Container Registry
docker push gcr.io/$PROJECT/$APP:$VERSION