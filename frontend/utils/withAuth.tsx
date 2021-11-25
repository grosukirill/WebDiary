import { GetStaticProps } from 'next'
import type { AppProps } from 'next/app'

const Auth = (Component: React.StatelessComponent) => {  
  const withAuth = ({ pageProps }: AppProps) => {
    return <Component {...pageProps} />;
  };

  withAuth.getInitialProps = async (ctx: GetStaticProps) => {
    return { isProtected: true };
  };

  return withAuth;
};

export default Auth;