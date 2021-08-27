import { useRouter } from "next/router";

const withLogin = (WrappedComponent) => {
  return (props) => {
    if (typeof window !== "undefined") {
      const Router = useRouter();

      const accessToken = typeof window !== 'undefined' ? localStorage.getItem('token') : null

      if (accessToken) {
        Router.replace("/");
        return null;
      }

      return <WrappedComponent {...props} />;
    }

    return null;
  };
};

export default withLogin;