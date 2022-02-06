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
    git clone "git@github.com:minaxijoshi3101/hello-world-1.git"
    cd $WORKSPACE/hello-world-1
    '''
    }
    stage("build")
    {
    sh '''
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
