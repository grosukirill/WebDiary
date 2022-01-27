import Header from './Header'
import Sidebar from './Sidebar';
import RightSidebar from './RightSidebar';
import { useRouter } from 'next/router'

interface LayoutProps {
    children: React.ReactNode
}

const Layout: React.FC<LayoutProps> = ({ children }) => {
    const router = useRouter()
    const noLayoutPaths = [
        "/register",
        "/login"
    ]
    const denyPaths = noLayoutPaths.includes(router.pathname)

    return (
        <>
            <main className={!denyPaths ? "auth_main" : ""}>
                <Sidebar denyPaths={denyPaths} route={router.route} />
                {denyPaths ? children : null}
                <Header denyPaths={denyPaths} children={children} />
                <RightSidebar denyPaths={denyPaths} />
            </main>
        </>
    )
}

export default Layout;