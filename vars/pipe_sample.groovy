def call()
{
pipeline
{
  node("master")
  {
    stage("code checkout from SCM")
    {
    echo "checkout scm"
    sh '''
    cd $WORKSPACE
    rm -rf hello-world-1
    git clone "git@github.com:minaxijoshi3101/hello-world-1.git"
    '''
    }
    stage("build")
    {
    sh '''
    cd hello-world-1
    mvn clean install
    cd webapp/target
    '''
    stash 'source'
    }
    }
    node("SIT_ENV")
    {
    stage("deploy")
    {
    unstash 'source'
    sh " cp **/*.war /opt/tomcat/webapps/ "
      
    }
  }
}
}
