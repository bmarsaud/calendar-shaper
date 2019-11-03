#!/bin/bash -e

# ========================
#      CONFIGURATION
# ========================
# Definition
CONF_DOCKERFILE=Dockerfile
CONF_REGISTRY=registry.mouseover.fr:5000
CONF_IMAGE="bmarsaud/calendar-shaper"
CONF_IMAGE_TAG=unknown
CONF_TAG_AS_LATEST=true

# Environment resolve
[[ -z $DOCKERFILE ]] && DOCKERFILE=$CONF_DOCKERFILE
[[ -z $REGISTRY ]] && REGISTRY=$CONF_REGISTRY
[[ -z $IMAGE ]] && IMAGE=$CONF_IMAGE
[[ -z $IMAGE_TAG ]] && IMAGE_TAG=$CONF_IMAGE_TAG
[[ -z $TAG_AS_LATEST ]] && TAG_AS_LATEST=$CONF_TAG_AS_LATEST

# Environment logging
echo "=== ENVIRONMENT ==="
echo "-> Docker"
echo "IMAGE=$IMAGE"
echo "IMAGE_TAG=$IMAGE_TAG"
echo "REGISTRY=$REGISTRY"
echo "TAG_AS_LATEST=$TAG_AS_LATEST"
echo "==================="

# ========================
#    Application tasks
# ========================
echo "===> Package application"
mvn -f ../ -Dmaven.test.skip clean compile package
echo "Done."

echo "===> Extracting version"
PROJECT_VERSION=$(cat ../pom.xml | grep "^    <version>.*</version>$" | awk -F'[><]' '{print $3}')
echo "PROJECT_VERSION=$PROJECT_VERSION"
echo "Done."

echo "==> Copying jar"
FILE_NAME="calendar-shaper-$PROJECT_VERSION.jar"
cp ../target/$FILE_NAME $FILE_NAME
echo "Done."

# ========================
#    Docker tasks
# ========================
echo "===> Building image"
docker build --no-cache -t $IMAGE -f $DOCKERFILE --build-arg FILE_NAME=$FILE_NAME .
echo "Done."

echo "===> Exporting image to registry"
IMAGE_TAG=$PROJECT_VERSION
$TAG_AS_LATEST && docker tag $IMAGE $REGISTRY/$IMAGE:latest
docker tag $IMAGE $REGISTRY/$IMAGE:$IMAGE_TAG
docker tag $IMAGE $IMAGE:$IMAGE_TAG
docker push $REGISTRY/$IMAGE:$IMAGE_TAG
docker rmi $REGISTRY/$IMAGE:$IMAGE_TAG || true
docker rmi $IMAGE || true
echo "Done."

echo "===> Cleaning"
rm $FILE_NAME
echo "Done."
