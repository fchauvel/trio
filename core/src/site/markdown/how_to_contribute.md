## How to contribute?

### How to fetch the source code

Trio is an open-source Java application, whose source code is available on 
Github at the following address: 

https://github.com/fchauvel/trio/


### How to build Trio

The Trio code base follows the Maven convention. The project can be build,
tested, packaged and installed locally by running the command:

    $> mvn clean install


### How to release a new version

Before to create a release, one should ensure the following:

 - Read/write access to the git repository 

 - Valid credentials to connect on the "deployment" server are
   configured in .m2/settings.xml

The configuration of the SCM maven plugin can be tested using the
following command:

    $> mvn scm:validate

Under Windows, one shall set up an ssh agent to avoid that the git
command hang waiting for a passphrase. The following commands are
necessary (run on the git-bash):

    $> eval `ssh-agent`
    $> env | grep SSH
    $> ssh-add "/c/Users/Bobby/.ssh/id_rsa"
       enter passphrase : XXXXXXXX

The Maven release plugin can be run in a "dryRun" mode using the
option '-DdryRun=true-'. We recommend to get the 'dry run' before to
run the 'release:prepare' command.

    $> mvn --batch-mode release:prepare -DdryRun=true -DreleaseVersion=0.1 -DdevelopmentVersion=0.2-SNAPSHOT
    $> mvn --batch-mode release:prepare -Dresume=false -DreleaseVersion=0.1 -DdevelopmentVersion=0.2-SNAPSHOT
    $> mvn release:perform

If anything goes wrong during the 'perform' command, on can
revert the info committed in the repository using the following
commands:

    $> git reset --hard <id of the commit you want to reset to>
    $> git push origin master --force

Tags that have been created by the release plugin will not be removed by the 
previous commands, and must remove manually, as shown below. The first command 
retrieves all the existing tags, and the next two remove and publish the removal
respectively.

    $> git tag
    my-app-0.1
    $> git tag -d my-app-0.1
    $> git push origin :refs/tags/my-app-0.1