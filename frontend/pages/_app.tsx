import '../styles/globals.css'
import type { AppProps } from 'next/app'
import store from '../store/index';
import { useRouter } from "next/router";

import { Provider } from 'react-redux';

import Layout from '../app/layout/Layout';

import '../assets/css/global_auth.css'
import '../assets/css/global.css'
import '../public/css/feed.css'
import '../assets/css/global_components.css'
import { useState, useEffect } from 'react';

import { setUserLogged } from '../store/actions/authActions'

function MyApp({ Component, pageProps }: AppProps) {
  const [loading, setLoading] = useState<boolean>(true)
  const [isLogged, setIsLogged] = useState<boolean>(false)
  const Router = useRouter();

  useEffect(() => {
    const token = typeof window !== 'undefined' ? localStorage.getItem('token') : null

    if (pageProps.isProtected) {
      if (token) {
        setUserLogged(store, token)
        setIsLogged(true)
        setLoading(false)
      }
      else {
        goLogin();
      }
    }
    else {
      setLoading(false)
    }
  })

  const goLogin = () => {
    Router.replace("/login");
  }

  return (
    <Provider store={store}>
      {loading ? (
        <span>Loading...</span>
      ) : (
        <div className={(isLogged ? "main_background " : "") + "main"}>
           <Layout>
          <Component {...pageProps} />
          </Layout>
        </div>
      )}
    </Provider>
  )
}
export default MyApp
