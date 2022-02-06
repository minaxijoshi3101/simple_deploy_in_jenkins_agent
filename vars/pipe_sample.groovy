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
    
    '''
    stash 'source'
    }
    }
    node("SIT_ENV")
    {
    stage("deploy")
    {
    unstash 'source'
    sh ''' 
    cd $WORKSPACE/hello-world-1/webapp/target
    cp *.war /opt/tomcat/webapps/
    '''
      
    }
  }
}
}
