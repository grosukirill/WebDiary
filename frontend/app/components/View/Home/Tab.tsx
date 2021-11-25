import Link from 'next/link'
import { useRouter } from "next/router";

const Tab = () => {
    const Router = useRouter();

    const isMain = Router.pathname === "/"
    const isFeed = Router.pathname === "/feed"
    return (
        <div className="tab_component">
            <ul>
                <Link href="/feed">
                    <li className={isFeed ? "active_tab_li" : ""}>
                        Лента
                    </li>
                </Link>
                <Link href="/">
                    <li className={isMain ? "active_tab_li" : ""}>
                        Главная
                    </li>
                </Link>
                <Link href="/">
                    <li>
                        Новое
                    </li>
                </Link>
                <Link href="/">
                    <li>
                        Поиск
                    </li>
                </Link>
            </ul>
        </div>
    )
}

export default Tab;