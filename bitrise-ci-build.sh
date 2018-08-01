#!/bin/bash

if [[ -z "${BITRISE_PULL_REQUEST}" ]]; then
    echo "---> Making build outside of Pull Request (building single commit or branch)"
    ./gradlew clean gnagCheck build jacocoDebugReport
else
    echo "---> Making build for Pull Request"
    # In the env variable BITRISE_PULL_REQUEST Bitrise CI provides the ID of the PR
    ./gradlew clean gnagReport -PauthToken="${GITHUB_TOKEN}" -PissueNumber="${BITRISE_PULL_REQUEST}" build jacocoDebugReport
fi