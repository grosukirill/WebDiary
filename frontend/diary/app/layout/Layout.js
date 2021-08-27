import Header from './Header'
import Sidebar from './Sidebar';
import { useRouter } from 'next/router'

const Layout = ({ children }) => {
    const router = useRouter()
    const noLayoutPaths = [
        "/register",
        "/login"
    ]
    const denyPaths = noLayoutPaths.includes(router.pathname)

    return (
        <>
            <Header denyPaths={denyPaths} />
            <main className={!denyPaths ? "auth_main" : ""}>
                <Sidebar denyPaths={denyPaths} />
                {children}
            </main>
        </>
    )
}

export default Layout;