def call(Map pipelineParams)
{
pipeline
{
  node("master")
  {
    stage("code checkout from SCM")
    {
    sh '''
    git clone "git@github.com:minaxijoshi3101/hello-world-1.git"
    cd hello-world-1
    '''
    }
    stage("build")
    {
    sh '''
    mvn clean install
    cd webapp/target
    '''
    }
    }
    node("SIT_ENV")
    {
    stage("deploy")
    {
    cp **/*.war /opt/tomcat/webapps/
    }
  }
}
}
