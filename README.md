# Spring Boot app w/instructions on deploying to different cloud environments
## Prerequisites for all deployments:
1. GitHub CLI installed
2. Cloned this repo locally:
    - `git clone https://github.com/franklsm1/cloud-deploy-demo.git`
3. `cd` into the newly cloned repos directory

# AWS Deployments using the elastic beanstalk CLI
### Requirements:
   - AWS account (free tier works perfectly)
   - Elastic Beanstalk (eb) CLI installed (see link at the bottom for instructions)
   - An AWS user access id and secret key (instructions found [here](https://docs.aws.amazon.com/IAM/latest/UserGuide/id_users_create.html#id_users_create_console) if needed)
### Steps
1. Setup and configure this repo to work with AWS: `eb init`
    - select the desired region to deploy to
    - enter the access key and secret key created in the requirements (this step is only performed the first time `eb init` is called)
    - choose an existing application or create a new one
    - choose Java for the platform (#11)
    - choose java 8 for the version
    - select "N" when asked about codeCommit setup (see link at the bottom if interested in setting up an AWS pipeline)
    - select "n" when asked about a key pair (unless you want to ssh into the instance at some point)
2. Add the following config property to the newly created `.elasticbeanstalk/config.yml`:
    ```
    deploy:
      artifact: build/libs/demo-0.0.1.jar
    ```
    - this tells the eb CLI what to deploy to the cloud
3. Create a non-load balanced environment on AWS (can use any name): `eb create aws-deploy-demo -s`
4. Clean and build the project to create a jar: `./gradlew clean build`
5. Deploy the app to the cloud: `eb deploy`
6. Validate the app is running on AWS:
    - visit the [elastic beanstalk homepage](https://us-east-1.console.aws.amazon.com/elasticbeanstalk) and hit the `/swagger-ui.html` endpoint for the running apps URL

#### Notes:
- AWS by default runs on port 5000 (setting the `server.port` property solved this issue)
- Need to run a gradle build command before redeploying any new changes

### Example working config.yml file:
```yaml
branch-defaults:
  master:
    environment: aws-deploy-demo
    group_suffix: null
global:
  application_name: aws-deploy-demo
  branch: null
  default_ec2_keyname: aws-eb
  default_platform: Java 8
  default_region: us-east-1
  include_git_submodules: true
  instance_profile: null
  platform_name: null
  platform_version: null
  profile: eb-cli
  repository: null
  sc: git
  workspace_type: Application
deploy:
  artifact: build/libs/demo-0.0.1.jar
```

## Steps to Install the EB CLI
### Mac Only
    Requirements:
       - Homebrew
    Commands to run:
        1. Update homebrew: brew update
        2. Install EB CLI: brew install awsebcli

### Linux or Mac
    Requirements:
       - Python version 2.7, version 3.4, or newer
    Commands to run:
        1. Download pip: `curl -O https://bootstrap.pypa.io/get-pip.py`
        2. Install pip: `python3 get-pip.py –-user`
        3. Add pip to path: `export PATH=~/Library/Python/3.6/bin:$PATH`
            - add the following command to the `~/.bash_profiles` file to make permanent
        4. Install EB CLI: `pip install awsebcli --upgrade –-user`


# Cloud Foundry deployments
### Requirements:
1. Cloud foundry CLI installed (see link at the bottom for instructions)
2. Free Account and a created "Org" on one CF supported cloud environment (example envs below):
    - Both IBM cloud and PCF are free to sign up w/out a credit card
    - [Link](https://www.ibm.com/cloud/info/try-ibm-cloud) to sign up for IBM Cloud
    - [Link](https://pivotal.io/platform/pcf-tutorials/getting-started-with-pivotal-cloud-foundry/introduction) to sign up for Pivotal Cloud Foundry(PCF)

### Steps
1. Clean and build the project to create a jar: `./gradlew clean build`
2. Point the cf CLI to the desired cloud environment
    - Command for IBM Cloud: `cf api api.ng.bluemix.net --skip-ssl-validation`
    - Command for PCF: `cf api api.run.pivotal.io --skip-ssl-validation`
3. Login to the chosen environment: `cf login`
4. (Optional) Update names config variables in the [manifest.yml](./manifest.yml)
   - The IBM max memory per instance for the **FREE** tier is 256MB
5. Push the jar to the cloud: `cf push -p build/libs/demo-0.0.1.jar`

## Helpful links:
- [Install python, pip, and elastic beanstalk CLI](https://docs.aws.amazon.com/elasticbeanstalk/latest/dg/eb-cli3-install-linux.html)
- [How to create an AWS pipeline](https://docs.aws.amazon.com/codebuild/latest/userguide/how-to-create-pipeline.html)
- [Installing and getting started with the CF CLI](https://docs.cloudfoundry.org/cf-cli/)