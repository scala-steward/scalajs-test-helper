#! /bin/bash

sbt clean +test +publishSigned sonatypeBundleUpload sonatypeReleaseAll
