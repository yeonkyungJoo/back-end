#!/bin/bash
find /opt/codedeploy-agent/deployment-root/c414d20e-1274-4a04-bad2-5a03565477ec/* -maxdepth 0 -type 'd' | grep -v
$(stat -c '%Y:%n' /opt/codedeploy-agent/deployment-root/c414d20e-1274-4a04-bad2-5a03565477ec/* | sort -t: -n |
tail -1 | cut -d: -f2- | cut -c 3-) | xargs rm -rf
