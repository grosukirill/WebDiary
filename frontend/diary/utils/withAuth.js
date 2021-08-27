const Auth = (Component) => {  
  const withAuth = props => {
    return <Component {...props} />;
  };

  withAuth.getInitialProps = async ctx => {
    return { isProtected: true };
  };

  return withAuth;
};

export default Auth;