import { useRouter } from "next/router";
import type { AppProps } from 'next/app'

const withLogin = (WrappedComponent: React.StatelessComponent) => {
  return ({ pageProps }: AppProps) => {
    if (typeof window !== "undefined") {
      const Router = useRouter();

      const accessToken = typeof window !== 'undefined' ? localStorage.getItem('token') : null

      if (accessToken) {
        Router.replace("/");
        return null;
      }

      return <WrappedComponent {...pageProps} />;
    }

    return null;
  };
};

export default withLogin;