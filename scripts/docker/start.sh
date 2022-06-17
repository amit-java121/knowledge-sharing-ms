#!/bin/bash

if [[ $2 = "--debug" ]]; then
  debug_option="-p 8787:8787 -e DEBUG=1"
fi

docker run \
  -p 8083:8083 \
  $debug_option \
  --rm \
  --name knowledge-sharing-ms \
  knowledge-sharing-ms:latest
