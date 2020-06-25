#!/bin/bash
aws ecs update-service --cluster magpi --service magpi-service --desired-count 2  --force-new-deploy --profile fernando 
