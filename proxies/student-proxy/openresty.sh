#!/bin/bash

# Installation instructions found in OpenResty 

# add the public key used to sign the repo:
wget 'http://openresty.org/package/admin@openresty.com-5ea678a6.rsa.pub'
mv 'admin@openresty.com-5ea678a6.rsa.pub' /etc/apk/keys/

# add the repo:
. /etc/os-release
MAJOR_VER=`echo $VERSION_ID | sed 's/\.[0-9]\+$//'`

echo "http://openresty.org/package/alpine/v$MAJOR_VER/main" \
    | tee -a /etc/apk/repositories

# update the local index cache:
apk update

# install openresty
apk add openresty
